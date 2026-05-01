package jp.co.sys.dao;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import jp.co.sys.bean.UserBean;
import jp.co.sys.util.DatabaseConnectionProvider;
import jp.co.sys.util.UserList;

/**
 * データベース「meetingroomb」のテーブル「user」を操作するクラスです。
 * @author 加藤博文
 */
public class UserDao {
	/**
	 * インスタンス化の抑制処理
	 */
	private UserDao() {
	}
	
	/**
	 * 有効ユーザーをUserListで全件出力するメソッドです。
	 * @return UserList型でデータベース情報を返す。
	 */
	public static UserList findAll() {
		UserList userlist = new UserList();
		String sql = "select * from user where id AND isDeleted != 1";
		try (Connection db = DatabaseConnectionProvider.getConnection();
				PreparedStatement pstmt = db.prepareStatement(sql)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					String id = rs.getString("id");
					String address = rs.getString("address");
					String name = rs.getString("name");
					String password = rs.getString("password");
					int isAdmin = rs.getInt("isAdmin");
					int isDeleted = rs.getInt("isDeleted");
					UserBean ub = new UserBean(address, id, name, password, isAdmin, isDeleted);
					userlist.add(ub);
				}
			}
		} catch (SQLException ex) {
			System.out.println("★UserDAOのfindAllでエラー発生！");
			ex.printStackTrace();
		}
		return userlist;
	}
	
	/**
	 * 利用者IDとパスワードで利用者認証（データベースの登録可否）を行うメソッドです。
	 * @param id 利用者ID
	 * @param password パスワード
	 * @return UserBean型のデータベース情報を返す。
	 * @throws NoSuchAlgorithmException この例外は、ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合にスローされます。
	 * @throws InvalidKeySpecException 無効なキー仕様の例外です。
	 */
	public static UserBean certificate​(String id, String password)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		UserBean attestation = new UserBean(id, password);
		UserBean user = null;
		String pass = attestation.getPassword();
		//パスワードをハッシュ化用アルゴリズムの設定です。
		PBEKeySpec spec = new PBEKeySpec(pass.toCharArray(), attestation.getHash(), attestation.getIterations(),
				attestation.getKeyLength());
		//"PBKDF2WithHmacSHA256"は、ハッシュ化用アルゴリズムの設定です。
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		byte[] hash = skf.generateSecret(spec).getEncoded();
		String sql = "select * from user where id = ? AND password=? ";
		try (Connection db = DatabaseConnectionProvider.getConnection();
				PreparedStatement pstmt = db.prepareStatement(sql)) {
			pstmt.setString(1, id);
			//ハッシュ化したパスワードをString変換
			pstmt.setString(2, Base64.getEncoder().encodeToString(hash));
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					password = rs.getString("password");
					id = rs.getString("id");
					String name = rs.getString("name");
					String address = rs.getString("address");
					int isAdmin = rs.getInt("isAdmin");
					int isDeleted = rs.getInt("isDeleted");
					user = new UserBean(address, id, name, password, isAdmin, isDeleted);
				}
			}
		} catch (SQLException ex) {
			System.out.println("★UserDAOのcertificateでエラー発生！");
			ex.printStackTrace();
		}
		return user;
	}

	/**
	 * 引数idNowにて、現在の西暦下二桁に存在する現在の最新利用者IDを取得するメソッドです。
	 * @param idNow 西暦下二桁の情報
	 * @return UserList型でデータベース情報を返す。
	 */
	public static UserList getNowId(String idNow) {
		UserList userlist = new UserList();
		String sql = "select * from user where id like ? ";
		try (Connection db = DatabaseConnectionProvider.getConnection();
				PreparedStatement pstmt = db.prepareStatement(sql)) {
			pstmt.setString(1, idNow + "%");
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					String id = rs.getString("id");
					String address = rs.getString("address");
					String name = rs.getString("name");
					String password = rs.getString("password");
					int isAdmin = rs.getInt("isAdmin");
					int isDeleted = rs.getInt("isDeleted");
					UserBean ub = new UserBean(id, address, name, password, isAdmin, isDeleted);
					userlist.add(ub);
				}
			}
		} catch (SQLException ex) {
			System.out.println("★UserDAOのgetNowIdでエラー発生！");
			ex.printStackTrace();
		}
		return userlist;
	}

	/**
	 * 利用者IDで該当するユーザーを検索するメソッドです。
	 * @param id 利用者ID
	 * @return 存在する場合、UserBean型でデータベース情報を返し、存在しない場合nullを返す。
	 */
	public static UserBean findById(String id) {
		String sql = "SELECT * FROM user WHERE id=?";
		try (Connection db = DatabaseConnectionProvider.getConnection();
				PreparedStatement pstmt = db.prepareStatement(sql)) {
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			UserBean rb = new UserBean(
					rs.getString("id"),
					rs.getString("name"),
					rs.getString("address"),
					rs.getString("password"),
					rs.getInt("isAdmin"));
			return rb;
		} catch (SQLException e) {
			System.out.println("★UserDAOのfindByIdでエラー発生！");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 現存する有効な管理者ユーザーを検索するメソッドです。
	 * @return 管理者数
	 */
	public static int findAdmin() {
		String sql = "SELECT * FROM user WHERE isAdmin=1 and isDeleted!=1";
		try (Connection db = DatabaseConnectionProvider.getConnection();
				PreparedStatement pstmt = db.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {
			ResultSet rs = pstmt.executeQuery();
			rs.last();
			int row = rs.getRow();
			return row;
		} catch (SQLException e) {
			System.out.println("★UserDAOのfindAdminでエラー発生！");
			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * 新規ユーザー登録を実行するメソッドです。
	 * @param userbean 登録するデータをUserBean型で取得する
	 * @return テーブル「user」へのデータ挿入真偽
	 * @throws Exception 発生する可能性がある検査例外を呼び出し元に委譲する為に記載。
	 */
	public static boolean insert​(UserBean userbean) throws Exception {
		int ret = -1;
		UserBean cipher = new UserBean();
		String sql = "INSERT INTO user (id,password,name, address ,isDeleted, isAdmin) VALUES(?, ?, ?, ?, ?,?)";
		String password = userbean.getPassword();
		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), cipher.getHash(), cipher.getIterations(),
				cipher.getKeyLength());
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		byte[] hash = skf.generateSecret(spec).getEncoded();
		try (Connection conn = DatabaseConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userbean.getId());
			pstmt.setString(2, Base64.getEncoder().encodeToString(hash));
			pstmt.setString(3, userbean.getName());
			pstmt.setString(4, userbean.getAddress());
			pstmt.setInt(5, userbean.getIsDeleted());
			pstmt.setInt(6, userbean.getIsAdmin());
			ret = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("★UserDAOのinsert​でエラー発生！");
			e.printStackTrace();
		}
		return ret != 0;
	}

	/**
	 * 既存ユーザー情報を修正するメソッドです。
	 * @param userbean 修正するデータをUserBean型で取得する
	 * @return テーブル「user」のデータ変更真偽（id以外）
	 * @throws NoSuchAlgorithmException この例外は、ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合にスローされます。
	 * @throws InvalidKeySpecException 無効なキー仕様の例外です。
	 */
	public static boolean update(UserBean userbean) throws NoSuchAlgorithmException, InvalidKeySpecException {
		int ret = -1;
		UserBean cipher = new UserBean();
		String sql = "UPDATE user SET name =?, address=?, password=?, isAdmin=? WHERE id=?";
		String password = userbean.getPassword();
		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), cipher.getHash(), cipher.getIterations(),
				cipher.getKeyLength());
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		byte[] hash = skf.generateSecret(spec).getEncoded();
		try (Connection db = DatabaseConnectionProvider.getConnection();
				PreparedStatement pstmt = db.prepareStatement(sql)) {
			pstmt.setString(1, userbean.getName());
			pstmt.setString(2, userbean.getAddress());
			pstmt.setString(3, Base64.getEncoder().encodeToString(hash));
			pstmt.setInt(4, userbean.getIsAdmin());
			pstmt.setString(5, userbean.getId());
			ret = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("★UserDAOのupdate​でエラー発生！");
			e.printStackTrace();
		}
		return ret != 0;
	}

	/**
	 * ユーザーに削除フラグ（論理削除）設定を実施します。
	 * @param userbean 削除するデータをUserBean型で取得する
	 * @return テーブル「user」のデータ論理削除の真偽
	 */
	public static boolean delete​(UserBean userbean) {
		String sql = "update user set isDeleted = 1 where id  = ?";
		try (Connection conn = DatabaseConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userbean.getId());
			int ret = pstmt.executeUpdate();
			return ret != 0;
		} catch (SQLException e) {
			System.out.println("★UserDAOのdelete​​でエラー発生！");
			e.printStackTrace();
		}
		return false;
	}
}
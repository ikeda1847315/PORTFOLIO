package jp.co.sys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import jp.co.sys.bean.ReservationBean;
import jp.co.sys.util.DatabaseConnectionProvider;
import jp.co.sys.util.ReservationList;

/**
 * データベース「meetingroomb」のテーブル「reservation」を操作するクラスです。
 * @author 小山裕貴
 */
public class ReservationDao {
	/**
	 * インスタンス化の抑制処理
	 */
	private ReservationDao() {}
	
	/**
	 * 引数なしで、予約状況を全件検索するメソッドです。
	 * @return ReservationList型の全ての予約データを返す。データがない場合は、nullを返す。
	 */
	public static ReservationList findAll() {
		ReservationList list = new ReservationList();
		String sql = "SELECT * FROM Reservation";
		try (Connection db = DatabaseConnectionProvider.getConnection();
				PreparedStatement pstmt = db.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				ReservationBean rb = new ReservationBean(rs.getInt("id"), rs.getString("roomId"),
						rs.getString("date"),
						rs.getString("start"), rs.getString("end"), rs.getString("userId"));
				list.add(rb);
			}
		} catch (SQLException e) {
			System.out.println("★ReservationDaoのfindAllでエラー発生！");
			e.printStackTrace();
		}
		// 空だったらnullを入れる
		if (list.isEmpty()) {
			return null;
		}
		return list;
	}
	
	/**
	 * 利用日情報で、当該日時の予約状況の検索を行うメソッドです。
	 * @param date 利用日
	 * @return ReservationList型の予約データを返す。データがない場合は、nullを返す。
	 */
	public static ReservationList findByDate​(String date) {
		ReservationList list = new ReservationList();
		String sql = "SELECT * FROM reservation WHERE date = ?";
		try (Connection conn = DatabaseConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, date);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					ReservationBean rb = new ReservationBean(rs.getInt("id"), rs.getString("roomId"),
							rs.getString("date"),
							rs.getString("start"), rs.getString("end"), rs.getString("userId"));
					list.add(rb);
				}
			}
		} catch (SQLException e) {
			System.out.println("★ReservationDaoのfindByDateでエラー発生！");
			e.printStackTrace();
		}
		if (list.isEmpty()) {
			return null;
		}
		return list;
	}

	/**
	 * 予約番号から、当該予約の検索を行うメソッドです。
	 * @param id 予約番号
	 * @return ReservationBean型のidの予約データを返す。データがない場合は、nullを返す。
	 * @throws SQLException データベース・アクセス・エラーまたはその他のエラーに関する情報を提供する例外です。
	 */
	public static ReservationBean findById​(int id) throws SQLException {
		ReservationBean rb = null;
		String sql = "SELECT * FROM reservation WHERE id = ? ";

		try (Connection conn = DatabaseConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					rb = new ReservationBean(rs.getInt("id"), rs.getString("roomId"),
							rs.getString("date"),
							rs.getString("start"), rs.getString("end"), rs.getString("userId"));
				}
			} catch (SQLException e) {
				System.out.println("★ReservationDaoのfindById​でエラー発生！");
				e.printStackTrace();
			}
			return rb;
		}
	}

	/**
	 * 会議室IDから、当該会議室の予約状況の検索を行うメソッドです。
	 * @param roomId 会議室ID
	 * @return ReservationList型の予約データを返す。データがない場合は、nullを返す。
	 * @throws SQLException データベース・アクセス・エラーまたはその他のエラーに関する情報を提供する例外です。
	 */
	public static ReservationList findByRoomId​(String roomId) throws SQLException {
		ReservationList list = new ReservationList();
		String sql = "SELECT * FROM reservation WHERE roomId = ?";
		try (Connection conn = DatabaseConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, roomId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					ReservationBean searchResult = new ReservationBean(rs.getInt("id"), rs.getString("roomId"),
							rs.getString("date"),
							rs.getString("start"), rs.getString("end"), rs.getString("userId"));
					list.add(searchResult);
				}
				return list;
			} catch (SQLException e) {
				System.out.println("★ReservationDaoのfindByRoomIdでエラー発生！");
				e.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * 利用者IDにて、当該人物の予約状況の検索を行うメソッドです。
	 * @param userID 利用者ID
	 * @return ReservationList型の予約データを返す。データがない場合は、nullを返す。
	 */
	public static ReservationList finduserID(String userID) {
		ReservationList list = new ReservationList();
		String sql = "SELECT * FROM reservation WHERE userId = ?";
		try (Connection conn = DatabaseConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userID);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					ReservationBean rb = new ReservationBean(rs.getInt("id"), rs.getString("roomId"),
							rs.getString("date"),
							rs.getString("start"), rs.getString("end"), rs.getString("userId"));
					list.add(rb);
				}
			}
		} catch (SQLException e) {
			System.out.println("★ReservationDaoのfinduserIDでエラー発生！");
			e.printStackTrace();
		}
		if (list.isEmpty()) {
			return list;
		}
		return list;
	}

	/**
	 * 会議室予約を新規登録する為のメソッドです。
	 * @param reservation 登録する予約データ
	 * @return テーブル「reservation」のデータ登録真偽
	 */
	public static boolean insert​(ReservationBean reservation) {
		String sql = "INSERT INTO reservation (roomId,date,start,end,userID) VALUES(?, ?, ?, ?, ?)";
		try (Connection conn = DatabaseConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, reservation.getRoomId());
			pstmt.setString(2, reservation.getDate());
			pstmt.setString(3, reservation.getStart());
			pstmt.setString(4, reservation.getEnd());
			pstmt.setString(5, reservation.getUserId());
			int ret = pstmt.executeUpdate();
			 if (ret == 0) {
		            return false;
		        }
		        try (ResultSet rs = pstmt.getGeneratedKeys()) {
		            if (rs.next()) {
		                int id = rs.getInt(1);
		                reservation.setId(id); 
		            }
		        }
		        return true;
		} catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			System.err.println("登録していないルームIDを入れているか日付や時間が重複しています。");
		} catch (SQLException e) {
			System.out.println("★ReservationDaoのinsertでエラー発生！");
			e.printStackTrace();
			System.err.println("SQLに関するエラーです。");
		}
		return false;
	}

	/**
	 * 予約番号から、当該予約を物理削除するメソッドです。
	 * @param reservation 削除する予約データ
	 * @return テーブル「reservation」のデータ削除真偽
	 */
	public static boolean delete​(ReservationBean reservation) {
		String sql = "DELETE FROM reservation WHERE id = ?";
		try (Connection conn = DatabaseConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, reservation.getId());
			int ret = pstmt.executeUpdate();
			return ret != 0;
		} catch (SQLException e) {
			System.out.println("★ReservationDaoのdeleteでエラー発生！");
			e.printStackTrace();
		}
		return false;
	}
}
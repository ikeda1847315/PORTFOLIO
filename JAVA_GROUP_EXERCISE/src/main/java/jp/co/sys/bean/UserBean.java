package jp.co.sys.bean;

import java.io.Serializable;

/**
 * UserBeanを定義する為のクラスです。
 * @author 池田喜一郎
 */
public class UserBean implements Serializable {
	/**
	 * 利用者ID（テーブルuser：id）
	 */
	private String id;
	/**
	 * 氏名（テーブルuser：name）
	 */
	private String name;
	/**
	 * パスワード（テーブルuser：password）
	 */
	private String password;
	/**
	 * 住所（テーブルuser：address）
	 */
	private String address;
	/**
	 * 管理者フラグ（テーブルuser：isAdmin）
	 */
	private int isAdmin;
	/**
	 * 削除フラグ（テーブルuser：isDeleted）
	 */
	private int isDeleted;
	/**
	 * ハッシュ化用のペッパーデータの定数です。
	 */
	private final String pepper = "NKkxGDiVF9i+V7m5Ww70YA==";
	/**
	 * ペッパーデータをbyte[]変換する用の定数データです。
	 */
	private final byte[] hash = pepper.getBytes();
	/**
	 * ハッシュ化を繰り返す回数を定義する定数です。
	 */
	private final int iterations = 65536;
	/**
	 * 出力bitを定義する定数です。（256bit＝32バイト）
	 */
	private final int keyLength = 256;
	/**
	 * 直列化用バージョン番号
	 */
	private static final long serialVersionUID = 1L;

	public UserBean() {
	}

	/**
	 * @param id userテーブルのテーブルのカラム「id」です。
	 * @param name userテーブルのテーブルのカラム「name」です。
	 * @param password userテーブルのテーブルのカラム「password」です。
	 * @param address userテーブルのテーブルのカラム「address」です。
	 * @param isAdmin userテーブルのテーブルのカラム「isAdmin」です。
	 */
	public UserBean(String address, String id, String name, String password, int isAdmin) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.address = address;
		this.isAdmin = isAdmin;
	}

	/**
	 * @param id userテーブルのテーブルのカラム「id」です。
	 * @param name userテーブルのテーブルのカラム「name」です。
	 * @param password userテーブルのテーブルのカラム「password」です。
	 * @param address userテーブルのテーブルのカラム「address」です。
	 * @param isAdmin userテーブルのテーブルのカラム「isAdmin」です。
	 * @param isDeleted userテーブルのテーブルのカラム「isDeleted」です。
	 */
	public UserBean(String address, String id, String name, String password, int isAdmin, int isDeleted) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.address = address;
		this.isAdmin = isAdmin;
		this.isDeleted = isDeleted;
	}

	/**
	 * @param id userテーブルのテーブルのカラム「id」です。
	 * @param password userテーブルのテーブルのカラム「password」です。
	 */
	public UserBean(String id, String password) {
		super();
		this.id = id;
		this.password = password;
	}

	/**
	 * 利用者IDを返します。
	 * @return String id 利用者ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * パスワードを返します。
	 * @return String password パスワード
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 氏名を返します。
	 * @return String name 氏名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 住所を返します。
	 * @return String address 住所
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 管理者フラグの値を返します。（一般：0 管理者：1）
	 * @return int isAdmin 管理者フラグ
	 */
	public int getIsAdmin() {
		return isAdmin;
	}

	/**
	 * 削除フラグの値を返します。（未削除：0 削除済：1）
	 * @return int isDeleted 削除フラグ
	 */
	public int getIsDeleted() {
		return isDeleted;
	}

	/**
	 * ペッパーデータをbyte[]変換した値を返します。
	 * @return  byte[]
	 */
	public byte[] getHash() {
		return hash;
	}

	/**
	 * ハッシュ化を繰り返す回数の値を返します。
	 * @return int
	 */
	public int getIterations() {
		return iterations;
	}

	/**
	 * 出力bitの値を返します。
	 * @return int
	 */
	public int getKeyLength() {
		return keyLength;
	}

	/**
	 * 利用者IDに値を代入するためのメソッドです。
	 * @param id String id 利用者ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 管理者フラグに値を代入するためのメソッドです。（一般：0 管理者：1）
	 * @param int isAdmin 管理者フラグ
	 */
	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 *このオブジェクトの文字列表現を返します。
	 *デバック用
	 *@return String 現在のフィールドに設定された文字列表現
	 */
	@Override
	public String toString() {
		return id + " " + name + " " + password + " 管理者フラグ:" + isAdmin + " " + address + "　削除フラグ：" + isDeleted + "\n";
	}
}
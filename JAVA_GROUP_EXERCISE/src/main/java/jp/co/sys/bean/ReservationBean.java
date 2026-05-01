package jp.co.sys.bean;

import java.io.Serializable;

/**
 * ReservationBeanを定義するクラスです。
 * @author 小山祐貴
 */
public class ReservationBean implements Serializable {
	/**
	 * 予約番号（テーブルreservation：id）
	 */
	private int id;
	/**
	 * 会議室ID（テーブルreservation：roomId）
	 */
	private String roomId;
	/**
	 * 利用日（テーブルreservation：date）
	 */
	private String date;
	/**
	 * 開始時刻（テーブルreservation：start）
	 */
	private String start;
	/**
	 * 終了時刻（テーブルreservation：end）
	 */
	private String end;
	/**
	 * 利用者ID（テーブルreservation：userId）
	 */
	private String userId;
	/**
	 * 直列化用バージョン番号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * デフォルトコンストラクタ
	 */
	public ReservationBean() {
	}

	/**
	 * フィールドを全設定するコンストラクタです。
	 * @param id reservationテーブルのカラム「id」です。
	 * @param roomId reservationテーブルのカラム「roomId」です。
	 * @param date reservationテーブルのカラム「date」です。
	 * @param start reservationテーブルのカラム「start」です。
	 * @param end reservationテーブルのカラム「end」です。
	 * @param userId reservationテーブルのカラム「userId」です。
	 */
	public ReservationBean(int id, String roomId, String date, String start, String end, String userId) {
		this.id = id;
		this.roomId = roomId;
		this.date = date;
		this.start = start;
		this.end = end;
		this.userId = userId;
	}

	/**
	 * 予約生成する為のコンストラクタです。
	 * @param roomId reservationテーブルのカラム「roomId」です。
	 * @param date reservationテーブルのカラム「date」です。
	 * @param start reservationテーブルのカラム「start」です。
	 * @param end reservationテーブルのカラム「end」です。
	 * @param userId reservationテーブルのカラム「userId」です。
	 */
	public ReservationBean(String roomId, String date, String start, String end, String userId) {
		this(0, roomId, date, start, end, userId);
		this.roomId = roomId;
		this.date = date;
		this.start = start;
		this.end = end;
		this.userId = userId;
	}

	/**
	 * 予約削除する為のコンストラクタです。
	 * @param id reservationテーブルのカラム「id」です。
	 */
	public ReservationBean(int id) {
		this.id = id;
	}

	/**
	 * 予約番号を返します。
	 * @return int id 予約番号
	 */
	public int getId() {
		return id;
	}

	/**
	 * 予約番号に値を代入するためのメソッドです。
	 * @param int id 予約番号
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 会議室IDを返します。
	 * @return String roomId 会議室ID
	 */
	public String getRoomId() {
		return roomId;
	}

	/**
	 * 利用日を返します。
	 * @return String date 利用日
	 */
	public String getDate() {
		return date;
	}

	/**
	 * 開始時刻を返します。
	 * @return String start 開始時刻
	 */
	public String getStart() {
		return start;
	}

	/**
	 * 終了時刻を返します。
	 * @return String end 終了時刻
	 */
	public String getEnd() {
		return end;
	}

	/**
	 * 利用者IDを返します。
	 * @return String userId 利用者ID
	 */
	public String getUserId() {
		return userId;
	}
	
	/**
	 *このオブジェクトの文字列表現を返します。
	 *デバック用
	 *@return String 現在のフィールドに設定された文字列表現
	 */
	@Override
	public String toString() {
		return id + " " + date + " " + start + " " + end + " " + userId + "\n";
	}
}
package jp.co.sys.stub.hasebe;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jp.co.sys.bean.ReservationBean;
import jp.co.sys.bean.RoomBean;
import jp.co.sys.bean.UserBean;
import jp.co.sys.dao.ReservationDao;
import jp.co.sys.dao.RoomDao;
import jp.co.sys.dao.UserDao;
import jp.co.sys.util.RoomList;


public class MeetingRoom2 implements Serializable {
	//フィールド
	private static final long serialVersionUID = 1L;
	private static int INTERVAL = 60;
	private static String[] PERIOD = {"09:00", "10:00", "11:00", "12:00","13:00", "14:00", "15:00", "16:00"};
	private String date;
	private RoomList rooms;
	private UserBean user;
	
	//コンストラクタ
	public MeetingRoom2() {
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.date =currentDate.format(dateTimeFormatter); 
	}
	
	
	//メソッド
	/**
	*会議室予約システムの利用日を返します。
	*@return 利用日
	*/
	public String getDate() {
		return date;
	}
	
	/**
	*利用日更新
	*会議室予約システムの利用日を設定します。
	*@param 利用日
	*/
	public void	setDate​(String date) {
		this.date = date;
	}
	
	/**
	*利用会議室取得
	*会議室IDがroomIdの会議室を返します。
	*@return RoomList 会議室(見つからない場合は、nullを返却)
	*/
	public RoomList getRooms() {
		return rooms;
	}
	/**
	*会議室予約システムにログインしている利用者を返します。
	*@return UserBean 利用者
	*/
	public UserBean	getUser() {
		return user;
	}
	/**
	*利用時間帯の配列を返す。
	*@return String[] 開始時刻の配列
	*/
	public static String[] getPeriod() {
		return PERIOD;
	}
	/**
	*利用会議室取得
	*会議室IDがroomIdの会議室を返します。
	*@param roomId 会議室ID
	*@return RoomBean 会議室(見つからない場合は、nullを返却)
	*/
	public RoomBean	getRoom​(String roomId) {
		for(RoomBean room:rooms) {
			if(room.getId().equals(roomId)) {
				return room;
			}
		}
		return null;
	}
	/**
	*roomIdの会議室が配列に格納されている添字を返します。
	*@param roomId 会議室ID
	*@return int 配列の添字
	*@throws java.lang.IndexOutOfBoundsException 会議室が存在しない場合
	*/
	@SuppressWarnings("unlikely-arg-type")
	private int	roomIndex​(String roomId) throws IndexOutOfBoundsException {
		if (rooms.contains(roomId)) {
			int roomIndexNum = rooms.indexOf(roomId);
		return roomIndexNum;
		}
		throw new IndexOutOfBoundsException();
	}
	/**
	*利用開始時刻に対応する利用時間帯の添え字を計算します。
	*@param start String 利用開始時刻
	*@return int 時間帯番号
	*@throws java.lang.IndexOutOfBoundsException 利用時間帯の範囲外
	*/
	private int startPeriod​(String start) throws IndexOutOfBoundsException {
		int periodIndex = 0;
		for (int i=0;i<PERIOD.length;i++) {
			if(PERIOD[i].equals(start)) {
				periodIndex = i;
				return periodIndex;
			}
		}
		throw new IndexOutOfBoundsException();
	}
	/**
	*会議室予約システムの利用日における予約状況を返します。
	*@return ReservationBean[][] 会議室，時間帯ごとの予約状況
	*/
	public ReservationBean[][] getReservations(){
		int roomSize = rooms.size();
		ReservationBean reservations[][] = new ReservationBean [roomSize][PERIOD.length];
		List<ReservationBean> reserveList = ReservationDao.findByDate​(this.date);
		for(ReservationBean reserve:reserveList) {
			int roomInd = roomIndex​(reserve.getRoomId());
			int startInd = startPeriod​(reserve.getStart());
			reservations[roomInd][startInd] = reserve;
		}
		return reservations;
	}
	/**
	*認証
	*会議室予約システムにログインします。
	*@param id String 利用者ID , password String パスワード
	*@return ログインできた場合はtrue，それ以外の場合false
	*/
	public boolean login​(String id, String password) {
		UserBean result = UserDao.certificate​(id,password);
		if(result != null) {
			this.user = result;
			return true;
		} else {
			return false;
		}
	}
	/**
	*予約生成
	*予約日で会議室と時間帯を指定した会議室予約情報を生成します。また、開始時刻を基に終了時刻を生成し利用する。
	*@param roomId String 会議室ID, start String 利用開始時刻(HH:mm形式で受け取る事を想定)
	*@return ReservationBean 会議室予約情報
	*/
	public ReservationBean createReservation​(String roomId, String start) {
		LocalTime startTime = LocalTime.parse(start);
		LocalTime endTime = startTime.plusMinutes(INTERVAL);
		String end = endTime.toString();
		String userId = user.getId();//ここわからなさすぎるて
		ReservationBean reservation = new ReservationBean(roomId, date, start, end, userId);
		return reservation;
	}
	/**
	*予約登録
	*会議室予約情報で会議室Daoを利用し、予約します。
	*@param reservation ReservationBean 会議室予約情報
	*@return 戻値の説明
	*@throws java.lang.Exception 予約ができない場合に次のメッセージの例外を投げます。
	*			予約済みの場合："既に予約されています"
	*			現在時刻が予約時間を過ぎている場合："時刻が過ぎているため予約できません"
	*/
	public void	reserve​(ReservationBean reservation) throws Exception {
		LocalDateTime localDateTime = LocalDateTime.now();
		
		LocalDate reserveDate = LocalDate.parse(reservation.getDate());
		LocalTime reserveTime = LocalTime.parse(reservation.getStart());
		
		LocalDateTime reserveDateTime = LocalDateTime.of(reserveDate, reserveTime);
		
		if(reserveDateTime.isBefore(localDateTime)) {
			throw new Exception("時刻が過ぎているため予約できません");
		}
		
		List<ReservationBean> reserveList = ReservationDao.findByDate​(this.date);
		for(ReservationBean rs:reserveList) {
			String reservedRoomId = rs.getRoomId();
			if(reservedRoomId.equals(reservation.getRoomId())&&rs.getDate().equals(reservation.getStart())&&rs.getIsDeleted()==0){
				throw new Exception("既に予約されています");
			}
		}
				
		ReservationDao.insert​(reservation);	
	}
	/**
	*予約キャンセル
	*会議室予約情報で会議室をキャンセルします。
	*@param reservation ReservationBean 会議室予約情報
	*@throws java.lang.Exception - キャンセルができない場合に次のメッセージの例外を投げます。 
	*			キャンセル済みの場合："既にキャンセルされています" 
	*			現在時刻が予約時間を過ぎている場合："時刻が過ぎているためキャンセルできません"
	*/
	public void cancel​(ReservationBean reservation) throws Exception {
		LocalDateTime localDateTime = LocalDateTime.now();
		
		LocalDate reserveDate = LocalDate.parse(reservation.getDate());
		LocalTime reserveTime = LocalTime.parse(reservation.getStart());
		
		LocalDateTime reserveDateTime = LocalDateTime.of(reserveDate, reserveTime);
		
		if(reserveDateTime.isBefore(localDateTime)) {
			throw new Exception("時刻が過ぎているためキャンセルできません");
		}
		
		ReservationBean cancelReserve = ReservationDao.findById​(reservation.getId());
			if(cancelReserve.getIsDeleted()==1){
			throw new Exception("既にキャンセルされています");
			
		}
		ReservationDao.delete​(reservation);	
	}
	
	
	

//	このメソッドほしい！
	public RoomList getRoomList() {
	    return this.rooms = RoomDao.findAll();
	}
	
	/**
	*このオブジェクトの文字列表現を返します。
	*デバッグ用
	*@return String 会議室予約システムの文字列表現
	*/
	
	public String toString() {
		return user.toString()+rooms.toString()+this.date;
	}
}

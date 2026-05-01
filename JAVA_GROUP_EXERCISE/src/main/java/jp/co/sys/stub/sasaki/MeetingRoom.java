import java.io.Serializable;
//

import jp.co.sys.bean.ReservationBean;

public class MeetingRoom implements Serializable {

	private static final long serialVersionUID = 1L;

	//フィールド
	private String date;
	//開始時間の配列
	private String[] period = {
			"09:00", "10:00", "11:00", "12:00"
	};
	//会議室の一覧
	private RoomBean[] rooms = {
			new RoomBean("A", "会議室A"),
			new RoomBean("B", "会議室B"),
			new RoomBean("C", "会議室C")
	};
	//メソッド
	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public String[] getPeriod() {
		return period;
	}

	public RoomBean[] getRooms() {
		return rooms;
	}

	// 仮の予約状況（本当はResevationBean型）
	public String[][] getReservations() {
	    	
	    	String[][] reservation =new String[][] {
	            {"〇", "×", "〇", "〇"},
	            {"×", "〇", "〇", "×"},
	            {"〇", "〇", "×", "〇"}
	    	
	    	};
	        return reservation;
	    	
	    }
	//予約情報の生成をする
	public ReservationBean createReservation(String roomId, String start) {

		String end = "仮の終了時間";
		ReservationBean reservation = new ReservationBean(

				roomId,
				this.date="2026/01/30",
				start,
				end,
				"testUser");
		return reservation;

	}

	public RoomBean getRoom(String roomId) {
		for (RoomBean room : rooms) {
			if (room.getId().equals(roomId)) {
				return room;
			}
		}
		return null;
	}
	class User {
	    String name="テスト";
	}
	User user=new User();
	
	public void cancel​(ReservationBean reservation) throws Exception {
		//例外処理入れてみたが働かず
		if (reservation==null) {
			throw new Exception("すでに、キャンセルされています。");
		}else if (reservation.equals("2")) {
			throw new Exception("キャンセル可能時間を過ぎています。");
		}else if (reservation.equals("3")) {
			throw new Exception("原因不明エラー");
		}
	}

}	
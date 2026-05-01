package jp.co.sys.stub.asano;
//テスト終わったら変える！！！！

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
		int id = 666;
		String end = "仮の終了時間";
		int isDeleted =0;
		ReservationBean reservation = new ReservationBean(
                
				id,
				roomId,
				this.date,
				start,
				end,
				"testUser",
				isDeleted);
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

}

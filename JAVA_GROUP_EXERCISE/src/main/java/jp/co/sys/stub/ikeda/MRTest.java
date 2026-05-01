package jp.co.sys.stub.ikeda;

import jp.co.sys.bean.MeetingRoom;
import jp.co.sys.bean.ReservationBean;


public class MRTest {

	public static void main(String[] args) {
//		RoomList roomlist = new RoomList();
//		String sql = "SELECT * FROM room";
//		try (Connection db = DatabaseConnectionProvider.getConnection();
//				PreparedStatement pstmt = db.prepareStatement(sql);
//				ResultSet rs = pstmt.executeQuery()) {
//			while (rs.next()) {
//				String roomid = rs.getString("id");
//				String roomname = rs.getString("name");
//				RoomBean rb = new RoomBean(roomid, roomname);
//				roomlist.add(rb);
//				System.out.println(rb);
//				}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		MeetingRoom mr = new MeetingRoom();
//		//		RoomBean aa = mr.getRoom​("0201");　OK
//		ReservationBean[][] aa = mr.getReservations();
//		System.out.println(aa);
//		//  MeetingRoom()は、ヌルポがでるけど、user.toString()に値が入れば大丈夫。
		
		MeetingRoom mr = new MeetingRoom();
		ReservationBean[][] reservations = mr.getReservations();
		System.out.println(reservations);
		{
		}

}}
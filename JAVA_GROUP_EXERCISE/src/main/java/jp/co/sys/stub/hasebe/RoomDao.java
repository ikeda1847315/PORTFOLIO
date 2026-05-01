package jp.co.sys.stub.hasebe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sys.bean.RoomBean;
import jp.co.sys.util.DatabaseConnectionProvider;
import jp.co.sys.util.RoomList;

public class RoomDao {

	public static RoomList findAll() {
		RoomList roomlist = new RoomList();
		String sql = "SELECT * FROM room";
		try (Connection db = DatabaseConnectionProvider.getConnection();
				PreparedStatement pstmt = db.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				String roomid = rs.getString("id");
				String roomname = rs.getString("name");
				System.out.println("DEBUG: DBから取得しました -> " + roomid + ":" + roomname); // これを追加
				RoomBean rb = new RoomBean(roomid, roomname);
				roomlist.add(rb);
			}
			return roomlist;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}

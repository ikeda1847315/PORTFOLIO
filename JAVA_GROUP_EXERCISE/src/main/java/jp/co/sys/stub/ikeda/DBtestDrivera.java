package jp.co.sys.stub.ikeda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sys.util.DatabaseConnectionProvider;

public class DBtestDrivera {

	public static void main(String[] args) {
		try (Connection db = DatabaseConnectionProvider.getConnection()) {
			System.out.println("接続に成功！！");
		} catch (SQLException e) {
			System.out.println("接続に失敗：" + e.getMessage());
		}
		try (Connection db = DatabaseConnectionProvider.getConnection();
				PreparedStatement pstmt = db.prepareStatement("SELECT database(), current_user();");
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				String room = rs.getString(1);
				String currentUser = rs.getString(2);
				System.out.println("接続先DB：" + room);
				System.out.println("ログインユーザー：" + currentUser);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//	https://qiita.com/ha_ru/items/2e62b3c03b4a0d747018
}
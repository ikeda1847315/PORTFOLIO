package jp.co.sys.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * データベース接続を行う為のクラスです。
 * @author 池田喜一郎
 */
public class DatabaseConnectionProvider implements DatabaseConfig {
	private DatabaseConnectionProvider() {
	}

	public static Connection getConnection() throws SQLException {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("ドライバがありません");
			return null;
		}
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}
}
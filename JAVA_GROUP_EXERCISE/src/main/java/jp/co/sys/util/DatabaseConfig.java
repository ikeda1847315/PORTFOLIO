package jp.co.sys.util;

/**
 * データベース接続の内容を記載したインターフェースです。
 * @author 池田喜一郎
 */
public interface DatabaseConfig {
	String DRIVER = "com.mysql.cj.jdbc.Driver";
	String URL = "jdbc:mysql://localhost:3306/meetingroomb";
	String USER = "user";
	String PASSWORD = "pass";
}
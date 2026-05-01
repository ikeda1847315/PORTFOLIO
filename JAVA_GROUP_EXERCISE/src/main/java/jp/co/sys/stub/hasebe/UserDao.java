package jp.co.sys.stub.hasebe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jp.co.sys.bean.UserBean;
import jp.co.sys.util.DatabaseConnectionProvider;
import jp.co.sys.util.UserList;

public class UserDao {

	public static UserList findAll() throws Exception{
		UserList list = new UserList();
		String sql = "SELECT * FROM user WHERE isDeleted = 0";
		try (Connection db = DatabaseConnectionProvider.getConnection();
				PreparedStatement pstmt = db.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			
			while (rs.next()) {
				String userId = rs.getString("id");
				String userPassword = rs.getString("password");
				String userName = rs.getString("name");
				String userAddress = rs.getString("address");
				String userAdmin = rs.getString("isAdmin");
				UserBean ub = new UserBean(userAddress, userId, userName, userPassword, userAdmin);
				list.add(ub);
				
			}
			return list;
		}
	}
	
}

package jp.co.sys.stub.ikeda;

import jp.co.sys.bean.UserBean;
import jp.co.sys.dao.UserDao;
import jp.co.sys.util.UserList;

public class UserInsert {
	public static void main(String[] args) throws Exception {
		UserBean piyo = new UserBean("なんとか県", "2500050", "追加太郎","Ikeda32602", 0);
		boolean passingstatus = UserDao.insert​(piyo);
		System.out.println(passingstatus);
		UserList rb = UserDao.findAll();
		System.out.println(rb);
	}
}
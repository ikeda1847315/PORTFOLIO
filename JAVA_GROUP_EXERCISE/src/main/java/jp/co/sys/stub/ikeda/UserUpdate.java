package jp.co.sys.stub.ikeda;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import jp.co.sys.bean.UserBean;
import jp.co.sys.dao.UserDao;
import jp.co.sys.util.UserList;

public class UserUpdate {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
		UserBean piyo = new UserBean("なんか県", "2500050", "修正さん", "333333", 0);
		boolean passingstatus = UserDao.update(piyo);
		System.out.println(passingstatus);
		UserList rb = UserDao.findAll();
		System.out.println(rb);
	}
}
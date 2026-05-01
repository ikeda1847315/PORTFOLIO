package jp.co.sys.stub.ikeda;

import jp.co.sys.dao.UserDao;

public class UDAdmin {

	public static void main(String[] args) {
		int aa= UserDao.findAdmin();
		System.out.println(aa);
	}
}

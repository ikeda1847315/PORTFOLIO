package jp.co.sys.stub.ikeda;

import jp.co.sys.bean.UserBean;
import jp.co.sys.dao.UserDao;

public class RoomFindIdStub {

	public static void main(String[] args) {
//		RoomBean name = RoomDao.findId("0201");
		UserBean name = UserDao.findById("2500001");
		System.out.println(name);
		System.out.println(name.getClass().getName()); //変数のデータ型を確認する
	}
}

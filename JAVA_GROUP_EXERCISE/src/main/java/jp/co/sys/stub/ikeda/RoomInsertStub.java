package jp.co.sys.stub.ikeda;

import java.util.List;

import jp.co.sys.bean.RoomBean;
import jp.co.sys.dao.RoomDao;

public class RoomInsertStub {
	public static void main(String[] args) {
		RoomBean piyo = new RoomBean("1111", "登録した会議室");
		boolean passingstatus = RoomDao.insert(piyo);
		System.out.println(passingstatus);
		List<RoomBean> rb = RoomDao.findAll();
		System.out.println(rb);
	}
}
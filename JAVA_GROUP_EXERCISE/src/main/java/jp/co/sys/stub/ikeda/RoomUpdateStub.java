package jp.co.sys.stub.ikeda;

import java.util.List;

import jp.co.sys.bean.RoomBean;
import jp.co.sys.dao.RoomDao;

public class RoomUpdateStub {
	public static void main(String[] args) {
		RoomBean piyo = new RoomBean("1111", "変更できた会議室");
		boolean passingstatus = RoomDao.update(piyo);
		System.out.println(passingstatus);
		List<RoomBean> rb = RoomDao.findAll();
		System.out.println(rb);
	}
}
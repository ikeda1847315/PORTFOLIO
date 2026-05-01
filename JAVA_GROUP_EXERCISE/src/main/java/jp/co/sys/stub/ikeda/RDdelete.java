package jp.co.sys.stub.ikeda;

import java.util.List;

import jp.co.sys.bean.ReservationBean;
import jp.co.sys.dao.ReservationDao;

public class RDdelete {

	public static void main(String[] args) {
		ReservationBean rb = new ReservationBean(1);
		boolean aaa = ReservationDao.delete​(rb);
		System.out.println(aaa);
		
		List<ReservationBean> bb = ReservationDao.findAll();
		System.out.println(bb);
	}

}

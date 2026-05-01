package jp.co.sys.stub.koyama;

import jp.co.sys.bean.ReservationBean;
import jp.co.sys.dao.ReservationDao;

public class test {

	public static void main(String[] args) {

//				List<ReservationBean> rb = ReservationDao.findByDate​("2026-01-11");
//				System.out.println(rb);

//		ReservationBean rb = ReservationDao.findById​(8);
//		System.out.println(rb);

//		List<ReservationBean> rb = ReservationDao.finduserID("2500000");
//		System.out.println(rb);

//		List<ReservationBean> rb = ReservationDao.findAll();
//		System.out.println(rb);

		
//				ReservationBean hoge = new ReservationBean("0302", "2026-01-11", "09:00:00", "10:00:00", "2500003");
//				boolean bl = ReservationDao.insert​(hoge);
//				System.out.println(bl);
		//
				ReservationBean huga = new ReservationBean("0201", "2026-01-11", "09:00:00", "10:00:00", "2500003");
				boolean dl = ReservationDao.delete​(huga);
				System.out.println(dl);

	}
}

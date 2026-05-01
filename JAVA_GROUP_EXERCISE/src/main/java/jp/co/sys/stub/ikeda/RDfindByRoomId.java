package jp.co.sys.stub.ikeda;

import java.sql.SQLException;

import jp.co.sys.bean.ReservationBean;
import jp.co.sys.dao.ReservationDao;
import jp.co.sys.util.ReservationList;

public class RDfindByRoomId {

	public static void main(String[] args) throws SQLException {
//		ReservationBean rb = new ReservationBean();
		ReservationList rl = ReservationDao.findByRoomId​("0201");
		for (ReservationBean reservationBean : rl) {
			System.out.println(reservationBean);
		}
	}
}

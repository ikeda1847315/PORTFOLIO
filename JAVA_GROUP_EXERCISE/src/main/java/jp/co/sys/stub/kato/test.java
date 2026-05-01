package jp.co.sys.stub.kato;

import jp.co.sys.bean.UserBean;
import jp.co.sys.dao.UserDao;
import jp.co.sys.util.UserList;

public class test {
	public static void main(String[] args) {

		//			ユーザ検索のテスト
		//				UserBean bl=UserDao.certificate​("2500002", "111111");
		//				System.out.println(bl);
		//				System.out.println();

		////		ユーザ追加のテスト
		////				UserBean huga = new UserBean("大阪府", "2500010", "テスト太郎", "111111", "0", "0");
		////				boolean dl = UserDao.insert​(huga);
		////				System.out.println(dl);
		////				System.out.println();

		//		ユーザ削除フラグテスト
		//		boolean huga = UserDao.delete​("2500001");
		//		System.out.println(huga);
		//		UserBean bl=UserDao.certificate​("2500001", "111111");
		//		System.out.println(bl);

		//		NowIdだけで検索するテスト
		//		UserList huga = UserDao.getNowId("2500001");
		//		System.out.println(huga);

		//		ユーザテーブル全検索するテスト
		UserList huga = UserDao.findAll();
		System.out.println("戻値型：" + huga.getClass().getName());
		for (UserBean pika : huga) {
			System.out.print(pika.getId() + " ");
			System.out.print(pika.getName() + " ");
			System.out.print(pika.getPassword() + " ");
			System.out.print(pika.getAddress() + " ");
			System.out.print("管" + pika.getIsAdmin() + " ");
			System.out.println("削" + pika.getIsDeleted());
		}

	}

}

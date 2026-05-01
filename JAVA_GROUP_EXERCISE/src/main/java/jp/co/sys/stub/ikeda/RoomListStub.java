package jp.co.sys.stub.ikeda;

import jp.co.sys.bean.RoomBean;
import jp.co.sys.dao.RoomDao;
import jp.co.sys.util.RoomList;

public class RoomListStub {
	public static void main(String[] args) {
		RoomList list = RoomDao.findAll();
		for (RoomBean room : list) {
            System.out.print(room.getId()+" ");
            System.out.println(room.getName());
        }
	}
}

//　ごり押しできた
//try (Connection db = DatabaseConnectionProvider.getConnection();
//		PreparedStatement pstmt = db.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
//				ResultSet.CONCUR_READ_ONLY);
//		ResultSet rs = pstmt.executeQuery()) {
//ResultSetMetaData rsmd = rs.getMetaData(); //ResultSet オブジェクトの列の型とプロパティに関する情報を取得する
//int column = rsmd.getColumnCount(); //この ResultSet オブジェクトの列数を返します。
//rs.last(); //カーソルをこのResultSetオブジェクト内の最終行に移動します。
//int row = rs.getRow(); //現在の行の番号を取得します。
//int arraycount = row * column; //最終行の行数×全部の列数で、配列に必要な数値を取得【できる想定笑）
//System.out.println(row + "|" + column + "|" + arraycount);
//rs.beforeFirst(); //カーソルをこのResultSetオブジェクトの先端、つまり先頭行の直前に移動します。
package jp.co.sys.stub.asano;
//テスト終わったら変える！！！！

import java.io.Serializable;

public class RoomBean implements Serializable{
	private static final long serialVersionUID = 1L;
	String id;
	String name;
	
	public RoomBean() {
		super();
	}

	public RoomBean(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return super.toString();
//		戻り値:String 会議室の文字列表現 が不明
	}
}

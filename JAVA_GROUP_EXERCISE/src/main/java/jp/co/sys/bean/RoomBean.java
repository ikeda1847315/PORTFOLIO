package jp.co.sys.bean;

import java.io.Serializable;

/**
 * RoomBeanを定義するクラスです。
 * @author 池田喜一郎
 */
public class RoomBean implements Serializable {
	/**
	 * 会議室ID（テーブルroom：id）
	 */
	private String id;
	/**
	 * 会議室名（テーブルroom：name）
	 */
	private String name;
	/**
	 * 直列化用バージョン番号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * デフォルトコンストラクタ：直列化復元時に使用します。
	 * 
	 */
	public RoomBean() {
		super();
	}

	/**
	 * 会議室検索用のコンストラクタです。
	 * @param String id 会議室ID
	 * @param String name 会議室名
	 */
	public RoomBean(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	/**
	 * 会議室登録用のコンストラクタです。
	 * @param String name 会議室名
	 */
	public RoomBean(String name) {
		super();
		this.name = name;
	}

	/**
	 * 会議室IDを返します。
	 * @return String id 会議室ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * 会議室名を返します。
	 * @return String name 会議室名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 会議室IDに値を代入するためのメソッドです。
	 * @param String id 会議室ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 *このオブジェクトの文字列表現を返します。
	 *デバック用
	 *@return String 現在のフィールドに設定された文字列表現
	 */
	@Override
	public String toString() {
		return id + " " + name + "\n";
	}
}
package jp.co.sys.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.sys.bean.MeetingRoom;
import jp.co.sys.bean.ReservationBean;

/**
 * 会議室予約を生成するサーブレットです。
 * @author 浅野裕子
 */
@WebServlet("/reserveCreateServlet")
public class ReserveCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 *get通信の場合はログイン画面へリダイレクト
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
		return;

	}

	/**
	 *ログインの成功しているアクセス者からのリクエストを受け取り予約する会議室を
	 *sessionに登録します
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//リクエストで取得した文字をUTF-8へ
		request.setCharacterEncoding("UTF-8");

		
		//リクエストパラメータの取得
		String roomId = request.getParameter("roomId");//会議室ID
		String start = request.getParameter("time");//利用開始時刻

		//sessionの取得
		HttpSession session = request.getSession();

		//sessionからmeetingRoomを取得
		MeetingRoom meetingRoom = (MeetingRoom) session.getAttribute("meetingRoom");
		
		// セッション切れ or ログインしていなければログイン画面へ
		if (meetingRoom == null) {
		    
		    response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
		    return;
		}


		//Meetingroomに予約情報の生成を依頼する
		ReservationBean reservation = meetingRoom.createReservation​(roomId, start);
		
		try {
			//reservation:予約情報が nullかチェック
            if (reservation == null) {
                throw new Exception("reservation が session に存在しません");
            }
						
		
		//sessionに戻す
		session.setAttribute("reservation", reservation);
		//sessionに予約する会議室の情報をセット
		session.setAttribute("room", meetingRoom.getRoom​(roomId));
		// ===== コンソール確認用 =====
		System.out.println("MeetingRoom.date = " + meetingRoom.getDate());
		System.out.println("roomId = " + roomId);
		System.out.println("start = " + start);
		System.out.println("reservation = " + reservation);

		//元の画面に戻る
		request.getRequestDispatcher("/jsp/reserveConfirm.jsp")
				.forward(request, response);
		} catch (Exception e) {
			//登録失敗したら予約エラー画面へ
			request.getRequestDispatcher("/jsp/reserveError.jsp")
					.forward(request, response);
		}

	}

}

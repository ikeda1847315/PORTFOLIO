package jp.co.sys.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.sys.bean.MeetingRoom;
import jp.co.sys.bean.ReservationBean;

/**
 * キャンセルしたい予約情報を生成するサーブレットです。
 * @author 佐々木美智子
 */

@WebServlet("/CancelCreateServlet")
public class CancelCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
/**get通信の場合はログイン画面へリダイレクト */
		response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
	}

protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
/** セッション取得 */
		HttpSession session = request.getSession();
/** 入力された情報取得 */

		request.setCharacterEncoding("UTF-8");
		String roomId = request.getParameter("roomId");
		String start = request.getParameter("time");
		String researvationId = request.getParameter("researvationId");
		int researvationIdInt = Integer.parseInt(researvationId);

		/** 会議室管理システム取得 */		
		MeetingRoom meetingRoom=(MeetingRoom) session.getAttribute("meetingRoom");
/** 取り消す予約情報を生成する */
		ReservationBean reservation = meetingRoom.createReservation​(roomId,start);
		reservation.setId(researvationIdInt);
		
//		reservation.setId(roomId);
/** 取り消す予約（会議室ID、開始時間）をセッションにセットする */
		session.setAttribute("reservation",reservation);
		session.setAttribute("room",meetingRoom.getRoom​(roomId));
		/** 予約確認画面へフォワード */
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/cancelConfirm.jsp");
		rd.forward(request, response);
		//		response.sendRedirect(request.getContextPath() + "/jsp/cancelConfirm.jsp");
	}
}
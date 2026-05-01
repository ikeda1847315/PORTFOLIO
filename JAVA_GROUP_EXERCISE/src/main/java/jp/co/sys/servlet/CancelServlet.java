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
 * 予約を削除し、予約成功時は予約完了画面に遷移し
 * 例外を捕捉した場合は，例外メッセージをリクエスト属性に
 * セットして予約エラー画面に遷移するサーブレットです。
 * @author 佐々木美智子
 */

@WebServlet("/CancelServlet")
public class CancelServlet extends HttpServlet {
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
		/** 会議室管理システム取得 */
		MeetingRoom meetingRoom=(MeetingRoom) session.getAttribute("meetingRoom");
		/** DBに記録するキャンセル予約 */
		ReservationBean reservation = (ReservationBean) session.getAttribute("reservation");
		/** キャンセル実行し、エラー発生する場合は理由をセット */
		String errorReason=null;
			try {
				meetingRoom.cancel​(reservation);
			} catch (Exception e) {
				errorReason=e.getMessage();
				request.setAttribute("errorReason", errorReason);
			}
		/** エラーなしの場合予約完了画面、エラーありの場合エラー画面へフォワード */
if (errorReason==null) {
	RequestDispatcher rd = request.getRequestDispatcher("/jsp/canceled.jsp");
	rd.forward(request, response);
}else {
	RequestDispatcher rd = request.getRequestDispatcher("/jsp/cancelError.jsp");
	rd.forward(request, response);
		}
	}
}
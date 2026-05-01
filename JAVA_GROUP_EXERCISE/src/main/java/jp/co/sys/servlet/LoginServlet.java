package jp.co.sys.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.sys.bean.MeetingRoom;

/**
 * ログイン処理を管理するサーブレットです。
 * @author 加藤博文
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//		 直接URLを叩かれた場合はログイン画面へ戻す
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// コンテキストパスを含めてリダイレクト
		response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
	}

	// JSPのフォームから送信された時の処理
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("userId");
		String password = request.getParameter("userPw");

		String nextPage;
		HttpSession session = request.getSession();
		//セッション時間の変更
		//		session.setMaxInactiveInterval(5);
		MeetingRoom meetingRoom = new MeetingRoom();

		try {
			boolean login = meetingRoom.login​(id, password);

			//        ミーティングルームのメソッドを呼び込む
			//		public boolean login​(String id, String password)
			//       ユーザ情報がヌルじゃないか？

			if (login == true) {
				//        	メニューJSPにページを飛ばす

				//UserBean user =  meetingRoom.getUser();

				//        	セッションを行う
				session.setAttribute("meetingRoom", meetingRoom);
				// ===== コンソール確認用 =====
				System.out.println("=== 予約登録内容 ===");
				System.out.println("日付: " + meetingRoom.getDate());
				System.out.println("部屋ID: " + meetingRoom.getUser());
				//System.out.println("開始: " + reservation.getStart());
				//System.out.println("終了: " + reservation.getEnd());
				//System.out.println("ユーザー");
				//System.out.println("isDeleted:" + reservation.getIsDeleted());
				//System.out.println("===================");

				nextPage = request.getContextPath() + "/jsp/menu.jsp";
			} else {
				//        		ログインJSPに飛ばす
				session.setAttribute("message", "IDかパスワードが違います");
				nextPage = request.getContextPath() + "/jsp/login.jsp";
				response.sendRedirect(nextPage);
				return;
			}
			//        	ページ先に情報を渡す
			response.sendRedirect(nextPage);
			return;
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

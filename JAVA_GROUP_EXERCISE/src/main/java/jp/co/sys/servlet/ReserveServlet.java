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
 * 予約を登録するサーブレットです
 * @author 浅野裕子
 */
@WebServlet("/ReserveServlet")
public class ReserveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// get通信の場合はログイン画面へリダイレクト
		response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//リクエストで取得した文字をUTF-8へ
		request.setCharacterEncoding("UTF-8");

		//sessionの取得
		HttpSession session = request.getSession();

		//会議室管理システムの取得
		MeetingRoom meetingRoom = (MeetingRoom) session.getAttribute("meetingRoom");
		
		//nullかどうかの判定なければエラー画面へ
		if (meetingRoom == null) {
		    request.setAttribute("errorReason", "会議室情報が取得できませんでした。");
		    RequestDispatcher rd = request.getRequestDispatcher("/jsp/reserveError.jsp");
		    rd.forward(request, response);
		    return;
		}
		
		// 予約情報の取得（ReservationBean）
        ReservationBean reservation = (ReservationBean) session.getAttribute("reservation");
        
        //予約情報の有無を判断
        if (reservation == null) {
            request.setAttribute("errorReason", "予約情報が取得できませんでした。");
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/reserveError.jsp");
            rd.forward(request, response);
            return;
        }

		// ===== コンソール確認用 =====
		System.out.println("=== 予約登録内容 ===");
		System.out.println("日付: " + reservation.getDate());
		System.out.println("部屋ID: " + reservation.getRoomId());
		System.out.println("開始: " + reservation.getStart());
		System.out.println("終了: " + reservation.getEnd());
		System.out.println("ユーザー");
		System.out.println("===================");

     

			try {
				//meetingroomに予約確定の依頼
				meetingRoom.reserve​(reservation);
				// 成功したらsessionへ登録し、完了画面へ
	            session.setAttribute("reserved", true);
	            request.getRequestDispatcher("/jsp/reserved.jsp").forward(request, response);


		} catch (Exception e) {
			
			//MeetingRoom.reseve（）の例外を受け取り、画面に表示
			request.setAttribute("errorReason", e.getMessage());
            request.getRequestDispatcher("/jsp/reserveError.jsp").forward(request, response);
        }
    }
}




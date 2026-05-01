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
 * 利用日を変更するサーブレットです。
 * @author 浅野裕子
 */
@WebServlet("/ChangeDateServlet")
public class ChangeDateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// URLから直接アクセスした場合はログイン画面へ
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
		return;
	}

	
	//入力画面・キャンセル入力画面から値が送られて来た時の処理
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//リクエストで取得した文字をUTF-8へ
		request.setCharacterEncoding("UTF-8");
		//リクエストパラメータの取得
		String date = request.getParameter("date");//利用日
		String page = request.getParameter("page");//遷移元
		
		//sessionの取得
		HttpSession session =request.getSession();
		
		//ログイン時に作られたmeetingRoomをsessionから取得
		MeetingRoom meetingRoom =(MeetingRoom)session.getAttribute("meetingRoom");

		// meetingRoomがnulか チェック
		if (meetingRoom == null) {
		    // ログイン画面へ戻す
		    response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
		    return;
		}
		
		//利用日がYYYY-MM-DDに一致しているかをチェックする
		if(date !=null &&date.matches("\\d{4}-\\d{2}-\\d{2}"))
 {
			meetingRoom.setDate​(date);
			//値が取れているかの確認
			System.out.println("MeetingRoom.date = " + meetingRoom.getDate());
						
		}
		//sessionに戻す
		session.setAttribute("meetingRoom", meetingRoom);
				
		//元の画面に戻る
		request.getRequestDispatcher("/jsp/"+ page)
		.forward(request, response);
		
		
	}

}

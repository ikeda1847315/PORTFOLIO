package jp.co.sys.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.sys.bean.MeetingRoom;
import jp.co.sys.bean.RoomBean;
import jp.co.sys.util.RoomList;

/**
 * 会議室の一覧表示および削除を制御するサーブレットです。
 * @author 長谷部広樹
 */
@WebServlet("/RoomAdminServlet")
public class RoomAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RoomAdminServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		MeetingRoom mr = (MeetingRoom) session.getAttribute("meetingRoom");	
		
		String roomId = request.getParameter("roomId");
		String roomName = request.getParameter("roomName");
		RoomBean room = new RoomBean(roomId, roomName);
		
		String action = request.getParameter("action");
		String nextPath = "/jsp/conferenceRoomList.jsp";
		
//		会議室の削除
		String message = "";

		if ("削除".equals(action)) {
			try {
				
				if(mr.deleteRoom(room)) {
					message = "削除しました。";
					mr.reloadRooms();

				} else {
					message = "削除に失敗しました。";
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				
				String errorMessage = e.getMessage();
				
				if("会議室が見つかりません".equals(errorMessage)) {
					message = "存在しない会議室です。";
					
//				} else if("予約があるため削除できません".equals(errorMessage)) {
//					message = "予約があるため削除できません。";

				} else {
					message = "削除できませんでした。";

				}
			}
			

		}
		RoomList list = mr.reloadRooms();
	
		request.setAttribute("message", message);
		request.setAttribute("list", list);
		
		request.getRequestDispatcher(nextPath).forward(request, response);
	}
}
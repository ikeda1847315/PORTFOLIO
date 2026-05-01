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

/**
 * 会議室の登録および編集を制御するサーブレットです。
 * @author 長谷部広樹
 */
@WebServlet("/RoomEditServlet")
public class RoomEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RoomEditServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		MeetingRoom mr = (MeetingRoom)session.getAttribute("meetingRoom");
		
		String action = request.getParameter("action");
		String title = request.getParameter("title");
		
		String roomId = request.getParameter("roomId");
		String roomName = request.getParameter("roomName");
		String roomFloor = request.getParameter("roomFloor");
		
		RoomBean room = new RoomBean(roomId, roomName);
		RoomBean addRoom = new RoomBean(roomFloor, roomName);
		
	    String nextPage = "";
		String message = "";
		

//		入力画面
		if ("編集".equals(action)) {
			title = "会議室編集";
			nextPage = "/jsp/conferenceRoomInput.jsp";
			
		} else if ("追加".equals(action)) {
			title = "会議室登録";
			nextPage = "/jsp/conferenceRoomInput.jsp";
			
		} else if ("決定".equals(action)) {
			
			if (roomName.length() > 20) {
				message += "会議室名は20文字以内、";
			} 
			if (roomFloor.length() > 2) {
				message += "階数は1-99の間、、";
			}
			if (roomFloor.matches(".*[-<>'\"&;].*") || roomName.matches(".*[<>'\"&; 　].*") ) {
				message += "半角記号・スペースを使用しない形式、";
			}
			
			if (message != "") {
				message += "で入力してください。";
				request.setAttribute("room", room);
				request.setAttribute("addRoom", addRoom);
				request.setAttribute("title", title);
				request.setAttribute("message", message);
				request.getRequestDispatcher("/jsp/conferenceRoomInput.jsp").forward(request, response);
				return;
			}
			
			nextPage = "/jsp/conferenceRoomConfirm.jsp";
			
			
		} else if ("戻る".equals(action)) {
			nextPage = "/jsp/conferenceRoomInput.jsp";
			
		} else if ("登録".equals(action)) {
			Boolean isSuccess = false;

			if("会議室編集".equals(title)) {
				try {
					isSuccess = mr.editRoom(room);
				} catch (Exception e) {
					e.printStackTrace();
					
					String errorMessage = e.getMessage();
					
					if("既に同じ名前で登録されています".equals(errorMessage)) {
						message = "既に同じ名前で登録されています。";
						
					} else {
						message = "変更できませんでした。";

					}
				}
				
			} else if ("会議室登録".equals(title)) {
				try {
					isSuccess = mr.addRoom(addRoom);
				} catch (Exception e) {
					e.printStackTrace();
					
					String errorMessage = e.getMessage();
					
					if("既に登録されています".equals(errorMessage)) {
						message = "既に登録されています";
						
					} else {
						message = "登録できませんでした。";

					}
				}
			}
			
			if (isSuccess) {
				nextPage = "/jsp/conferenceRoomed.jsp";
				mr.reloadRooms();
				
			} else {
				nextPage = "/jsp/conferenceRoomError.jsp";	
			}
			
		} else if ("一覧へ".equals(action)) {
			nextPage = "/jsp/conferenceRoomList.jsp";	
		}
		
		request.setAttribute("room", room);
		request.setAttribute("addRoom", addRoom);
		request.setAttribute("title", title);
		request.setAttribute("message", message);

		request.getRequestDispatcher(nextPage).forward(request, response);
	
	}

}

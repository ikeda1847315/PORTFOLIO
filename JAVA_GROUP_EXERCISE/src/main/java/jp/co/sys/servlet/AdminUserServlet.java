package jp.co.sys.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.sys.bean.MeetingRoom;
import jp.co.sys.bean.UserBean;
import jp.co.sys.util.UserList;

/**
 * 会員の一覧表示および削除を制御するサーブレットです。
 * @author 長谷部広樹
 */
@WebServlet("/AdminUserServlet")
public class AdminUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminUserServlet() {
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
		
		String userAddress = request.getParameter("userAddress");
		String userId = "会員情報編集".equals(action) ? mr.getUser().getId() : request.getParameter("userId");
		String userName = request.getParameter("userName");
		String userPw = request.getParameter("userPw");
		String userAdmin = request.getParameter("userAdmin");
		
		// 確認の「戻る」ボタン押しても、チェック維持する
		String checked = "on".equals(userAdmin) ? "checked" : "";
		userAdmin = "on".equals(userAdmin) ? "管理者" : "一般会員";
		int userAdminInt = "管理者".equals(userAdmin) ? 1 : 0;
		
		UserBean user = new UserBean(userAddress, userId, userName, userPw, userAdminInt);
		
		String nextPath = "/jsp/userList.jsp";
		String message = "";
//		int cancelFlag = mr.getUser().getIsAdmin();
//		String adminFlag = request.getParameter("adminFlag");
		
		if ("削除".equals(action)) {
			
			try {
				//成功したらtrue
				if(mr.deleteUser(user)) {
					message = "削除しました。";
					mr.getUsers();
				} else {
					
					message = "削除できませんでした。";
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				
				String errorMessage = e.getMessage();
				
				if("存在しないユーザーです".equals(errorMessage)) {
					message = "存在しないユーザーです。";
					
				} else if("既に削除されています".equals(errorMessage)) {
					message = "既に削除されています。";
					
					
				} else if("予約があるため削除できません".equals(errorMessage)) {
					message = "予約があるため削除できません。";
				
				} else {
					message = "削除できませんでした。";

				}
			}
		}
		
		if ("退会する".equals(action)) {
			
			try {
				if(mr.deleteUser(user)) {
					message = "退会しました。";
					nextPath = "/jsp/login.jsp";
				  //session破棄
				  session.invalidate();
					
				} else {
					nextPath = "/jsp/menu.jsp";
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				
				String errorMessage = e.getMessage();
				
				if("存在しないユーザーです".equals(errorMessage)) {
					message = "存在しないユーザーです。";
					nextPath = "/jsp/login.jsp";					
					
				} else if("既に削除されています".equals(errorMessage)) {
					message = "既に退会されています。";
					nextPath = "/jsp/login.jsp";					
					
				} else if("予約があるため削除できません".equals(errorMessage)) {
					message = "予約があるため退会できません。";
					nextPath = "/jsp/menu.jsp";
				
				} else {
					message = "退会できませんでした。";
					nextPath = "/jsp/menu.jsp";
				}
			}	
		}
//		最新の会員一覧取得
		UserList list = mr.getUsers();
		
		request.setAttribute("message", message);
		request.setAttribute("list", list);
		request.setAttribute("user", user);
		request.setAttribute("checked", checked);
//		request.setAttribute("cancelFlag", cancelFlag);
//		request.setAttribute("adminFlag", adminFlag);
		
		request.getRequestDispatcher(nextPath).forward(request, response);
	}

}

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

/**
 * 会員の新規登録を制御するサーブレットです。
 * @author 長谷部広樹
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegistrationServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		MeetingRoom mr = (MeetingRoom)session.getAttribute("meetingRoom");
		
		String action = request.getParameter("action");
		
		String userAddress = request.getParameter("userAddress");
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String userPw = request.getParameter("userPw");
		String userAdmin = request.getParameter("userAdmin");
		String checked = "on".equals(userAdmin) ? "checked" : "";
		userAdmin = "on".equals(userAdmin) ? "管理者" : "一般会員";
		int userAdminInt = "管理者".equals(userAdmin) ? 1 : 0;
		
		UserBean user = new UserBean(userAddress, userId, userName, userPw, userAdminInt);
		
		String nextPage = "jsp/registrationInput.jsp";
		String message = "";

		if ("決定".equals(action)) {
			
			// バリデーション
			if (userPw.length() > 10 || userPw.length() < 6 || !userPw.matches("^[a-zA-Z0-9]+$")) {
				message += "パスワードは、6文字から10文字の半角英数字のみ、";
			} 
			if (userName.length() > 10) {
				message += "氏名は10文字以内、";
			}
			if (userAddress.length() > 30) {
				message += "住所は30文字以内、";
			}
			if (userName.matches(".*[<>'\"&; 　].*") || userAddress.matches(".*[<>'\"&; 　].*") ) {
				message += "半角記号・スペースを使用しない形式、";
			}
			
			if (message != "") {
				message += "で入力してください。";
				request.setAttribute("user", user);
				request.setAttribute("checked", checked);
				request.setAttribute("message", message);
				request.getRequestDispatcher(nextPage).forward(request, response);
				return;
			}
			nextPage = "jsp/registrationConfirm.jsp";
			
		} else if ("戻る".equals(action)) {
			nextPage = "jsp/registrationInput.jsp";
			
		}  else if ("登録".equals(action)) {
			try {
				// 成功したらtrue
				if (mr.addUser(user)) {
					nextPage = "/jsp/registered.jsp";
					mr.getUsers();
					
				} else {
					nextPage = "/jsp/registrationError.jsp";
					message = "登録できませんでした。";
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				
				// meetingroomからthrowされたmessage
				String errorMessage = e.getMessage();
				
				if("既に登録されています".equals(errorMessage)) {
					message = "既にとうろくされています。";
				
				} else {
					message = "登録できませんでした。";

				}
			}
		}

		request.setAttribute("user", user);
		request.setAttribute("checked", checked);
		request.setAttribute("message", message);
		
		request.getRequestDispatcher(nextPage).forward(request, response);
	}

}

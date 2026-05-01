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
 * 登録ユーザーを編集する為のサーブレットです。
 * @author 長谷部広樹
 */
@WebServlet("/UserEditServlet")
public class UserEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		MeetingRoom mr = (MeetingRoom) session.getAttribute("meetingRoom");

		String action = request.getParameter("action");
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String userAddress = request.getParameter("userAddress");
		String userPw = request.getParameter("userPw");
		String userAdmin = request.getParameter("userAdmin");
		String adminFlag = request.getParameter("adminFlag");
		String transition = request.getParameter("transition");

		if ("会員情報編集".equals(action)) {
			UserBean self = mr.getUser();
			userId = self.getId();
			userName = self.getName();
			userAddress = self.getAddress();
			userPw = "";
			adminFlag = "0";
			transition = "会員情報編集";
			userAdmin = String.valueOf(mr.getUser().getIsAdmin());
			
		} else if ("編集".equals(action)) {
			adminFlag = "1";
			transition = "編集";
		}

		int userAdminInt = "on".equals(userAdmin) || "1".equals(userAdmin) ? 1 : 0;
		UserBean user = new UserBean(userAddress, userId, userName, userPw, userAdminInt);
		String checked = (userAdminInt == 1) ? "checked" : "";

		// 自分の情報であればtrue
		boolean isSelf = mr.getUser().getId().equals(userId);
		// 管理者フラグが1ならtrue
		boolean isFromList = "1".equals(adminFlag);

		// 管理者は自分自身の編集ボタンを非表示（チェックボックス）
		String adminConfigClass = (isFromList && !isSelf) ? "" : "hidden";
		// 一般の会員かつメニューからの編集のみ表示（退会ボタン）
		String unsubscribeClass = (!isFromList && mr.getUser().getIsAdmin() == 0) ? "" : "hidden";

		String nextPage = "/jsp/editInput.jsp";
		String message = "";

		// バリデーション
		if ("決定".equals(action)) {
			if (userPw.length() > 10 || userPw.length() < 6 || !userPw.matches("^[a-zA-Z0-9]+$")) {
				message += "パスワードは、6文字から10文字の半角英数字のみ、";
			}
			if (userName.length() > 10) {
				message += "氏名は10文字以内、";
			}
			if (userAddress.length() > 30) {
				message += "住所は30文字以内、";
			}
			if (userName.matches(".*[<>'\"&; 　].*") || userAddress.matches(".*[<>'\"&; 　].*")) {
				message += "半角記号・スペースを使用しない形式、";
			}

			if (!"".equals(message)) {
				message += "で入力してください。";
			} else {
				nextPage = "/jsp/edittedConfirm.jsp";
			}
			
		} else if ("登録".equals(action)) {
			try {
				// 登録が成功したらtrue
				if (mr.editUser(user)) {
					mr.getUsers();
					nextPage = "/jsp/editted.jsp";
				} else {
					nextPage = "/jsp/edittedError.jsp";
				}
			} catch (Exception e) {
				e.printStackTrace();
				nextPage = "/jsp/edittedError.jsp";
			}
			
		} else if ("戻る".equals(action)) {
			nextPage = "/jsp/editInput.jsp";
		} else if ("一覧へ".equals(action)) {
			nextPage = "AdminUserServlet";
		} else if ("メニューへ".equals(action)) {
			nextPage = "/jsp/menu.jsp";
		}

		request.setAttribute("user", user);
		request.setAttribute("checked", checked);
		request.setAttribute("message", message);
		request.setAttribute("adminConfigClass", adminConfigClass);
		request.setAttribute("unsubscribeClass", unsubscribeClass);
		request.setAttribute("adminFlag", adminFlag);
		request.setAttribute("transition", transition);

		request.getRequestDispatcher(nextPage).forward(request, response);
	}
}
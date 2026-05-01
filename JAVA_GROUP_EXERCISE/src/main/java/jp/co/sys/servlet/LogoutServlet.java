package jp.co.sys.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ログアウト処理を管理するサーブレットです。
 * @author 加藤博文
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("UTF-8");
		  PrintWriter out = response.getWriter();

		  HttpSession session = request.getSession(true);
		  //session破棄
		  session.invalidate();
//		  リダイレクト先
		  response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
	}

}

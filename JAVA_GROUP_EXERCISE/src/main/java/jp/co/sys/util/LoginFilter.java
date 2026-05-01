package jp.co.sys.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ログイン状態管理や、ブラウザバック対策を行うクラスです。
 * 	@author 長谷部広樹
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String uri = req.getRequestURI();
        
        boolean isLogin = uri.endsWith("login.jsp") || uri.endsWith("LoginServlet");
        boolean isCss = uri.contains("/css/");
        boolean isImages = uri.contains("/images/");

        boolean loggedIn = (session != null && session.getAttribute("meetingRoom") != null);

        if (loggedIn || isLogin || isCss || isImages) {
        	
//  	ブラウザバック対策
  			res.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
  			res.setHeader("Pragma", "no-cache");
  			res.setDateHeader("Expires", 0);

        	
            chain.doFilter(request, response);

//       未ログインならログイン画面へ飛ばす
        } else {
        	session = req.getSession(true);
        	session.setAttribute("message", "一定時間が経過したため再ログインしてください");
        	res.sendRedirect(req.getContextPath() + "/jsp/login.jsp");
        }
    }
}


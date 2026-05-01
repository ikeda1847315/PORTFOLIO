package jp.co.sys.stub.hasebe;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/clearSession")
public class clearSession extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public clearSession() {
    	super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // セッションを取得して破棄する
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            System.out.println("LOG: セッションを破棄しました。");
        }
        
        response.sendRedirect(request.getContextPath() + "/jsp/menu.jsp");
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
    }
}

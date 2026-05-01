package jp.co.sys.stub.hasebe;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.sys.bean.MeetingRoom;

/**
 * Servlet implementation class setSession
 */
@WebServlet("/setSession")
public class setSession extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public setSession() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    HttpSession session = request.getSession();
	    MeetingRoom meetingRoom = null;

	    try {
	        // ここで DAO (findAll) が走るため、失敗すると catch へ
	        meetingRoom = new MeetingRoom(); 
	    } catch (Exception e) {
	        // エラーが出ても止まらないようにログだけ出す
	        e.printStackTrace();
	        System.err.println("DEBUG: DAOの呼び出しに失敗しましたが、処理を続行します。");
	    }

	    if (meetingRoom != null) {
	        session.setAttribute("meetingRoom", meetingRoom);
	    }
	    
		request.getRequestDispatcher("/jsp/menu.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

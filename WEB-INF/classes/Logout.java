import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class Logout extends HttpServlet{

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		processRequest(req, res);	
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		processRequest(req, res);
	}

    public void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException{
        PrintWriter out = res.getWriter();

        HttpSession sess = req.getSession(false);
        //sess.getAttribute("name");
        if(sess!=null)
        {
            sess.invalidate();
        }

        RequestDispatcher rd = req.getRequestDispatcher("/Login.html");
        rd.include(req,res);

        out.println("<h2>You've logged out succussfully!</h2>");
        out.close();
    }
}
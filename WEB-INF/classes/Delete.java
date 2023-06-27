import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class Delete extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		processRequest(req, res);	
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		processRequest(req, res);
	}

    public void processRequest(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        HttpSession sess = req.getSession(false);
        if(sess==null)
        {
            res.sendRedirect("Login.html");
            return;
        }
        sess.getAttribute("name");
        if(sess.getAttribute("name") == null){
            res.sendRedirect("Login.html");
            return;
        }
    
        String name = req.getParameter("name");

        out.println("<html>");
	    out.println("<head><title>Classroom App</title></head>");
	    out.println("<body>");

        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1/db";
            Connection c = DriverManager.getConnection(url,"root","root");
            Statement st = c.createStatement();
            String query = "DELETE FROM signup WHERE name = '"+ name +"'";
            int rs = st.executeUpdate(query);
            if(rs==0){
                out.println("<h2>Deletion failed! Name or Email is already taken/empty, please try again.</h2>");
                RequestDispatcher rd = req.getRequestDispatcher("/Delete.html");
                rd.include(req,res);
            }
            else{
                out.println("<h2>Student record deleted successfully!</h2>");
                RequestDispatcher rd = req.getRequestDispatcher("/AdminPortal.html");
                rd.include(req,res);
            }
            /*if(rs==1){
                out.println("<h2>Student removed successfully!</h2>");
                RequestDispatcher rd = req.getRequestDispatcher("/AdminPortal.html");
				rd.include(req,res);
            }
            else{
                out.println("<h2>Student could not removed!</h2>");
            }*/
            out.println("</body></html>");
	        st.close();
	        c.close();
        }
        catch(Exception e){
            out.println(e);
        }
    }
}
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class ShowAll extends HttpServlet {

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
        
        out.println("<html>");
	    out.println("<head><title>Classroom App</title></head>");
	    out.println("<body>");

        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1/db";
            Connection c = DriverManager.getConnection(url,"root","root");
            Statement st = c.createStatement();
            String query = "SELECT * FROM signup";
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
                while(rs.next()){
                    out.println(rs.getString("name") + "," + "\n");
                    out.println(rs.getString("email") + "," + "\n");
                    out.println(rs.getString("password") + ",");
                }
            }
            else{
                out.println("<h2>No Student found!</h2>");
            }
            out.println("</body></html>");
	        st.close();
	        c.close();
        }
        catch(Exception e){
            out.println(e);
        }
    }
}
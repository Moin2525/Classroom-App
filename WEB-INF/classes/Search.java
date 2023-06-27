import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class Search extends HttpServlet {

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
            String query = "SELECT * FROM signup WHERE name = '" + name + "'";
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
                String names = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                if(names.equals("Admin")){
                    out.println("<h3>Sorry! You can't see the data of Admin.</h3>");
                    RequestDispatcher rd = req.getRequestDispatcher("/Search.html");
				    rd.include(req,res);
                }
                else{
                    out.println("<h2>Student Found!</h2>");
                    out.println("<h4>Email and Password of student is: </h4>" + email + " and " + password);
                }
            }
            else{
                out.println("<h2>Student could not found!</h2>");
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
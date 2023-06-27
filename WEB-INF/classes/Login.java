import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class Login extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		processRequest(req, res);	
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		processRequest(req, res);
	}

	public void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
				
	    res.setContentType("text/html");
	    PrintWriter out = res.getWriter();

	    String name = req.getParameter("name");
	    String password = req.getParameter("password");

	    out.println("<html>");
	    out.println("<head><title>Classroom App</title></head>");
	    out.println("<body>");

	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	        String url = "jdbc:mysql://127.0.0.1/db";
	        Connection c = DriverManager.getConnection(url, "root", "root");
	        Statement st = c.createStatement();

	        String query = "SELECT * FROM signup WHERE name = '" + name + "' AND password = '" + password + "'";
	        ResultSet rs = st.executeQuery(query);

	        if (rs.next()) {
				HttpSession sess = req.getSession(true);
				sess.setAttribute("name","name");

				String names = rs.getString("name");
				String passwords = rs.getString("password");
				if(names.equals("Admin") && passwords.equals("123")){
					RequestDispatcher rd = req.getRequestDispatcher("/AdminPortal.html");
					rd.forward(req,res);
				}
				else if(name=="" || password==""){
					out.println("<h2>Please enter username or password!</h2>");
					RequestDispatcher rd = req.getRequestDispatcher("/Login.html");
					rd.include(req,res);
				}
				else{
					RequestDispatcher rd = req.getRequestDispatcher("/UserPortal.html");
					rd.forward(req,res);
				}
	        } else {
	            out.println("<h2>Please enter valid username or password!</h2>");
				RequestDispatcher rd = req.getRequestDispatcher("/Login.html");
				rd.include(req,res);
	        }

	        out.println("</body></html>");
	        st.close();
	        c.close();

	    } catch(Exception e) {
	        out.println(e);
	    }
		
	}
}
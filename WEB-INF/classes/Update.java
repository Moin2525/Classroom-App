import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class Update extends HttpServlet {

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
        sess.getAttribute("name");
        if(sess==null)
        {
            res.sendRedirect("Login.html");
            return;
        }
        if(sess.getAttribute("name") == null){
        res.sendRedirect("Login.html");
        return;
        }
        
        String name = req.getParameter("name");
	    String email = req.getParameter("email");
	    String password = req.getParameter("password");
	    String conf_password = req.getParameter("conf_password");

        out.println("<html>");
	    out.println("<head><title>Classroom App</title></head>");
	    out.println("<body>");

        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1/db";
            Connection c = DriverManager.getConnection(url,"root","root");
            Statement st = c.createStatement();
            String query = "UPDATE signup SET email = '"+ email +"', password = '"+ password +"', conf_password = '"+ conf_password +"' WHERE name = '"+ name +"'";
            int rs = st.executeUpdate(query);
            if(rs==0){
                out.println("<h2>Updation failed! Name or Email is already taken/empty, please try again.</h2>");
                RequestDispatcher rd = req.getRequestDispatcher("/Update.html");
                rd.include(req,res);
            }
            else{
                out.println("<h2>Student record updated successfully!</h2>");
                RequestDispatcher rd = req.getRequestDispatcher("/AdminPortal.html");
                rd.include(req,res);
            }
            /*if(rs==1){
                if(name.equals(name) || email.equals(email) || name=="" || email==""){
                    out.println("<h2>Updation failed! Name or Email is already taken/empty, please try again.</h2>");
                    RequestDispatcher rd = req.getRequestDispatcher("/Update.html");
                    rd.include(req,res);
                }
                else{
                    out.println("<h2>Student Updated successfully!</h2>");
                    RequestDispatcher rd = req.getRequestDispatcher("/AdminPortal.html");
                    rd.include(req,res);
                }
            }
            else{
                out.println("<h2>Student record could not Updated! Try again.</h2>");
                RequestDispatcher rd = req.getRequestDispatcher("/Update.html");
                rd.include(req,res);
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
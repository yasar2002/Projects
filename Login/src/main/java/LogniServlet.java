import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LogniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String Amail;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    RequestDispatcher rd = request.getRequestDispatcher("AuthServlet");
 		try {
			if(login(request,response))
			rd.forward(request, response);
		} catch (Exception e) {
			System.out.println("SQl Problem in login page");
			System.out.println(e);
		}
    	  
	}
	public boolean login(HttpServletRequest request,HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException{
		String mail = request.getParameter("Mail");
		String pass = request.getParameter("Pass");
		Amail = mail.replace((" "),("")); 
		 Class.forName("com.mysql.cj.jdbc.Driver");
		 Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDetails", "root", "");			
	     PreparedStatement st = conn.prepareStatement("select * from CustomerDetails  where Email_id = ? and Password=?");
         st.setString(1,Amail);
         st.setString(2,pass);
         
         ResultSet rs = st.executeQuery();
              
         if (!rs.next()) {
             System.out.println("Your email or password is incorrect");
             
         } else {
             System.out.println("Hi " + rs.getString(2) + " You are LoggedIn successfully");
     		System.out.println("Entered");
     		Mail ob = new Mail();
             String to = LogniServlet.Amail;
             String sub = "Authentication";
             int min = 1000;
     		int max = 10000;
     		int token = (int)(Math.random()*(max-min+1)+min);  
     		request.setAttribute("token",token);
             String msg = "your auth token is : "+Integer.toString(token);
             System.out.println("state :"+ob.SendMail(to,sub,msg));
             return true;
         }
         return false;
	}
	
}

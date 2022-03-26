import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Filterz")
public class SignInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
		PrintWriter pw = response.getWriter();
		pw.print("Vaid Input");
		
		try {
			if(connect(request) == 1)
			response.sendRedirect("Open.html");
		} catch (ClassNotFoundException | SQLException | IOException e) {
			System.out.println("SQl Problem in Sign up page");
		}
	}
	public int connect(HttpServletRequest request) throws SQLException, ClassNotFoundException {
		 String mail=request.getParameter("Mail");
        String name=request.getParameter("Uname");
        String number=request.getParameter("Mbnum");
        String password = request.getParameter("Pass");
         
		Class.forName("com.mysql.cj.jdbc.Driver");
		 Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDetails", "root", "");			
	     PreparedStatement st = conn.prepareStatement("insert into CustomerDetails(Name,Email_id,Mobile_Number,Password) values(?,?,?,?)");
        st.setString(1,name);
        
        st.setString(2,mail);
        
        st.setString(3, number);
        
        st.setString(4, password);
        System.out.println("Updated Successfully in database");
        return st.executeUpdate();
        
		
	}

}

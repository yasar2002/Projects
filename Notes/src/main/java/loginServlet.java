import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	    String pass,encpass;
	    public static String uidInput;
	   
	    
	    Logger logger = Logger.getLogger(loginServlet.class);
	    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String filePath = "/home/yasar-zstk264/eclipse-workspace/Notes/src/main/java/log4j.properties";
        PropertyConfigurator.configure(filePath);
        
		try {
			if(aluser(request, response)) {
			    logger.info("The given data is updated successfully");
			}
			else {
				logger.info("The given data is not updated successfully");
			}
		} catch (Exception e) {
			logger.info("There was a problem found in "+e);
		}  
	}
	public boolean aluser(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException, ServletException, NoSuchAlgorithmException {
		uidInput = request.getParameter("uid");
		pass = request.getParameter("pass");
//		encpass = Util.hashPassword(pass);
		
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Keep", "root", "");
        PreparedStatement st = conn.prepareStatement("select * from UserDetails where UniqId = ? and Password = ?");
        st.setString(1,uidInput);
        st.setString(2,pass);
        ResultSet rs = st.executeQuery();
        
        if(rs.next()) {
        	setCookies("uid",uidInput, response);
        	response.sendRedirect("Note.html");
        	conn.close();        
       }
        else {
        conn.close();
		return false;
        }
return false;

	}
	public void setCookies(String name,String value,HttpServletResponse response) {
		Cookie cookie=new Cookie(name, value);
		response.addCookie(cookie);
	}
}

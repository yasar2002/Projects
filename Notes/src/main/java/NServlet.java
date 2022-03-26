import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONObject;
 

@WebServlet("/Filter")
public class NServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

     String mail,pass,encpass;

	    
	    Logger logger = Logger.getLogger(NServlet.class);
	    
	    

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filePath = "/home/yasar-zstk264/eclipse-workspace/Notes/src/main/java/log4j.properties";
        PropertyConfigurator.configure(filePath);
        PrintWriter pw = response.getWriter();
		System.out.println("Entering to NServlet");
		
		try {
			if(newuser(request,response)){
				logger.info("Your new id has been created successfully");
				pw.print("Vaid Input");
				response.sendRedirect("Note.html");
			}
			else {
				response.sendRedirect("index.html");
				logger.info("There was a problem in creating your new Id");
			}
				
		} catch (SQLException e) {
			logger.info("There was a problm found in sql email " + e);
			response.sendRedirect("Note.html");
		}catch (Exception e) {
			logger.info("There was a problm found in " + e);
		}
}
		
		public boolean newuser(HttpServletRequest request,HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException, NoSuchAlgorithmException {
	    JSONObject obj1 = new JSONObject();
		mail = request.getParameter("mail").replace((" "),(""));
		System.out.println(mail);
		pass = request.getParameter("pass");
//		encpass = Util.hashPassword(pass);
		String uid = getAlphaNumericString(5);
		System.out.println(uid);
		PrintWriter out = response.getWriter();
		obj1.put("status","failed");
	            if(!check(mail)) {
	            System.out.println("Entering to check mail");
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Keep", "root", "");
	            PreparedStatement st = conn.prepareStatement("Insert into UserDetails (Mail,Password,UniqId) values(?,?,?)");
	            st.setString(1,mail);
	            st.setString(2,pass);
	            st.setString(3,uid);
				int rs = st.executeUpdate();
				System.out.println("Your UId is : "+uid);
				Mail ob = new Mail();
		        String to = mail;
		        String sub = "Authenticaion";
		        String msg = "your auth token is : "+uid ;
		        logger.info("state :"+ob.SendMail(to,sub,msg));
		        
				if(rs != 0) {
					return true;
				}
				return false;
	            }
	            else
					return false;
		}
		
		public boolean check(String mail) throws ClassNotFoundException, SQLException {
			System.out.println("Entered to check mail");
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Keep", "root", "");
            PreparedStatement st = conn.prepareStatement("Select * from UserDetails where Mail = ?");
            st.setString(1,mail);
            System.out.println(st);
            ResultSet res = st.executeQuery();
			return res.next();
			}
    
	static String getAlphaNumericString(int n)
    {
 
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz";
  
        StringBuilder sb = new StringBuilder(n);
  
        for (int i = 0; i < n; i++) {
  
       int index = (int)(AlphaNumericString.length() * Math.random());
       
       sb.append(AlphaNumericString.charAt(index));
        }
  
        return sb.toString();
    }
	
}


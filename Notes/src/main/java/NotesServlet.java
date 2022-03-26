import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;


@WebServlet("/NotesServlet")
public class NotesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	 LocalDateTime myDateObj = LocalDateTime.now();
	   
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    String formattedDate = myDateObj.format(myFormatObj);
	    
	    DateTimeFormatter myFormatObj1 = DateTimeFormatter.ofPattern("HH:mm");
	    String formattedTime = myDateObj.format(myFormatObj1);
	    
	    Logger logger = Logger.getLogger(UpdServlet.class);
	    JSONObject obj = new JSONObject();
	    
	
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	Cookie cookie[]=request.getCookies();
	    	String uidInput="";
	    	for(int i=0;i<cookie.length;i++) {
	    		if(cookie[i].getName().equals("uid"))uidInput=cookie[i].getValue();
	    		else uidInput="no";
	    	}
			try {
				if(notes(request, response,uidInput))
				{
					logger.info("Statement executed successfully");
				}
				else {
					logger.info("failure in statement");
				}
			} catch (ClassNotFoundException | IOException | SQLException e) {
				logger.info("The problem was found in "+e);
			}
		}
	    
	
	  public boolean notes(HttpServletRequest request, HttpServletResponse response,String uid) throws IOException, SQLException, ClassNotFoundException {
		String title = request.getParameter("title");
    	String notes = request.getParameter("notes");
    	String nid   = getAlphaNumericString(8);
    	PrintWriter out = response.getWriter();
    	Class.forName("com.mysql.cj.jdbc.Driver");
    	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Keep", "root", "");
    	PreparedStatement st = conn.prepareStatement("Insert into Content (UniqId,Nid,Title,Notes,Date,Time) values (?,?,?,?,?,?)");
    	st.setString(1,uid);
    	st.setString(2,nid);
    	st.setString(3,title);
    	st.setString(4,notes);
    	st.setString(5,formattedDate);
    	st.setString(6,formattedTime);
    	int res = st.executeUpdate();
    	conn.close();
    	if(res!=0) {
    		System.out.println("Statement executed successfully Your Nid is "+nid);
    		obj.put("User Data", Dataget(request, response,uid));
    		obj.put("status","ok");
    		out.write(obj.toJSONString());
			out.flush();  
		    out.close();
    		return true;
    	}
    	else {
    		logger.info("There was a problem in adding Notes");
    		obj.put("status","failed");
    		out.write(obj.toJSONString());
			out.flush();  
		    out.close();
    		return false;
    	}
		
	}
	public String Dataget(HttpServletRequest request, HttpServletResponse response,String uidInput) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Keep", "root", "");
        PreparedStatement st = conn.prepareStatement("select * from Content where UniqId = ?");
        System.out.println("uid "+uidInput);
        System.out.println(st);
        st.setString(1,uidInput);
        
        ResultSet rs = st.executeQuery();
        List<JSONObject>jsonObjects=new ArrayList<JSONObject>();
     
        while(rs.next()) {
           JSONObject record = new JSONObject();
           
           record.put("Nid", rs.getString("Nid"));
           record.put("Title", rs.getString("Title"));
           record.put("Notes", rs.getString("Notes"));
           record.put("Date", rs.getDate("Date"));
           record.put("Time", rs.getTime("Time"));
           
          jsonObjects.add(record);
        }
        conn.close();
        return jsonObjects.toString();
    	
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

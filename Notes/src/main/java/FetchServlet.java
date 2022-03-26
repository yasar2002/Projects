import java.io.IOException;
import java.io.PrintWriter;
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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@WebServlet("/FetchServlet")
public class FetchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
         
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		        try {
					//obj.put("User Data",fetch(request, response));
					JSONArray array = fetch(request, response);
					out.write(array.toJSONString());
				    out.flush();  
			        out.close();
					System.out.println("Data send successfully");

				} catch (ClassNotFoundException | SQLException e) {
					System.out.println("Problem in sending data"+e);
				}
    		    			
	}
    public JSONArray fetch(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
    	String uid = request.getParameter("uid");
    	Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Keep", "root", "");
        PreparedStatement st = conn.prepareStatement("select * from Content where UniqId = ?");
        st.setString(1,uid);
        ResultSet rs = st.executeQuery();
        JSONArray array = new JSONArray();
     
        while(rs.next()) {
           JSONObject record = new JSONObject();
          
           record.put("Title", rs.getString("Title"));
           record.put("Note", rs.getString("Notes"));
           array.add(record);
        }
		return array;
    	
    }
	

}

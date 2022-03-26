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
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONObject;


@WebServlet("/DServlet")
public class DServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    JSONObject obj = new JSONObject();

	Logger logger = Logger.getLogger(UpdServlet.class);
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filePath = "/home/yasar-zstk264/eclipse-workspace/Notes/src/main/java/log4j.properties";
        PropertyConfigurator.configure(filePath);
		 try {
			if(delete(request, response))
			        logger.info("Your Note is Deleted successfully");
			        else
			        logger.info("There is a something problem in Deleting Data");
		} catch (ClassNotFoundException | SQLException e) {
			logger.info("There was a problem which was found in "+e);
		}
	}
    
	public boolean delete(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
		String title = request.getParameter("title");
		String uid = request.getParameter("uid");
		PrintWriter out = response.getWriter();
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Keep", "root", "");
        PreparedStatement st = conn.prepareStatement("delete from Content where UniqId = ? and Title = ?");
        st.setString(1,uid);
        st.setString(2,title);
        System.out.println(st);	
        int rs = st.executeUpdate();
        conn.close();
        if(rs != 0) {
        	obj.put("Status","Success");
        	out.write(obj.toJSONString());
			out.flush();  
		    out.close();
		    return true;
        }
        else {
        	obj.put("Status","Failure");
        	out.write(obj.toJSONString());
			out.flush();  
		    out.close();
        	return false;
        }
        	

	}

}

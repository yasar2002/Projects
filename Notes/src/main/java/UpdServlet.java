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


@WebServlet("/UpdServlet")
public class UpdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    JSONObject obj = new JSONObject();
    
	Logger logger = Logger.getLogger(UpdServlet.class);
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filePath = "/home/yasar-zstk264/eclipse-workspace/Notes/src/main/java/log4j.properties";
        PropertyConfigurator.configure(filePath);
	    try {
			if(update(request, response)) {
				obj.put("status","success");
				
				logger.info("Notes updated successfully");
			}
			else
				logger.info("There is a something problem in updation");
		} catch (Exception e) {
			logger.info("The problem was found in "+e);
		}
	    
	}
	
	public boolean update(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException{
		String uidInput = request.getParameter("uid");
		String nid = request.getParameter("nid");
		String notes = request.getParameter("notes");
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Keep", "root", "");
        PreparedStatement st = conn.prepareStatement("update Content set Notes = ? where Nid = ? and UniqID = ?");
        st.setString(1,notes);
        st.setString(2,nid);
        st.setString(3,uidInput);
        int rs = st.executeUpdate();
        System.out.println(rs);
        conn.close();
        if(rs != 0)
		    return true;
        else
        	return false;
	}

}

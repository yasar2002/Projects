import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;


@WebServlet("/AuthServlet")
public class AuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Entered to Authentication");
		JSONObject object = new JSONObject();
		PrintWriter pw = response.getWriter();
		String authtoken = request.getParameter("Otp");
		String token = (String)request.getAttribute("token");
		int Otp = Integer.parseInt(token);
		int Atoken = Integer.parseInt(authtoken);
		if(Otp == Atoken) {
			object.put("Status","Success");
			pw.write(object.toJSONString());
			pw.flush();
			pw.close();
			response.sendRedirect("Open.html");
		}
		
		else {
			object.put("Status","Failure");
			pw.write(object.toJSONString());
			pw.flush();
			pw.close();
			response.sendRedirect("Auth.html");
		}
	}

}

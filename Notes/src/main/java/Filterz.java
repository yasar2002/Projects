import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;

@WebFilter("/Filter")
public class Filterz extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;
	   String mail,pass;
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	    mail =request.getParameter("mail");
        pass = request.getParameter("pass");
        String cpassword = request.getParameter("CnfPass");
        boolean c1 = Pattern.matches("[a-zA-Z0-9]+[@][a-z]+[.][a-z]{2,3}", mail);
        boolean c2 = Pattern.matches("[a-zA-z0-9!@#$%_*]{8,}", pass);
        boolean c3 = (pass.equals(cpassword));
        System.out.println(mail+" "+pass+" "+cpassword);
        System.out.println(c1+" "+c2+" "+c3);
        
        PrintWriter pw = response.getWriter();
        if(c1  && c2 && c3)
		    chain.doFilter(request, response);
        else  
     	   pw.print("Invalid input");
     	   
	}

}

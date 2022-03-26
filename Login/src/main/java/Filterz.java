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

@WebFilter("/Filterz")
public class Filterz extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	    String mail =request.getParameter("Mail");
        String name=request.getParameter("Uname");
        String number=request.getParameter("Mbnum");
        String password = request.getParameter("Pass");
        String cpassword = request.getParameter("CnfPass");
        boolean c1 = Pattern.matches("[a-zA-Z0-9]+[@][a-z]+[.][a-z]{2,3}", mail);
        boolean c2 = Pattern.matches("[a-zA-Z_ ]*", name);
        boolean c3 = Pattern.matches("[0-9]{10}", number);
        boolean c4 = Pattern.matches("[a-zA-z0-9!@#$%_*]{8,}", password);
        boolean c5 = (password.equals(cpassword));
        System.out.println(mail+" "+name+" "+number+" "+password+" "+cpassword);
        System.out.println(c1+" "+c2+" "+c3+" "+c4+" "+c5);
        
        PrintWriter pw = response.getWriter();
        if(c1 && c2 && c3 && c4 && c5)
		    chain.doFilter(request, response);
        else  
     	   pw.print("Invalid input");
     	   
	}

}

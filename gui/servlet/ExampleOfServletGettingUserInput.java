import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/FormServlet")
public class FormServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		System.out.println("fname = " + fname);
		System.out.println("lname = " + lname);
		
		response.setContentType("text/html");
		out.println("<html>");
		out.println("<head><title>Form Submission</title></head>");
		out.println("<body>");
		out.println("<h1>Submitted Data</h1>");
		out.println("<First Name:<strong> " + fname + "</strong><br />");
		out.println("<Last Name:<strong> " + lname + "</strong>");
		out.println("</body>");
		out.println("</html>");
	}
}

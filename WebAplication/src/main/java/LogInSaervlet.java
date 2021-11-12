import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class LogInSaervlet extends HttpServlet {


    public static boolean LoggingCheck(String name,String pass){
        boolean status=false;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/webshop","root","magina123");

            PreparedStatement ps=con.prepareStatement(
                    "select * from users where user_email=? and user_password=?");
            ps.setString(1,name);
            ps.setString(2,pass);

            ResultSet rs=ps.executeQuery();
            status=rs.next();

        }catch(Exception e){
            System.out.println(e);
        }
        return status;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String email=request.getParameter("email");
        String pass=request.getParameter("pass");

        if(LogInSaervlet.LoggingCheck(email,pass)){
            RequestDispatcher rd=request.getRequestDispatcher("meni.jsp");
            rd.forward(request,response);
        }
        else {
            out.print("Sorry username or password error");
            RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
            rd.include(request,response);
        }

        out.close();
    }
}

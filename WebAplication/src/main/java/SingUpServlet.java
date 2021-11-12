import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SingUpServlet extends HttpServlet {




    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");

        try {

            // loading drivers for mysql
            Class.forName("com.mysql.cj.jdbc.Driver");

            //creating connection with the database
            Connection con = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/webshop","root","magina123");

            PreparedStatement ps = con.prepareStatement
                    ("insert into users(user_name, user_password, user_email) values(?,?,?)");

            ps.setString(1, name);
            ps.setString(2, pass);
            ps.setString(3, email);

            int i = ps.executeUpdate();

            if(i > 0) {
                RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
                rd.forward(request,response);
              //  out.println("You are sucessfully registered");

            }

        }
        catch(Exception se) {
            se.printStackTrace();
        }

    }
}



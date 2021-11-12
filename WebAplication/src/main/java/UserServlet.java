import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class UserServlet extends HttpServlet {
    private UserMethods userMethods;

    public UserServlet(){
        this.userMethods = new UserMethods();

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getServletPath();
        switch (action){
            case "/new":
                showNewForm(request,response);
                break;
            case "/insert":
                    insertUser(request,response);
              break;
            case "/delete":
                try {
                    deleteUser(request, response);
                }catch (SQLException e ){
                    e.printStackTrace();
                }
                break;
            case "/edit":
                showUser(request,response);
                break;
            case "/update":
                try {
                    updateUser(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                try {
                    listUser(request,response);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
        }
    }
          private void showNewForm(HttpServletRequest request, HttpServletResponse response)
                  throws ServletException, IOException{

              RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
              dispatcher.forward(request,response);
         }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        User newUser = new User(name, password, email);
        userMethods.insertUser(newUser);
        response.sendRedirect("list");

    }
    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        userMethods.deleteUser(id);
        response.sendRedirect("list");


    }
    private void showUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = userMethods.selectUserbyID(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
        request.setAttribute("user", existingUser);
        dispatcher.forward(request,response);

    }
    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        User upUser = new User(name, password, email);

        userMethods.updateUser(upUser);

        response.sendRedirect("list");


    }
    private void listUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException, ServletException {
        List<User> listUser = userMethods.selectAllUsers();
        request.setAttribute("listUser" , listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
        dispatcher.forward(request, response);

    }


}

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserMethods {

    private String url = "jdbc:mysql://localhost:3306/webshop";
    private String username = "root";
    private String password = "magina123";

    private static final String addUser = "insert into users" + "(user_name, user_password, user_email) values" +
             "(?,?,?);";

    private static final String selectById = "select user_id, user_name, user_password, user_email from users where id=?";
    private static final String allUsers = "select * from users";
    private static final String deleteUser = "delete from users where id = ?;";
    private static final String updateUser = "update users set user_name = ?, user_password = ?, user_email = ? where id=?;";

    protected Connection getConnection(){
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url,username,password);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public void insertUser(User user){
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(addUser)) {
            preparedStatement.setString(1, user.getUser_name());
            preparedStatement.setString(2, user.getUser_password());
            preparedStatement.setString(3, user.getUser_email());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        try(Connection connection = getConnection();
          PreparedStatement preparedStatement = connection.prepareStatement(updateUser)) {
            preparedStatement.setString(1,user.getUser_name());
            preparedStatement.setString(2, user.getUser_password());
            preparedStatement.setString(3, user.getUser_email());
            preparedStatement.setString(4, String.valueOf(user.getId()));

            rowUpdated = preparedStatement.executeUpdate() >0;

        }
        return rowUpdated;

    }
    public User selectUserbyID(int id){
        User user = null;
        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(selectById) ) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                String name = rs.getString("user_name");
                String password = rs.getString("user_password");
                String email = rs.getString("user_email");
                user = new User(id,name,password,email);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }return user;
    } public List<User> selectAllUsers(){
        List<User> users = new ArrayList<>();
        try(Connection connection = getConnection();
          PreparedStatement preparedStatement = connection.prepareStatement(allUsers)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("user_name");
                String password = rs.getString("user_password");
                String email = rs.getString("user_email");
                users.add(new User(id,name,password,email));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }
    public boolean deleteUser(int id) throws SQLException{
        boolean rowDeleted;

        try(Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteUser);
            preparedStatement.setInt(1,id);
            rowDeleted = preparedStatement.executeUpdate() > 0;

        }
        return rowDeleted;

    }




}

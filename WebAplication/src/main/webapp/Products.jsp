<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<html>
<head>
    <title>Product page</title>
</head>
<body>

<h1>All products</h1>
<hr>
<table>
    <tr id="header">
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
    </tr>
    <tr>
        <%
            try {

                // loading drivers for mysql
                Class.forName("com.mysql.cj.jdbc.Driver");

                //creating connection with the database
                Connection con = DriverManager.getConnection
                        ("jdbc:mysql://localhost:3306/webshop","root","magina123");

                PreparedStatement ps = con.prepareStatement
                        ("SELECT user_name from users");



                int i = ps.executeUpdate();



            }
            catch(Exception se) {
                se.printStackTrace();
            }
        %>
        <td>

        </td>
    </tr>
</table>


</body>

</html>
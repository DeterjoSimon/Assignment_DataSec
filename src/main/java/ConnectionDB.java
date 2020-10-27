import java.sql.*;

public class ConnectionDB {

    //Credentials to establish connection to DB
    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static final String DB_URL = "jdbc:mariadb://127.0.0.1/printers";
    static final String USER = "root";
    static final String PASS = "temisan";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stm = null;
        try {

            //Open a connection
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connection to database established!");


            //Execute query
            Statement stmt = conn.createStatement();

            String sql = "SELECT * FROM employees";

            System.out.println("Employee database: \n");

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){

                //Explicitly state keys
                String Name = rs.getString("Name");
                String ID = rs.getString("ID");
                int pass = rs.getInt("PASS");

                //Print results
                System.out.format("%s, %s, %s\n", Name, ID, PASS);
            }
        } catch (ClassNotFoundException | SQLException e) {
            // Handle errors
            e.printStackTrace();
        } finally {
            // Finally block to close resources
            try {
                if (stm != null){
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
                if (conn != null){
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            System.out.print("\n Bye bye!");
        }
    }
}

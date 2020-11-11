import java.sql.*;

public class ConnectionDB {

    /*
    //Credentials to establish connection to DB
    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static final String DB_URL = "jdbc:mariadb://172.27.156.17:3306/printers";
    static final String USER = "user";
    static final String PASS = "security";


    public Connection connect(){
        Connection conn = null;
        try {
            //Open a connection
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connection to database established!");
            return conn;
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }
    */

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/";
    //  Database credentials
    static final String USER = "username";
    static final String PASS = "password";

    public Connection connect(){
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            //Class.forName("com.mysql.cj.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection("jdbc:raima:rdm://local");

            //STEP 4: Execute a query
            System.out.println("Creating database...");
            stmt = conn.createStatement();

            String sql = "CREATE DATABASE STUDENTS";
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully...");
            return conn;
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
            return null;
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
            return null;
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
                    return null;
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
                    return null;
            }catch(SQLException se){
                se.printStackTrace();
                return null;
            }//end finally try
        }//end try
    }
}


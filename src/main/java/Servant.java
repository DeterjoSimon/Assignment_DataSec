import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.*;

public class Servant extends UnicastRemoteObject implements PrinterService {

    //Parameters for MySQL Database
    /*
    private final ConnectionDB db = new ConnectionDB();
    private final Connection conn = db.connect();
    */
    //Parameter for Excel Database
    private final Book DBBook = new Book("C:\\Users\\Simon\\Desktop\\DataSec\\Assignments\\Assignment2\\src\\main\\java\\DB.xlsx");

    //Parameters for user log-in
    private String userID;
    public HashMap<String, PrinterObject> printers;
    private boolean on;
    private final HashMap<String, String> params;
    private final String OffMsg;

    public Servant() throws RemoteException{
        super();
        on = true;
        printers = new HashMap<String, PrinterObject>();
        params = new HashMap<String, String>();
        OffMsg = "Printer SERVER is turned off... cannot execute command";
    }

    public void newUser(String name, String ID, String password) throws IOException {
        //Write in log
        log("New User");
        //Get hashed password
        String passwordDB = Hasher.hashPassword(name, password);
        //Create new user
        DBBook.newUser(name, ID, passwordDB);
    }

    private void log(String operation){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("Time: " + timestamp + "\n" + operation + " has been called by: " + this.userID);
    }

    private void log(String operation, ArrayList<String> params){
        log(operation);
        for (String i: params) {
            System.out.println("With parameters: " + i);
        }
    }

    private PrinterObject FindSelectedPrinter(String printer){
        PrinterObject selectedPrinter;
        if (!printers.containsKey(printer)){
            selectedPrinter = new PrinterObject();
            printers.put(printer, selectedPrinter);
        }
        else{
            selectedPrinter = printers.get(printer);
        }
        return selectedPrinter;
    }

    private String FindParam(String parameter){
        String selectedValue;
        if (!params.containsKey(parameter)){
            selectedValue = "Value has not been set yet.";
            params.put(parameter, selectedValue);
        }
        else{
            selectedValue = params.get(parameter);
        }
        return selectedValue;
    }

    public String start(){
        on = true;
        log("start");
        return "start";
    }

    public String stop(){
        on = false;
        log("stop");
        return "stop";
    }
    
    public boolean login(String username, String password){
        //Check password queried from DB
        if (Hasher.checkPassword(username, password, DBBook.getPassword(username))){
            log("User connected");
            this.userID = username;
            return true;
        }
        else {
            return false;
        }
    }

    /*
    @Override
    public boolean login(String username, String password) throws RemoteException, SQLException {
        Statement stmt = conn.createStatement();
        String sql = "SELECT Name,PASS FROM employees WHERE ID='" + username + "'";
        ResultSet rs = stmt.executeQuery(sql);

        // Initialising unique ID parameter for log writting
        this.userID = username;

        while (rs.next()){
            String name = rs.getString("Name");
            String passwordDB = rs.getString("PASS");
            if (Hasher.checkPassword(username, password, passwordDB)){
                log("User connected");
                rs.close();
                return true;
            }
        }
        rs.close();
        log("A User tried to connect");
        return false;
    }
    */
    
    public String restart(){
        log("restart");
        if (on){
            for (String key: printers.keySet()){
                printers.get(key).ResetJobs();
            }
            return "Print server has been restarted";
        }
        return OffMsg;
    }

    @Override
    public String status(String printer){
        log("status", new ArrayList<String>(Collections.singletonList(printer)));
        if (on) {
            PrinterObject selectedPrinter = FindSelectedPrinter(printer);
            int elements = selectedPrinter.jobs.size();
            if (elements == 0){
                return "Specified printer has no jobs, thus is idle";
            }
            else{
                return  "Specified printer is working on: " + elements + " number of jobs";
            }
        }
        return OffMsg;
    }

    @Override
    public String echo(String input) throws RemoteException{
        return "From server: " + input;
    }

    public String queue(String printer) throws RemoteException {
        log("queue", new ArrayList<String>(Collections.singletonList(printer)));
        if (on) {
            PrinterObject selectedPrinter = FindSelectedPrinter(printer);
            return selectedPrinter.PrintJobs();
        }
        return OffMsg;
    }

    public String topQueue(String printer, int job) throws RemoteException{
        log("TopQueue", new ArrayList<String>(Arrays.asList(printer, String.valueOf(job))));
        if (on){
            PrinterObject selectedPrinter = FindSelectedPrinter(printer);
            if (selectedPrinter.jobs.size() < job + 1){
                return "Job does not exist";
            }
            //Add job to start
            selectedPrinter.jobs.add(0, selectedPrinter.jobs.get(job));
            System.out.println(selectedPrinter.jobs.toString());
            //Remove job
            selectedPrinter.jobs.remove(job+1);
            return "Job has been moved to top";
        }
        return OffMsg;
    }

    public String print(String filename, String printer) throws RemoteException{
        log("print", new ArrayList<String>(Arrays.asList(filename, printer)));
        if (on) {
            PrinterObject selectedPrinter = FindSelectedPrinter(printer);
            selectedPrinter.jobs.add(filename);
            return "Added filename " + filename + " to printer: " + printer;
        }
        return OffMsg;
    }

    public String readConfig(String parameter) throws RemoteException{
        log("readConfig", new ArrayList<String>(Collections.singletonList(parameter)));
        if (on) {
            return "The selected value of parameter is: " + FindParam(parameter);
        }
        return OffMsg;
    }

    public String setConfig(String parameter, String value) throws RemoteException{
        log("setConfig", new ArrayList<String>(Arrays.asList(parameter, value)));
        if (on){
            params.put(parameter, value);
            return "Parameter: " + parameter + " has been changed to: " + value;
        }
        return OffMsg;
    }

    /*
    public void newUser(String name, String ID, String password) throws RemoteException, SQLException {
        log("newUser");
        Statement stmt = conn.createStatement();
        String passwordDB = Hasher.hashPassword(name, password);
        String sql = "INSERT employees VALUES('" + name + "','" + ID + "','" + passwordDB + "')";
        stmt.executeQuery(sql);
        System.out.println("New user created!");
    }

     */
}

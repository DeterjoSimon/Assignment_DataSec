import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

public interface PrinterService extends Remote{

    String start() throws RemoteException; // starts the print sever

    String stop() throws RemoteException; // stops the print server

    boolean login(String username, String password) throws RemoteException, SQLException;

    String restart() throws RemoteException; // stops the print server, clears the print queue and starts the print server again

    String status(String printer) throws RemoteException; // prints status of printer on the user's display

    String echo(String input) throws RemoteException; // test

    String topQueue(String printer, int job) throws RemoteException; // lists the print queue for a given printer on the user's display in lines of the form <job number> <file name>

    String queue(String printer) throws RemoteException; // lists the print queue for a given printer on the user's display in lines of the form <job number> <file name>

    String print(String filename, String printer) throws RemoteException; // prints file filename on the specified printer

    String readConfig(String parameter) throws RemoteException; // prints the value of the parameter on the user's display

    String setConfig(String parameter, String value) throws RemoteException; // sets the parameter value

    void newUser(String name, String ID, String password) throws IOException, SQLException;
}
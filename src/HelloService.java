import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloService extends Remote{

    public String start() throws RemoteException; // starts the print sever

    public String stop() throws RemoteException; // stops the print server

    public String restart() throws RemoteException; // stops the print server, clears the print queue and starts the print server again

    public String status(String printer) throws RemoteException; // prints status ofprinter on the user's display

    public String echo(String input) throws RemoteException; // test

    public String queue(String printer) throws RemoteException; // lists the print queue for a given printer on the user's display in lines of the form <job number> <file name>

    public String print(String filename, String printer); // prints file filename on the specified printer

    public String readConfig(String parameter); // prints the value of the parameter on the user's display

    public String setConfig(String parameter, String value); // sets the parameter value

}
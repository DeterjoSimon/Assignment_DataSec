import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloServant extends UnicastRemoteObject implements HelloService{

    public HelloServant() throws RemoteException{
        super();
    }

    @Override
    public String start() throws RemoteException {

        return "start";
    }

    @Override
    public String stop() throws RemoteException {

        return "stop";
    }

    @Override
    public String restart() throws RemoteException {

        return "restart";
    }

    @Override
    public String status(String printer) throws RemoteException {

        return "Status of " + printer + ": ";
    }

    public String echo(String input) throws RemoteException{

        return "From server: " + input;
    }

    @Override
    public String queue(String printer) throws RemoteException {

        return "queue";
    }

    @Override
    public String print(String filename, String printer) {

        return "Printing " + filename + " on printer " + printer;
    }

    @Override
    public String readConfig(String parameter) {

        return "Reading configuration for parameter " + parameter;
    }

    @Override
    public String setConfig(String parameter, String value) {

        return "Setting " + parameter + " to " + value;
    }
}

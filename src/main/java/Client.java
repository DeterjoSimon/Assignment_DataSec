import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.sql.SQLException;
import java.util.Scanner;

public class Client {

    private String username;
    private String password;
    private String name;
    private boolean loggedin = false;

    public Client() throws IOException {
        setUsername();
        setPassword();
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void setUsername() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter your username: ");
        this.username = reader.readLine();
    }

    public void setPassword() throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter your password: ");
        this.password = reader.readLine();
    }

    public static void main(String[] args) throws NotBoundException, IOException, InterruptedException, SQLException {
        PrinterService service = (PrinterService) Naming.lookup("rmi://localhost:5099/server");

        //Check if connected to server
        System.out.println("---" + service.echo("hey server"));

        System.out.println("You need to log in:\n");
        Client c = new Client();

        //Logging in:
        try {
            if (service.login(c.getUsername(), c.getPassword())){
                System.out.println("You are now logged in");
                c.loggedin = true;
            }
            else {
                System.out.println("Login failed");
            }
        } catch (SQLException se) {
            System.err.println(se.toString());
            se.printStackTrace();
        }
        if (c.loggedin) {
            //Menu display:
            Scanner input = new Scanner(System.in);
            int choice;
            while (true) {
                System.out.println("================================");
                System.out.println("Printer Server Menu\n");
                System.out.println("0: Exit");
                System.out.println("1. Start the printer server\n");
                System.out.println("2. Print file\n");
                System.out.println("3. Print queue handling\n");
                System.out.println("4. Restart print server\n");
                System.out.println("5. Printer status\n");
                System.out.println("6. Configuration\n");
                System.out.println("7. Stop the printer server\n");
                System.out.println("8. Create new user");
                System.out.println("================================");
                choice = input.nextInt();

                switch (choice) {
                    case 0:
                        System.out.println("Exit selected. Goodbye.");
                        System.exit(1);

                    case 1:
                        // Start Printer server
                        System.out.println(service.start());
                        Thread.sleep(2000);
                        break;

                    case 2:
                        // Print a file on a specific printer
                        try {
                            System.out.print("Enter file you wish to print.");
                            String str = input.next();
                            System.out.println("On which printer do you wish to print this file on?");
                            String printer = input.next();
                            System.out.println(service.print(str, printer));
                            Thread.sleep(2000);
                            break;
                        } catch (Exception e) {
                            System.out.println("No such file or printer exist :/...");
                            Thread.sleep(2000);
                            break;
                        }

                    case 3:
                        // Queue Options
                        int option;
                        System.out.println("================================");
                        System.out.println("1. List the print queue for a given printer.");
                        System.out.println("2. Move job to top of queue.");
                        option = input.nextInt();
                        switch (option) {
                            case 1:
                                // Print queue list
                                System.out.println("Which printer's queue do you wish to visualize?");
                                System.out.println(service.queue(input.next()));
                                break;
                            case 2:
                                // Put specific job on top of specific printer's queue
                                System.out.println("Which printer's queue do you wish to update?");
                                String printer = input.next();
                                System.out.println("Which job do you wish to put on top?");
                                System.out.println(service.topQueue(printer, input.nextInt()));
                                break;
                        }
                        break;

                    case 4:
                        // Restart print server
                        System.out.println(service.restart());
                        break;

                    case 5:
                        // Printer status
                        System.out.println("Which printer's status do you wish to see?");
                        System.out.println(service.status(input.next()));
                        break;

                    case 6:
                        // Configuration
                        // Queue Options
                        int option2;
                        System.out.println("================================");
                        System.out.println("1. Read the configuration of a certain parameter.");
                        System.out.println("2. Set the configuration of a certain parameter.");
                        option2 = input.nextInt();
                        switch (option2) {
                            case 1:
                                // Print queue list
                                System.out.println("Which parameter do you wish to visualize?");
                                System.out.println(service.readConfig(input.next()));
                                break;
                            case 2:
                                // Put specific job on top of specific printer's queue
                                System.out.println("Which parameter do you wish to update?");
                                String param = input.next();
                                System.out.println("What value should it have?");
                                System.out.println(service.setConfig(param, input.next()));
                                break;
                        }
                        break;

                    case 7:
                        System.out.println(service.stop());
                        break;

                    case 8:
                        System.out.println("Insert your name: (Max 20 characters)\n");
                        String name = input.next();
                        System.out.println("Insert your username: (Max 10 characters)\n");
                        String ID = input.next();
                        System.out.println("And finally, type in your password (Away from prying eyes!):\n");
                        String password = input.next();
                        service.newUser(name, ID, password);
                        break;
                }
            }
        }
    }
}

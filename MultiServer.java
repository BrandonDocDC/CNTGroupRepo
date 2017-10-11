package multiserver;

//Imports
import java.io.*;
import java.net.*;

//CNT4504 Project 1 
//Brandon DeCrescenzo, Tytus Hamilton, Cina Kim, Chelsea Saffold, and Kevin Serrano
public class MultiServer {

    public static void main(String[] args) throws IOException {

        //ERROR: If portNumber is incorrect  
        if (args.length < 1) {
            System.err.println("Usage: java server <port number>");
            System.exit(1);
        }
        
        //Global variables 
        int portNumber = Integer.parseInt(args[0]);

        //If port number is correct, proceed...
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server started, listening on port " + portNumber + " ...");
            System.out.println("Waiting for clients...");
            Socket clientSocket = serverSocket.accept(); 
            System.out.println("Client connected... Waiting for a request...");
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //While socket is open and listening...
            while (true) {
                //Local variables 
                String option = in.readLine();
                Process cmdProc; 
                String cmdans; 

                //Execute command associated with option 
                switch (option) {
                    //Current Date & Time 
                    case "1":
                        System.out.println("Responding to current time & date request from the client...");
                        String[] cmd = {"bash", "-c", "date +%D%t%T%Z"};
                        cmdProc = Runtime.getRuntime().exec(cmd);
                        break;
                    //Uptime 
                    case "2":
                        System.out.println("Responding to uptime request from the client...");
                        String[] cmdA = {"bash", "-c", "uptime -p"};
                        cmdProc = Runtime.getRuntime().exec(cmdA);
                        break;
                    //Memory Use 
                    case "3":
                        System.out.println("Responding to memory use request from the client...");
                        String[] cmdB = {"bash", "-c", "free -m"};
                        cmdProc = Runtime.getRuntime().exec(cmdB);
                        break;
                    //Netstat 
                    case "4":
                        System.out.println("Responding to netstat request from the client...");
                        String[] cmdC = {"bash", "-c", "netstat -r"};
                        cmdProc = Runtime.getRuntime().exec(cmdC);
                        break;
                    //Current Users 
                    case "5":
                        System.out.println("Responding to current users request from the client...");
                        String[] cmdD = {"bash", "-c", "users"};
                        cmdProc = Runtime.getRuntime().exec(cmdD);
                        break;
                    //Running Processes 
                    case "6":
                        System.out.println("Responding to running processes request from the client...");
                        String[] cmdE = {"bash", "-c", "ps"};
                        cmdProc = Runtime.getRuntime().exec(cmdE);
                        break;
                    //Quit 
                    case "7":
                        System.out.println("Quitting...");
                        String[] cmdF = {"bash", "-c", "exit"};
                        cmdProc = Runtime.getRuntime().exec(cmdF);
                        clientSocket.close();
                        in.close();
                        out.close();
                        break;
                    default:
                        System.out.println("Unknown request...");
                        return;
                }//End switch

                //Output answer from command to client 
                BufferedReader cmdin = new BufferedReader(new InputStreamReader(cmdProc.getInputStream()));
                while ((cmdans = cmdin.readLine()) != null) {
                    out.println(cmdans);
                    if (cmdans.equalsIgnoreCase("Bye.")) {
                        out.println("Bye.");
                        break;
                    }//End if
                }//End while
                out.println("Bye.");
            }//End while

            /*Keep server open and accept multiple clients
            while (true) {

                new MultiThread(serverSocket.accept()).start();
            }*/
        } //Catch errors 
        catch (IOException e) {
            System.out.println("Exception caught" + e);
        }

    }
}

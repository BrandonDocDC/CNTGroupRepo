
import java.io.*;
import java.net.*;

public class MultiServer {

    public static void main(String[] args) throws IOException {

        //Variables
        int portNumber = Integer.parseInt(args[0]);
        MultiThread t = new MultiThread("THREAD"); 

        //Error if the user executes the java without appropriate parameters 
        if (args.length < 1) {
            System.err.println("\n\nYou need the port: java Server <port number>");
            System.exit(1);
        }//End if

        //Open socket to listen for client 
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket socket = serverSocket.accept();
            System.out.println("\nServer started. Listening on Port " + portNumber);
            System.out.println("\nWaiting for clients");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true); //keep the output open
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //listen for input

            //Listen for client requests while socket is open
            while (true) {
                //Variables 
                String option = in.readLine();
                String thread = in.readLine(); 
                int threadCount = Integer.parseInt(thread);
                String send = "str";
                Process cmdProc;
                cmdProc = null;
                int minMen = 0;
                int maxMen = 8;
                
                //Create threads 
                for (int i = 0; i < threadCount; i++){
                    t.start(); 
                }

                //Check menu choice   
                if (option.equalsIgnoreCase("/*!@#$%^&*()\"{}_[]|\\?/<>,.")) {
                    System.err.println("Unrecognized option...please try again");
                    return;
                }//end if

                //Execute linux command
                switch (option) {
                    case "1":
                        System.out.println("Responding to date request from the client ");
                        String[] cmd = {"bash", "-c", "date +%D%t%T%t%Z"};
                        cmdProc = Runtime.getRuntime().exec(cmd);
                        break;
                    case "2":
                        System.out.println("Responding to uptime request from the client ");
                        String[] cmdA = {"bash", "-c", "uptime -p"};
                        cmdProc = Runtime.getRuntime().exec(cmdA);
                        break;
                    case "3":
                        System.out.println("Responding to number of active socket connections request from the client ");
                        String[] cmdB = {"bash", "-c", "free -m"};
                        cmdProc = Runtime.getRuntime().exec(cmdB);
                        break;
                    case "4":
                        System.out.println("Responding to netstat request from the client ");
                        String[] cmdC = {"bash", "-c", "ss -t -a | grep ESTAB | wc -l"};
                        cmdProc = Runtime.getRuntime().exec(cmdC);
                        break;
                    case "5":
                        System.out.println("Responding to current users request from the client ");
                        String[] cmdD = {"bash", "-c", "users"};
                        cmdProc = Runtime.getRuntime().exec(cmdD);
                        break;
                    case "6":
                        System.out.println("Responding to current processes request from the client ");
                        String[] cmdE = {"bash", "-c", "ps -aux | less"};
                        cmdProc = Runtime.getRuntime().exec(cmdE);
                        break;
                    case "7":
                        System.out.println("Quitting...");
                        String[] cmdF = {"bash", "-c", "exit"};
                        cmdProc = Runtime.getRuntime().exec(cmdF);
                        socket.close();
                        System.out.println("Socket closed.\n");
                        break;
                    default:
                        System.out.println("Unknown request ");
                        return;
                }//End switch

                if (option.equals("3") || option.equals("4") || option.equals("6")) {
                    out.println("");
                }//End if
                
                //Display result to client 
                else {
                    BufferedReader cmdin = new BufferedReader(new InputStreamReader(cmdProc.getInputStream()));
                    String cmdans;
                    while ((cmdans = cmdin.readLine()) != null) {
                        out.println(cmdans);
                    }
                }//End else
                
                out.println("Finished...");
            }//End while 
            
            //Kill threads 
            
        }//End try
        
        catch (IOException e) {
            System.out.println("\nException caught" + e);
        }//End catch
    }//End main
}//End server 


import java.io.*;
import java.net.*;
import java.util.*;

public class MultiClient {

    //Variables
    public static String userInput;
    public static String stopVar = "Stop";
    public static int threads, option;

    public static void main(String[] args) throws IOException {

        //Variables
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        //Error if the user executes the java without appropriate parameters
        if (args.length != 2) {
            System.err.println("You need the server and port: java MultiClient <host name> <port number>");
            System.err.println("  --                 Example: java MultiClient 192.168.100.102 3333");
            System.exit(1);
        }//End if

        //When successful, it will establish a socket
        try {
            Socket clientSocket = new Socket(hostName, portNumber);

            //While the socket is open, it will listen for host response and display menu after
            while (true) {

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); // doorstop to keep listening on socket
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // in is the input from server response
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)); //user menu input
                Scanner s = new Scanner(System.in); // s var to loop executions

                long start_time = System.currentTimeMillis();

                System.out.println("1) Host Current Date and Time\n"
                        + "2) Host Current Uptime\n"
                        + "3) Host Current Memory Use\n"
                        + "4) Host Current Netstat\n"
                        + "5) Host Current Users\n"
                        + "6) Host Running Processes\n"
                        + "7) Quit\n");

                System.out.println("Select your option: ");
                option = stdIn.read();
                
                System.out.println("How many threads should be running in the background?");
                threads = s.nextInt();

                switch (option) {
                    case '1':
                        out.println("1");
                        System.out.println("");
                        System.out.println("Current Date and Time: " + in.readLine());
                        break;
                    case '2':
                        out.println("2");
                        System.out.println("");
                        System.out.println("Current Uptime: " + in.readLine());
                        break;
                    case '3':
                        out.println("3");
                        System.out.println("");
                        while (in.readLine() != null) {
                            System.out.println("Current Memory Use: " + in.readLine());
                        }
                        break;
                    case '4':
                        out.println("4");
                        System.out.println("");
                        while (in.readLine() != null) {
                            System.out.println("Current Netstat: " + in.readLine());
                        }
                        break;
                    case '5':
                        out.println("5");
                        System.out.println("");
                        System.out.println("Current Users: " + in.readLine());
                        break;
                    case '6':
                        out.println("6");
                        System.out.println("");
                        while (in.readLine() != null) {
                            System.out.println("Current Running Processes: " + in.readLine());
                        }
                        break;
                    case '7':
                        out.println("7");
                        System.out.println("");
                        System.out.println(in.readLine());
                        return;
                    default:
                        System.err.println("Error! Unrecognized option.");
                        continue;
                } //end switch

                while ((userInput = in.readLine()) != null && !userInput.equalsIgnoreCase("Finished")) {
                    out.println(userInput);
                }//end while

                long end_time = System.currentTimeMillis();

                //Print length of time and status of option
                System.out.println("  --  Completed in " + (end_time - start_time) + "ms");
            }// end while loop
        }//end try
        
        //Catches 
        catch (NumberFormatException e) {
            System.out.print("  --  Please enter valid option!!\n");
            System.exit(1);
        }//End catch
        
        catch (UnknownHostException e) {
            System.err.println("  --  Unknown Host: " + hostName);
            System.exit(1);
        }//End catch
        
        catch (IOException e) {
            System.err.println("  --  Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }//End catch
        
        catch (InputMismatchException e) {
            System.err.println("  --  Unrecognized input " + e);
        }//End catch
    }//End main
}//End class 

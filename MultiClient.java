package multiclient;

//Imports
import java.io.BufferedReader;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

//CNT4504 Project 1 
//Brandon DeCrescenzo, Tytus Hamilton, Cina Kim, Chelsea Saffold, and Kevin Serrano
public class MultiClient {

    //Global variables
    public static String userInput;
    public static int times, option;

    public static void main(String[] args) throws IOException {

        //Local variables     
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        String serverResponse;

        //ERROR: If hostName or portNumber is wrong 
        if (args.length != 2) {
            System.err.println("Usage: java client <host name> <port number>");
            System.exit(1);
        }//End if 

        //If hostName and portNumber is correct, proceed...
        try {
            //Create socket 
            Socket clientSocket = new Socket(hostName, portNumber);

            //While socket is open and listening...
            while (true) {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                Scanner s = new Scanner(System.in);
                int numThreads = 0;

                //Ask client how many threads should be executed 
                System.out.println("How many threads would you like to execute this session?\n");
                numThreads = s.nextInt();

                //Create threads
                for (int i = 0; i < numThreads; i++) {
                    ClientThread client = new ClientThread(clientSocket);
                    client.start();
                    System.out.println("Starting thread " + i);
                }//End for loop

                //Display menu to client 
                System.out.println("1) Host Current Date and Time\n"
                        + "2) Host Uptime\n"
                        + "3) Host Memory Use\n"
                        + "4) Host Netstat\n"
                        + "5) Host Current Users\n"
                        + "6) Host Running Processes\n"
                        + "7) Quit\n");

                //Ask client to select an option 
                System.out.println("Please, select an option: ");
                option = stdIn.read();

                //Start timer 
                long start_time = System.currentTimeMillis();

                //Switch for menu options
                switch (option) {
                    //Host Current Date & Time
                    case '1':
                        out.println("1");
                        System.out.println("Current Date & Time: " + in.readLine());
                        break;
                    //Host Uptime
                    case '2':
                        out.println("2");
                        System.out.println("Uptime: " + in.readLine());
                        break;
                    //Host Memory Use
                    case '3':
                        out.println("3");
                        System.out.println("Memory Use: " + in.readLine());
                        while ((serverResponse = in.readLine()) != null && !serverResponse.equals("Bye.")) {
                            System.out.println(serverResponse);
                        }
                        continue;
                    //Host Netstat
                    case '4':
                        out.println("4");
                        System.out.println("Netstat: " + in.readLine());
                        while ((serverResponse = in.readLine()) != null && !serverResponse.equals("Bye.")) {
                            System.out.println(serverResponse);
                        }
                        serverResponse = null;
                        continue;
                    //Host Current Users 
                    case '5':
                        out.println("5");
                        System.out.println("");
                        String users = in.readLine();
                        System.out.println("Current Users: " + in.readLine());
                        break;
                    //Host Running Processes 
                    case '6':
                        out.println("6");
                        System.out.println("Running Processes: " + in.readLine());
                        while ((serverResponse = in.readLine()) != null && !serverResponse.equals("Bye.")) {
                            System.out.println(serverResponse);
                        }
                        serverResponse = null;
                        continue;
                    //Quit 
                    case '7':
                        out.println("7");
                        System.out.println("Quiting..." + in.readLine());
                        return;
                    //Invalid Input 
                    default:
                        System.err.println("ERROR! Invalid input... Please type a number between 1 and 7.");
                        continue;
                }//End switch
                //End timer 
                long end_time = System.currentTimeMillis();
                System.out.println("\nRequest is complete...\n");
                System.out.println("--- Execution time: " + (end_time - start_time) + "ms");
            }//End while 
        }//End try
        //Catch errors 
        //If input for menu is not in number format 
        catch (NumberFormatException e) {
            System.out.print("Please enter a valid option...\n");
            System.exit(1);
        }//End catch 
        //If hostName is incorrect
        catch (UnknownHostException e) {
            System.err.println("Unable to find " + hostName + " ...");
            System.exit(1);
        }//End catch
        //If server cannot connect to host 
        catch (IOException e) {
            System.err.println("Couldn't connection to " + hostName + " ...");
            System.exit(1);
        }//End catch
        //If input is incorrect
        catch (InputMismatchException e) {
            System.err.println("Unrecognized input: " + e + " Please enter a valid option...");
        }//End catch

    }//End main

}//End MultiClient


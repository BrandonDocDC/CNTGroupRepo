import java.io.*;
import java.net.*;

public class Server extends Thread {
	//Global Vars
	Socket s = null;
    
	//Thread Method
    public Server(Socket clientSocket){
        this.s = clientSocket;
    } //end constructor
    
	//run method
    @Override
    public void run() {
		//Announce new client connection
		System.out.println("Client connected...starting " + Thread.currentThread().getName() );

		//Listen
		try {
			PrintWriter out = new PrintWriter(s.getOutputStream(), true); //keep the output open
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream())); //listen for input

			//while it is open (listening for requests)
			while(true){     
				//local vars
				String option = in.readLine();
				String send = "str";
				Process cmdProc;
				cmdProc=null;
				String cmdans=null;
				
				//Checks menu choice
				if (option.equalsIgnoreCase( "/*!@#$%^&*()\"{}_[]|\\?/<>,.")) {
					System.err.println("Unrecognized option...please try again");
					return;
				}//end if
	
				//exectute commend in linux format
				switch (option){
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
						String[] cmdC = {"bash", "-c", "netstat -r"};
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
						s.close();
						System.out.println("Socket closed.\n");
					break;
					default:
						System.out.println("Unknown request ");
					return;
				}//end switch
				
				//output the answer from the bash command to the Client
				BufferedReader cmdin = new BufferedReader(new InputStreamReader(cmdProc.getInputStream()));
				while((cmdans = cmdin.readLine()) != null) {
					out.println(cmdans);
					if (cmdans.equalsIgnoreCase("Bye.")) {
						break;
					}//end if
				}//end while
				out.println("Bye.");
			}//end while
		}//end try
		catch (IOException e){
			System.out.println("Exception caught " + e);
			System.out.println(e.getMessage());
		}// end catch
		catch (NullPointerException e) {
			System.out.println("  --  Client disconnected.");
			System.exit(1);
		}//end catch

	}//end run()

	//main method
    public static void main(String[] args) throws IOException {
		//local vars
		int portNumber = Integer.parseInt(args[0]);
        
		//must specify a port number when running application  
        if(args.length < 1){
            System.err.println("\n\nYou need the port: java Server <port number>");
			System.err.println("  --      Example: java Server 3333");
            System.exit(1);
        }

		//opens the socket to listen for client.
        try {
			ServerSocket serverSocket = new ServerSocket(portNumber);
			System.out.println("\nServer started. Listening on Port " + portNumber);
			System.out.println("\nWaiting for clients");

			//Keep server open and accept multiple clients
			while(true){      
				new Server(serverSocket.accept()).start();
			}//End while
        }//End try
        catch (IOException e){
            System.out.println("\nException caught" + e);
        }//End Catch
    }//End main
}//end Server
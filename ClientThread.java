package multiclient;

//Imports
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//CNT4504 Project 1 
//Brandon DeCrescenzo, Tytus Hamilton, Cina Kim, Chelsea Saffold, and Kevin Serrano 
public class ClientThread extends Thread{
    //Variables 
    Socket socket = null;

    //Constructor 
    public ClientThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    //Run thread
    @Override
    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true); 
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }//End try 
        //Catch errors 
        catch (IOException e) {
            System.out.println("Exception caught..." + e);
        }//End catch 
    }//End run 
}//End ClientThread

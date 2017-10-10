
import java.io.*;
import java.net.*;

public class MultiThread extends Thread {
    
    private Thread thread; 
    private String threadName; 
    
    public MultiThread(String name) {
        this.threadName = name; 
    } //End constructor
    
    @Override
    public void run(){
        System.out.println("Running " + threadName); 
    }
    
    @Override
    public void start(){
        System.out.println("Starting " + threadName); 
        thread.start(); 
    }
    
} //end class MultiThread

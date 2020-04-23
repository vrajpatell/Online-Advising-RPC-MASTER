/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advising;

/**
 *
 * @author Vraj Patel
 */
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;


public class Notification {
	Notification()        //constructor for notificaton process do nothing.
	{

	}
	static Scanner scanner = new Scanner(System.in);
	public static void main(String args[]) throws IOException, InterruptedException 
	{
		myfunction1();   //this is the whole notification process to be done 
	}
		
		
		
		
	public static void myfunction1() throws InterruptedException
	{	
		try {
			Registry registry = LocateRegistry.getRegistry(1090) ;        //rmi registry with port 1090

			ServerInterface server = (ServerInterface) registry.lookup("Stub") ; //rmi stub 
			System.out.println("Notification started");      //commencement for notification process
			
			while(true) {								// loop to make notification process not to teriminate
				ArrayList<String> requests = server.getDecision();      //input is an arraylist from the server which has messages to be displayed on the student process.
				System.out.println(requests);			//displaying the decision of the advisor process to student process.
				
				if(requests.isEmpty())   // if there are no messeges to be displayed in the notification process
				{
					System.out.println("no message found");
					Thread.sleep(7000);      //sleep for 7 sceonds 
					myfunction1();			// again contact the message queue server
				}
				// delete code
				server.deleteMessages(requests);    // for deleting messages from the data structure.
			}
		} 
	catch (RemoteException | NotBoundException e)
	{
			}

	}
}
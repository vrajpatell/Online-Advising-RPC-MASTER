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
import java.util.Random;


public class Advising {
	Advising(){    // constructor for advisor process do nothing.

	}
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String args[]) throws IOException, NotBoundException, InterruptedException 
	{

			myfunction();   // the advisor process is called
		}

		
		public static void myfunction() throws InterruptedException
		{
		int value;
		try 
		{
			Registry registry = LocateRegistry.getRegistry(80443) ;         // rmi registry port 1090
			ServerInterface server = (ServerInterface) registry.lookup("Stub") ; 
			System.out.println("Advisor started");     //commencement for adviosor process
			while(true) {				// loop to keep the process non-terminated.
				ArrayList<String> requests = server.getClearenceRequests();	// input from the server for clearance of the requests sent by student process.
				System.out.println(requests);			//displaying of requests
				if(requests.isEmpty()) 				//if there are no requests found for clearance
				{
					System.out.println("no message found");
					Thread.sleep(3000);   //sleep for 3 seconds
					myfunction();			// again check for messages in the server.
				}
				 

				for (int i = 0; i < requests.size(); i++) 
				 {
					    Random random = new Random();     // random function for clearance
						boolean n = random.nextBoolean();
						String entry = requests.get(i);
						String [] entrSplit = entry.split(" ");		//split for checking the requests are assigned or not
						if(entrSplit[2].equalsIgnoreCase("CR")) 
						{
							if(n==true) 
							{
								String ent = entry.replace("CR","APPROVED");   //approving if random function returns true.
								requests.set(i, ent);  //setting the value in the data structure
							}
							else
							{
								String ent=entry.replace("CR","DIS-APPROVED");  //dis-approving if random function returns false.
								requests.set(i, ent);
							}
						}			
						}		
				System.out.print("displaying the decision on advisor process console.");
				 System.out.print(requests);    // displaying the results or decision in advisor process.
				 server.setClearenceRequests(requests);   // setting the decision in the datastructure present in the server.
			}
			
		} catch (RemoteException | NotBoundException e)
		{
		}
		}
	}

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
import java.util.Scanner;


public class Student {
	Student(){		// student default constructer

	}
	static Scanner scanner = new Scanner(System.in);

	public static void main(String args[]) throws IOException {
		try {
			Registry registry = LocateRegistry.getRegistry(1090) ;  //rmi registry port 1090

			ServerInterface server = (ServerInterface) registry.lookup("Stub") ; 
			System.out.println("Student started");		//commencement of student process
			while(true) {			//to make it not to teiminate.
				String input = scanner.nextLine();
				input = input + " CR";   //adding a third filed and saving.
				//System.out.println("Message sent from Msg Queue: "+ input);
				server.getClearence(input);   // it is sending the request to advisor process via message queue server.
			}
		} catch (RemoteException | NotBoundException e) {
		}

	}
}
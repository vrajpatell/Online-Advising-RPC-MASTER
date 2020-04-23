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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.rmi.Remote;

public class Server extends ServerInterface{
	
    ArrayList<String> messages = new ArrayList<>();	// the data structure chosen is an array list named messages.

	private String Line;
	Server(){
		
		try (BufferedReader br = new BufferedReader(new FileReader("C:/Apps/test.txt"))) {  //file path is as given
		    String line;
		    while ((line = br.readLine()) != null) {	//to load the data structure from the file if server shutdown and starts.
		       messages.add(line);		//adding the requests from file to data structure.
		       System.out.println(messages);   // displaying the messages from server side which are loaded into the data structure.
		    }
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

	

	public static void main(String args[]) {
		try {
			Registry registry = LocateRegistry.createRegistry(1096) ;	
			ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject((Remote) new Server(), 1096);
			registry.rebind("Stub", (Remote) stub);
			while(true) {		//to make server not to sleep until we need to.

			}
		}
		catch(RemoteException e) {
			System.err.println(e.getMessage());
		}
	}

	public void persistMessages(String s){

		try {
			System.out.println("Message Received from Student:"+s);		//displaying of messages requests from student process
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:/Apps/test.txt",true)) // writing to the file
                    ) {
                        messages.add(s);  //adding to the data structure
                        writer.write(s);
                        writer.newLine();
                        writer.flush();
                        //	System.out.println("file written");
                    } //adding to the data structure
		} catch (Exception iox) {
		}

	}

    /**
     *
     * @param s
     * @throws RemoteException
     */
    public void getClearence(String s) throws RemoteException {
		// TODO Auto-generated method stub
		persistMessages(s);   // this saves the incoming request to the file and the data structure.  
	}

    /**
     *
     * @return
     * @throws RemoteException
     */
 
	public ArrayList getClearenceRequests() throws RemoteException {
		ArrayList<String> requests = new ArrayList<>();    // to send the requests to advisor process comparing the request which should be given clearance from the data structure
		for (int i = 0; i < messages.size(); i++) {
			String entry = messages.get(i);
			String [] entrSplit = entry.split(" ");
			if(entrSplit[2].equals("CR")) {  // to check the third string and then giving clearance.
				requests.add(messages.get(i));  // adding it into another datastructure and sending.
			}
		}
		return requests;
	}
	
    /**
     *
     * @param a
     * @throws RemoteException
     */
	public void setClearenceRequests(ArrayList<String> a) throws RemoteException 
	{
		// TODO Auto-generated method stub
		try {
			System.out.println("Message from advisor:");     //this is from adivisor. to set the decision into the data structure.
			for(int i = 0; i < messages.size(); i++)
			{
					for(int j = 0; j < a.size(); j++) {
						String entry = messages.get(i);			//splitting the data structure
						String [] entrSplit = entry.split(" ");		
						
						String entry1 = a.get(j);				//splitting the datastructure received from the advisor.
						String [] entrSplit1 = entry1.split(" ");
						
						if(entrSplit[0].equalsIgnoreCase(entrSplit1[0])) {			//comparing the student name
							if(entrSplit[1].equalsIgnoreCase(entrSplit1[1])) {		//comparing the course name.
								String value = messages.get(i);
								value = value.replace(messages.get(i), a.get(j));		//replacing the string with decision.
							    messages.set(i, value);
						}					
						}
					}
			}
			 System.out.print(messages);	
			 
			 // this is to update the file too
			 try {

		            File f = new File("C:/Apps/test.txt");
		            BufferedReader b = new BufferedReader(new FileReader(f));
		            String readLine = "";
		            System.out.println("Reading file using Buffered Reader");
		            while ((readLine = b.readLine()) != null) 
		            {
		                System.out.println(readLine);
		                		                
		                for(int i = 0; i < messages.size(); i++)
		    			{
		    					String entry = messages.get(i);		//same as data structure splitting the data structure and comparing with line ina file.
		    					String [] entrSplit = entry.split(" ");
		    					
		    					String [] entrSplit1 = readLine.split(" ");
		    					
		    					if(entrSplit[0].equalsIgnoreCase(entrSplit1[0]))	//comparing the student name.
		    					{
		    						if(entrSplit[1].equalsIgnoreCase(entrSplit1[1])) //comparing the course name.
		    						{
		    							
		    							String newtext =readLine.replaceAll(readLine, entry);
		    				            //System.out.println(newtext);
                                                                        try (BufferedWriter writer1 = new BufferedWriter(new FileWriter("C:/Apps/test1.txt",true))) {
                                                                            writer1.write(newtext);// updating the decision in the file.
                                                                            writer1.newLine();
                                                                            writer1.flush();
                                                                            //System.out.println("file written");
                                                                        } // updating the decision in the file.
		    				            
		    							
		    						}
		    								
		    					}
		    				}
		            }

		                
		                //for ends
		            //replacing the file by creating a temporary file and deleting it.
		    					FileInputStream instream = null;
					    		FileOutputStream outstream = null;
							try
							{
					    	    File infile =new File("C:/Apps/test1.txt");
					    	    File outfile =new File("C:/Apps/test.txt");
					    	   
					    	    instream = new FileInputStream(infile);
					    	    outstream = new FileOutputStream(outfile);
					 
					    	    byte[] buffer = new byte[1024];
					 
					    	    int length;
					    	    /*copying the contents from input stream to
					    	     * output stream using read and write methods
					    	     */
					    	    while ((length = instream.read(buffer)) > 0)
					    	    {
					    	    	outstream.write(buffer, 0, length);
					    	    }
					    	    
					    	    
					    	    
					    	    //Closing the input/output file streams
					    	    instream.close();
					    	    outstream.close();
					    	    infile.delete();
					    	    //System.out.println("File copied successfully!!");
					    	    }
							catch(IOException ioe)
							{							}

										
		            }

			 catch (IOException e)
			 {
				 }
			
			}
		catch (Exception iox) 
		{
		}
	}
	
    /**
     *
     * @return
     * @throws RemoteException
     */
	public ArrayList getDecision() throws RemoteException {	//getdecision is for notifiction process to display the decision.
		ArrayList<String> requests = new ArrayList<>();
		for (int i = 0; i < messages.size(); i++) {	//filtering the messages processed by the advisor process.
			String entry = messages.get(i);
			String [] entrSplit = entry.split(" ");
			if(!(entrSplit[2].equals("CR"))) {
				requests.add(messages.get(i));	//adding it into data structure and returning it to the notification process for displaying.
			}
		}
		return requests;	
	}

    /**
     *
     * @param a
     * @throws RemoteException
     */
	public void deleteMessages(ArrayList<String> a)throws RemoteException {  // this method is to delete requests that are processed by the advisor and displayed by the notification process.
		try {
			System.out.println("Message from notification:");
					
			FileInputStream instream = null;
    		FileOutputStream outstream = null;
		try
		{
    	    File infile =new File("C:/Apps/test3.txt");
    	    File outfile =new File("C:/Apps/test.txt");
    	   
    	    instream = new FileInputStream(infile);
    	    outstream = new FileOutputStream(outfile);
    	    BufferedReader reader = new BufferedReader(new FileReader(infile));
    	    BufferedWriter writer = new BufferedWriter(new FileWriter(outfile)) ;
    	    byte[] buffer = new byte[1024];
    	    StringBuilder strbuild = new StringBuilder() ;
    	    String data = null ;
    	    while ((data = reader.readLine()) != null) {
    	    	if(!(a.get(0).equals(data.split(" ")[0]) &&
    	    			a.get(1).equals(data.split(" ")[1]) &&
    	    			(a.get(2).equals(data.split(" ")[2])))) {
    	    		strbuild.append(data).append("\n") ;		//comapring the name and couse and decision.
    	    	}
    	    }
    	    writer.write(strbuild.toString());
    	    int length;
    	    /*copying the contents from input stream to
    	     * output stream using read and write methods
    	     */
    	    while ((length = instream.read(buffer)) > 0)
    	    {
    	    	outstream.write(buffer, 0, length);
    	    }

    	    //Closing the input/output file streams
    	    instream.close();
    	    outstream.close();
    	   // infile.delete();
    	    System.out.println("File copied successfully!!");
    	    }
		catch(IOException ioe)
		{		}
		
		
		
			for(int i = 0; i < messages.size(); i++)
			{
				for(int j = 0; j < a.size(); j++) {
					String entry = messages.get(i);
					String [] entrSplit = entry.split(" ");
					
					String entry1 = a.get(j);
					String [] entrSplit1 = entry1.split(" ");
					
					if(entrSplit[0].equalsIgnoreCase(entrSplit1[0])) {		//comparing the name
						if(entrSplit[1].equalsIgnoreCase(entrSplit1[1])) {	//comparing the course name
							if(entrSplit[2].equalsIgnoreCase(entrSplit1[2])) {				//comparing the decision.
				messages.remove(i);			// removing the displaye dmessage from the data structure.
					}					
					}
					}
					
				}
			}
			System.out.print(messages);
			
			
	}catch (Exception iox) 
		{
	}
}

}
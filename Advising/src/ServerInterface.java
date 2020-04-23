import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerInterface extends Remote {
	public void storeStudentInfo(ArrayList<StudInfo> si) throws RemoteException;							// save the data to the file
	public ArrayList<StudInfo> fetchMessage()throws RemoteException;										// fetch all data from the file			
}

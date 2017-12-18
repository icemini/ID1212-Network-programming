package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

//remote methods server can call on the client
public interface Client extends Remote{
	
	//method for output to client 
	void receiveMessage(String message) throws RemoteException;

}

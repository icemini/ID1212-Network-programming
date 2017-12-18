package server.startup;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import server.controller.Controller;

//starts the server and binds it in the RMIregistry
public class Main {
	
	public static void main(String[] args) {
		
        try {
        	
            new Main().startRegistry();
            //bind even if there was already an object bound with that name
            //pass the objects that is supposed to be called remotly (Controller class)
            Naming.rebind(Controller.SERVER_REGISTRY_NAME, new Controller());
            System.out.println("Server is started");
            
        } catch (MalformedURLException | RemoteException ex) {
        	
            System.out.println("Could not start server.");
        }
	}
	
	//finds and starts the registry
    private void startRegistry() throws RemoteException {
        try {
        	
        	//returns a reference to a registry (the remote interface)
        	//list all objects that are already bound on the registry
        	LocateRegistry.getRegistry().list();
        } 
        
        catch (RemoteException noRegistryIsRunning) {
            
        	//runs if there is no registry running
        	//starts the registry
        	LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        }
    }
}

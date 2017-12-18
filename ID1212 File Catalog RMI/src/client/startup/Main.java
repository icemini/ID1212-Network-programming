package client.startup;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import client.view.NonBlockingInterpreter;
import common.Server;

public class Main {
	
	// starts the nonblockinginterpreter
    public static void main(String[] args) {
        try {
        	// client retrieves the remote reference to the Controller
        	Server server = (Server) Naming.lookup(Server.SERVER_REGISTRY_NAME);
            new NonBlockingInterpreter().start(server);
        } 
        catch (RemoteException | MalformedURLException | NotBoundException ex) {
            System.out.println("Could not start client");
        }
    }
}

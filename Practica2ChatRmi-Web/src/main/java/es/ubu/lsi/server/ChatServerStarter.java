package es.ubu.lsi.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Clase ChatServerStarter
 * 
 * @author Marcos Guzman Asolas
 *
 */
public class ChatServerStarter {
	
	/**
	 * método main que pone en funcionamiento el servidor
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			
			// Inicia el registro RMI en el puerto 8080
            Registry registry = LocateRegistry.createRegistry(8080);
            
            // Crea una instancia del ChatServerImpl
            ChatServer server = new ChatServerImpl();
            
            // Publica el servidor de chat en el registro RMI
            Naming.rebind("/ChatServer", server);
            
            System.out.println("Servidor registrado...");
            
            while(server.getStatus()) {
            	 try {
                     Thread.sleep(1000); // Añadir una pequeña pausa para no consumir CPU innecesariamente
                 } catch (InterruptedException e) {
               	  System.out.println("El hilo del servidor fue interrumpido");
                     break;
                 }
            }
            // Apaga el servidor y termina la ejecución
            registry.unbind("/ChatServer");
            UnicastRemoteObject.unexportObject(server, true);
            System.out.println("Servidor parado");
            
		}catch (Exception e) {
			System.err.println("Chat server exception:");
			e.printStackTrace();
		}
	}

}

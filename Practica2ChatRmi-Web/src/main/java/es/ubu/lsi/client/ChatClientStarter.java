package es.ubu.lsi.client;

import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import es.ubu.lsi.server.ChatServer;
import es.ubu.lsi.common.ChatMessage;

/**
 * Chat client starter.
 * 
 * Esta clase inicia un cliente de chat, lo exporta como objeto remoto, y lo conecta a un servidor de chat remoto.
 * Luego solicita al usuario la entrada de texto y envía mensajes al servidor en función de la entrada del usuario.
 * Para iniciar el cliente, ejecute la clase con el siguiente comando: java ChatClientStarter <nickname> [host remoto]
 * 
 * El parámetro nickname es obligatorio y especifica el apodo del usuario en el chat.
 * El parámetro host remoto es opcional y especifica el nombre de host o dirección IP del servidor de chat remoto. Si no se especifica, se establece por defecto como "localhost".
 * Para cerrar la sesión en el chat, ingrese el comando "logout".
 * 
 * @author Marcos Guzman Asolas
 *
 */
public class ChatClientStarter {

public static void main(String[] args) {
		
		if (args.length < 1) {
			System.out.println("Usage: java ChatClientStarter <nickname> [remote host]");
			System.exit(1);
		}
		
		String nickname = args[0];
		String remoteHost = args.length > 1 ? args[1] : null;
		
		try {
			// Pasa el nickname como argumento, y lo exporta como un cliente remoto
			ChatClientImpl client = new ChatClientImpl(nickname);
			ChatClient stub = (ChatClient) UnicastRemoteObject.exportObject(client, 0);
			
			// Resuelve el servidor de chat remoto y registra el cliente
			ChatServer server = (ChatServer) Naming.lookup("//" + (remoteHost != null ? remoteHost : "localhost") + "/ChatServer");
			int clientId = server.checkIn(stub);
			
			// Lee la entrada del usuario y envía mensajes al servidor
			Scanner scanner = new Scanner(System.in);
			String input = "";
			while (!input.equals("logout")) {
				System.out.print("> ");
				input = scanner.nextLine();
				if (input.equals("shutdown")) {
					server.shutdown(stub);
					break;
				}
				ChatMessage message = new ChatMessage(clientId, nickname, input);
				server.publish(message);
			}
			
			// Anula el registro del cliente al salir
			server.logout(stub);
			UnicastRemoteObject.unexportObject(client, true);
			scanner.close();
			
		} catch (Exception e) {
			System.err.println("Chat client exception:");
			e.printStackTrace();
		}
	}
	
}

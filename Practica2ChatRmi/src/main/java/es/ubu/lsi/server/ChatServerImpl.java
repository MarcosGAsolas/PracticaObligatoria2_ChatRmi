package es.ubu.lsi.server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import es.ubu.lsi.client.ChatClient;
import es.ubu.lsi.common.ChatMessage;

/**
 * Clase ChatServerImpl
 * 
 * @author Marcos Guzman Asolas
 * 
 */
public class ChatServerImpl implements ChatServer {

	private List<ChatClient> clientes;
	private boolean serverOn;
	
	/**
	 * Constructor de la clase
	 */
	public ChatServerImpl() {
		clientes = new ArrayList<ChatClient>();
		serverOn = true;
	}
	
	/**
	 * Devuelve el estado del servidor on/off
	 * @return serverOn: true = on, false = off
	 */
	@Override
	public boolean getStatus() {
		return serverOn;
	}
	
	/**
	 * Permite cambiar o establecer el estado del servidor
	 * true = on, false = off
	 * @param status es un boolean
	 */
	public void setStatus(boolean status) {
		serverOn = status;
	}
	
	
	/**
	 * Añade clientes a la lista de conectados
	 * @param client: cliente que desea unirse
	 */
	@Override
	public int checkIn(ChatClient client) throws RemoteException {
		//recojo al cliente y lo meto en la lista de clientes
		clientes.add(client);
		//le aviso
		client.receive(new ChatMessage(0, "Servidor", "Bienvenido al servidor, ahora ya puedes escribir y todos te leeran"));
		//devuelvo el indice del cliente 
		return clientes.indexOf(client);
	}

	/**
	 * Elimina clientes de la lista de conectados
	 * @param client: cliente que desea desconectarse
	 */
	@Override
	public void logout(ChatClient client) throws RemoteException {
		// le aviso
		client.receive(new ChatMessage(client.getId(), "Servidor","Estas siendo desconectado del servidor"));
		//le quito de la lista
		clientes.remove(client);
	}
	
	/**
	 * Envia un mensaje a todos los clientes conectados
	 * @param msg: mensaje con el texto, el nickname y la id que se desea enviar
	 */
	@Override
	public void publish(ChatMessage msg) throws RemoteException {
		// Con un bucle for each usamos el método receive de todos los clientes conectados
		for(ChatClient cliente : clientes) {
			cliente.receive(new ChatMessage(msg.getId(),msg.getNickname() , msg.getMessage()));
		}	
	}

	/**
	 * Apaga el servidor desconectando a todos los clientes y posteriormente cambiando el estado del mismo
	 * @param client: cliente que solicita el apagado
	 */
	@Override
	public void shutdown(ChatClient client) throws RemoteException {
		//doy aviso
		for(ChatClient cliente : clientes) {
			cliente.receive(new ChatMessage(cliente.getId(),"Servidor" ,"El servidor se esta cerrando..."));
			this.logout(cliente);
		}
		//vacio la lista
		clientes.clear();
		//se apaga el server
		serverOn=false;
		
	}
	
}

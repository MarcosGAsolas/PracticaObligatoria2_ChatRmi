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
	 * Devuelve el estado del servidor si esta operativo o no
	 * @return serverOn: true = operativo, false = apagado
	 */
	@Override
	public boolean getStatus() {
		return serverOn;
	}
	
	/**
	 * Permite modifica el estado del servidor
	 * true = operativo, false = apagado
	 * @param boolean status 
	 */
	public void setStatus(boolean status) {
		serverOn = status;
	}
	
	
	/**
	 * Añade los clientes conectados a una lista de clientes 
	 * @param client
	 */
	@Override
	public int checkIn(ChatClient client) throws RemoteException {
		//recogemos y metemos el cliente en la lista
		clientes.add(client);
		//se avisa al cliente
		client.receive(new ChatMessage(0, "Servidor", "Bienvenido al servidor, ahora ya puedes escribir y todos te leeran"));
		//se devuelve el indice del cliente
		return clientes.indexOf(client);
	}

	/**
	 * Elimina los clientes desconectados de la lista
	 * @param client
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
	 * @param msg: mensaje 
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

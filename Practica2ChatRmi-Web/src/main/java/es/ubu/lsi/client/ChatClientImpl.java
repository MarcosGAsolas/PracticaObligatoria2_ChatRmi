package es.ubu.lsi.client;

import java.rmi.RemoteException;
import es.ubu.lsi.common.ChatMessage;

/**
 * Clase ChatClientImpl
 * 
 * @author Marcos Guzman Asolas
 *
 */

public class ChatClientImpl implements ChatClient{

	private String nickname;
	private int id;

	public ChatClientImpl(String nickname) throws RemoteException {
		this.nickname = nickname;
	}
	
	@Override
	public int getId() throws RemoteException {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(int id) throws RemoteException {
		// TODO Auto-generated method stub
		this.id = id;
	}

	@Override
	public void receive(ChatMessage msg) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println(msg.getNickname() + ": " + msg.getMessage());
	}

	@Override
	public String getNickName() throws RemoteException {
		// TODO Auto-generated method stub
		return this.nickname;
	}

}

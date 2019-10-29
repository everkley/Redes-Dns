package br.edu.ifg.cliente;

import br.edu.ifg.interf.*;
import br.edu.ifg.model.Mensagem;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Chat {

	private String nick;

	InterfaceDNS remote;
	InterfaceMensagens remoteChat;

	public Chat(String nick) {
		this.nick = nick;
	}

	public ArrayList<String> obterListaUsuariosOnline() {

		try {
			return remote.obterListaUsuariosOnline();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void lookupDNS() {
		try {
			Registry registry = LocateRegistry.getRegistry("172.16.0.27", ConstantDNS.RMI_PORT);
//			Registry registry = LocateRegistry.getRegistry("localhost", ConstantDNS.RMI_PORT);
			remote = (InterfaceDNS) registry.lookup(ConstantDNS.RMI_ID);

		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}

	public void autentica() throws UnknownHostException, RemoteException {
		String ip = obtemMeuIP();
		remote.autentica(nick, ip);
	}

	public void lookupChat(String ip) {
		String ipAmigo = ip;

		try {
			Registry registry = LocateRegistry.getRegistry(ipAmigo, ConstantChat.RMI_PORT);
			remoteChat = (InterfaceMensagens) registry.lookup(ConstantChat.RMI_ID);

		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
	
	public void enviaMensagem(Mensagem mens) throws RemoteException{
		remoteChat.enviarMensagens(mens);
	}

	/**
	 * Metodo para obtencao do meu endereco IP
	 */
	public String obtemMeuIP() throws UnknownHostException {
		InetAddress ip = InetAddress.getLocalHost();
		String hostname = ip.getHostAddress();
		return hostname;
	}

	public String obterIpAmigo(String nickAmigo) throws RemoteException {
		String ipAmigo = remote.obterIP(nickAmigo);
		return ipAmigo;
	}
}
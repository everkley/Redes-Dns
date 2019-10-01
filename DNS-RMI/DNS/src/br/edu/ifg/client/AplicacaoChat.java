package br.edu.ifg.client;

import br.edu.ifg.interf.*;
import java.net.InetAddress;
import java.rmi.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AplicacaoChat {

	private AplicacaoChat() {
	}

	public static void main(String[] args) {
		
		try {
			Registry registry  = LocateRegistry.getRegistry("localhost",Constant.RMI_PORT);
			final InterfaceDNS remote = (InterfaceDNS) registry.lookup(Constant.RMI_ID);
			
			InetAddress ip = InetAddress.getLocalHost();
	        String hostname = ip.getHostAddress();
			
	        hostname=ip();
	        String nick = nameIP();
	        
			if (remote.autentica(nick, hostname)) {
				System.out.println("Usuario adicionado com sucesso!");
			} else {
				System.out.print("Erro ao adicionar!");
			}
			
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
	
	public static String ip() throws UnknownHostException, Exception {
		InetAddress ip = InetAddress.getLocalHost();
		String hostname = ip.getHostAddress();
		return hostname;
	}
	
	public static String nameIP() {
		JTextField campo = new JTextField();
		campo.setBounds(0, 0, 10, 10);
		campo.setVisible(true);
		String nick = JOptionPane.showInputDialog(campo, "Digite o nick");
		return nick;
	}
}
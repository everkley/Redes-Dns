package br.edu.ifg.cliente;

import java.net.UnknownHostException;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import br.edu.ifg.interf.InterfaceDNS;
import br.edu.ifg.model.Mensagem;

public class ChatMain {

	InterfaceDNS interfaceDNS;

	private ChatMain() {

	}

	public static void main(String[] args) {

		ServidorMensagens servidorMensagens = new ServidorMensagens();
		servidorMensagens.iniciarServidorMensagem();

		String nick = JOptionPane.showInputDialog("Digite seu nome: ");

		Chat chat = new Chat(nick);
		

		chat.lookupDNS();
		
		try {
			chat.autentica();
		} catch (UnknownHostException | RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(chat.obterListaUsuariosOnline());
		
		String nickAmigo = JOptionPane.showInputDialog("Digite o nome do seu amigo: ");
		String ipAmigo = "";

		try {
			ipAmigo = chat.obterIpAmigo(nickAmigo);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String conteudo = JOptionPane.showInputDialog("Digite sua mensagem: ");

		//mensagem.setConteudo(conteudo);

		chat.lookupChat(ipAmigo);
		
		Mensagem mensagem = new Mensagem(nick, conteudo);
//		mensagem.setRemetente(nick);

		try {
			chat.enviaMensagem(mensagem);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
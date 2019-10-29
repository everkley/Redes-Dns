package br.edu.ifg.cliente;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JOptionPane;

import br.edu.ifg.interf.ConstantChat;
import br.edu.ifg.interf.InterfaceMensagens;
import br.edu.ifg.model.Mensagem;

public class ServidorMensagens implements InterfaceMensagens {

	public void enviarMensagens(Mensagem mensagem) throws RemoteException {
		JOptionPane.showMessageDialog(null, mensagem.getRemetente()+ "disse: " + mensagem.getConteudo());
		System.out.println(mensagem.getRemetente() + " disse: " + mensagem.getConteudo());
	}

	public void iniciarServidorMensagem() {
		try {
			ServidorMensagens servidorMensagens = new ServidorMensagens();
			InterfaceMensagens mensagem = (InterfaceMensagens) UnicastRemoteObject.exportObject(servidorMensagens, 0);
			Registry registry = LocateRegistry.createRegistry(ConstantChat.RMI_PORT);

			registry.bind(ConstantChat.RMI_ID, mensagem);

			System.out.println("Servidor Mensagem ready!");

		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
}
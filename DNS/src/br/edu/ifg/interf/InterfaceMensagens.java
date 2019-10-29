package br.edu.ifg.interf;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.edu.ifg.model.Mensagem;

public interface InterfaceMensagens extends Remote {

	public void enviarMensagens(Mensagem mensagem) throws RemoteException;

}

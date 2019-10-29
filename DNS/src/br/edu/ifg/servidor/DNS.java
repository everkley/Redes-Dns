package br.edu.ifg.servidor;

import br.edu.ifg.interf.ConstantDNS;
import br.edu.ifg.interf.InterfaceDNS;
import br.edu.ifg.model.TabelaUsuarios;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class DNS implements InterfaceDNS {

	TabelaUsuarios tabelaUsuarios = new TabelaUsuarios();

	/**
	 * Construtor
	 */
	public DNS() {
	}

	/**
	 * Metodo main
	 * 
	 * @param args
	 * @throws RemoteException
	 */
	public static void main(String args[]) throws RemoteException {

		try {

			DNS dns = new DNS();
			InterfaceDNS interfDNS = (InterfaceDNS) UnicastRemoteObject.exportObject(dns, 0);

			Registry registry = LocateRegistry.createRegistry(ConstantDNS.RMI_PORT);

			registry.bind(ConstantDNS.RMI_ID, interfDNS);

			System.out.println("Servidor rodando!");

		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

	public boolean autentica(String nick, String ip) throws RemoteException {
		tabelaUsuarios.adicionarUsuario(nick, ip);
		System.out.println("Tabela de usuarios online: " + tabelaUsuarios.toString());
		return true;
	}

	public ArrayList<String> obterListaUsuariosOnline() throws RemoteException {
		HashMap<String, String> listaUsuariosOnline = new HashMap<String, String>();
		
		listaUsuariosOnline.putAll(tabelaUsuarios.getListaUsuariosOnline());
	
		System.out.println("Lista: " + listaUsuariosOnline.size());
		
		ArrayList<String> lista = new ArrayList<>();		
		for(String key : listaUsuariosOnline.keySet()) {
			System.out.println("chave: "+key);		
			lista.add(key);
		}	
		System.out.println("Lista:"+lista.size());
		return lista;
	}

	public String obterIP(String nick) throws RemoteException {
		return tabelaUsuarios.ipUsuario(nick);
	}
}
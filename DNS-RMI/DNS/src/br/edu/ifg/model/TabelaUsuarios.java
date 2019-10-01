package br.edu.ifg.model;

import java.util.ArrayList;
import java.util.HashMap;

public class TabelaUsuarios {

	HashMap<String, String> listaUsuariosOnline = new HashMap<String, String>();

	/**
	 * Metodo para adicionar usuario ao HashMap
	 * @param nick
	 * @param ip
	 */
	public void adicionarUsuario(String nick, String ip) {
		this.listaUsuariosOnline.put(nick, ip);
	}
	
	public HashMap<String, String> getListaUsuariosOnline() {
		return listaUsuariosOnline;
	}
	
	public String ipUsuario(String nick) {
		return listaUsuariosOnline.get(nick);
	}

	public void setListaUsuariosOnline(HashMap<String, String> listaUsuariosOnline) {
		this.listaUsuariosOnline = listaUsuariosOnline;
	}

	/**
	 * Metodo toString
	 */
	public String toString() {
		return "TabelaUsuarios [listaUsuariosOnline=" + listaUsuariosOnline + "]";
	}
	
	
	public ArrayList<String> arrayListaUsuario(){
		ArrayList<String> listaDeNicks = new ArrayList<String>();
		for(String key: listaUsuariosOnline.keySet()) {
			System.out.println(key);
			listaDeNicks.add(key);
		}
		return listaDeNicks;
	}
	
}

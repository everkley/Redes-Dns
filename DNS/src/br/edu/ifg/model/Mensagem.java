package br.edu.ifg.model;

import java.rmi.RemoteException;

public class Mensagem implements java.io.Serializable {

	private static final long serialVersionUID = 10L;

	private String conteudo;
	private String remetente;
	private String destinatario;

	public Mensagem(String nick, String conteudoMensagem) {
		this.remetente = nick;
		this.conteudo = conteudoMensagem;
	}

	public void enviarMensagem(Mensagem mensagem) throws RemoteException {
		System.out.println(mensagem.getRemetente() + "Disse:\n" + mensagem.getConteudo() + "\n Para:"
				+ mensagem.getDestinatario());
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public String getRemetente() {
		return remetente;
	}

	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Conteúdo: " + conteudo;
	}
}

package br.edu.ifg.tela;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.edu.ifg.cliente.Chat;
import br.edu.ifg.cliente.ServidorMensagens;
import br.edu.ifg.model.Mensagem;
import br.edu.ifg.model.ModeloTabelaUsuarios;

public class telaChat extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel containerPrincipal;

	private JTextArea txtChatTela;
	private JTextArea txtChat;
	private JTextField txtDestinatario;
	private JButton btnEnviar;
	private JButton btnLogout;

	private JTable tblUsuarios;
	private JScrollPane scrlUsuarios;
	private JScrollPane scrlChat;

	ArrayList<String> listaUsuarios = new ArrayList<String>();
	ServidorMensagens servidorMensagem = new ServidorMensagens();

	String conteudoMensagem;
	String nick;

	Mensagem mensagem = new Mensagem(nick, conteudoMensagem); // ALTERAR
	Chat chat = new Chat(nick);

	public telaChat() {
		inicializaServidor();
		inicializaTela();
	}

	public void inicializaServidor() {
		this.nick = JOptionPane.showInputDialog("Digite seu nome:");
		chat = new Chat(nick);
		servidorMensagem.iniciarServidorMensagem();
		mensagem.setRemetente(nick);

		chat.lookupDNS();

		try {
			chat.autentica();
		} catch (UnknownHostException | RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.listaUsuarios = chat.obterListaUsuariosOnline();
		carregaLista(listaUsuarios);
	}

	public void inicializaTela() {

		defineComponentes();

		add(containerPrincipal);
		setVisible(true);
		selecionarLista();
	}

	private void defineComponentes() {
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("CHAT");
		containerPrincipal = new JPanel();
		containerPrincipal.setBackground(Color.black);
		containerPrincipal.setLayout(null);

		txtChatTela = new JTextArea();
		txtChatTela.setBounds(10, 60, 590, 370);
		txtChatTela.setEditable(false);
		scrlChat = new JScrollPane(txtChatTela);
		scrlChat.setBounds(10, 60, 590, 370);
		containerPrincipal.add(scrlChat);

		txtDestinatario = new JTextField();
		txtDestinatario.setBounds(10, 30, 590, 20);
//		txtDestinatario.setEditable(false);
		containerPrincipal.add(txtDestinatario);

		txtChat = new JTextArea();
		txtChat.setBounds(10, 450, 590, 70);
		// txtChat.setBounds(x, y, width, height);
		txtChat.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					enviaMensagem();
				}
			}
		});
		containerPrincipal.add(txtChat);

		btnEnviar = new JButton("ENVIAR");
		btnEnviar.setBounds(640, 450, 110, 30);
		containerPrincipal.add(btnEnviar);
		btnEnviar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				enviaMensagem();
			}
		});

		btnLogout = new JButton("LOGOUT");
		btnLogout.setBounds(640, 490, 110, 30);
		btnLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});
		containerPrincipal.add(btnLogout);

		ModeloTabelaUsuarios modeloTabela = new ModeloTabelaUsuarios(listaUsuarios);
		tblUsuarios = new JTable(modeloTabela);

		scrlUsuarios = new JScrollPane(tblUsuarios);
		scrlUsuarios.setBounds(620, 30, 150, 400);
		containerPrincipal.add(scrlUsuarios);
		carregaLista(listaUsuarios);
		selecionarLista();
	}

	private void enviaMensagem() {
		if (txtDestinatario.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o destinatário");
		} else if(txtChat.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "A mensagem está vazia");
		} else {
			
//			if (!txtChat.getText().isEmpty()) {
			
			carregaLista(listaUsuarios);
			String nickAmigo = txtDestinatario.getText().trim();
			String ipAmigo = "";

			try {
				ipAmigo = chat.obterIpAmigo(nickAmigo);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}

			chat.lookupChat(ipAmigo);

        	mensagem.setConteudo(txtChat.getText());
        	try {
				chat.enviaMensagem(mensagem);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			txtChatTela.setText(txtChatTela.getText() + "Você disse: " + txtChat.getText() + "\n");
			txtChat.setText("");
		}
	}

	public void selecionarLista() {
		tblUsuarios.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (tblUsuarios.getSelectedRow() != -1) {
						txtDestinatario.setText(listaUsuarios.get(tblUsuarios.getSelectedRow()));
					}
				}
			}
		});
	}

	public void carregaLista(ArrayList<String> listaUsuarios) {
		ModeloTabelaUsuarios modeloTabela = new ModeloTabelaUsuarios(listaUsuarios);
		JTable tabela = new JTable(modeloTabela);
		JScrollPane scrl = new JScrollPane(tabela);
		scrl.setBounds(620, 30, 150, 400);
		this.add(scrl);
	}

	public static void main(String[] args) {
		new telaChat();
	}
}
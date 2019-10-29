package br.edu.ifg.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ModeloTabelaUsuarios extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String cabecalho[] = {"Bate-Papos"};
	//private ArrayList<TabelaUsuarios> listaUsuarios;
	ArrayList<String> listaUsuarios;

	
	public ModeloTabelaUsuarios(ArrayList<String> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return listaUsuarios.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return cabecalho.length;
	}

	@Override
	public String getColumnName(int posicao) {
		return cabecalho[posicao];
	}

	public void addRow(String p) {
		this.listaUsuarios.add(p);
		fireTableDataChanged();
	}

	public void removeRow(int row) {
		this.listaUsuarios.remove(row);
		this.fireTableRowsDeleted(row, row);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return listaUsuarios.get(rowIndex);
		}
		return null;
	}

	public void setValueAt(Object valor, int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			listaUsuarios.get(rowIndex);
			break;
		}
		fireTableDataChanged();
	}
}
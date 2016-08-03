package br.gov.sc.cbm.e193comunitario.model;

import java.util.ArrayList;
import java.util.List;

public class TipoEmergencia {
	
	private int id;
	private String nome;
	private boolean selected = true;
	
	public TipoEmergencia(){}
	
	public TipoEmergencia(int id, String nome, boolean selected){
		this.id = id;
		this.nome = nome;
		this.setSelected(selected);
	}
	
	public List<Integer> getIdsIfSelected(List<TipoEmergencia> list) {
		List<Integer> ids = null;
		if (list != null) {
			ids = new ArrayList<Integer>();
			TipoEmergencia aux = null;
			for (int i = 0; i < list.size(); i++) {
				aux = list.get(i);
				if (aux.selected) {
					ids.add(aux.getId());
				}
				aux = null;
			}
		}
		return ids;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}

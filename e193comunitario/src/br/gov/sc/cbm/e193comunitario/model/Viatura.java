package br.gov.sc.cbm.e193comunitario.model;

public class Viatura {
	private int id;
	private String nome;

	public Viatura(){}
	
	public Viatura(int id, String nome){
		this.id = id;
		this.nome = nome;
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
	
}

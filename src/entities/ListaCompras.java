package entities;


public class ListaCompras {
	// Propriedades
	private int id;
	private String nome;
	private String dataCriacao;
	private String dataModificacao;
	
	// Setters e Getters
	public void setDataCriacao(String dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public void setDataModificacao(String dataModificacao) {
		this.dataModificacao = dataModificacao;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDataCriacao() {
		return dataCriacao;
	}
	
	public String getDataModificacao() {
		return dataModificacao;
	}
	
	public int getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	// Construtores
	public ListaCompras() {
		this.id = 0;
		this.nome = "";
		this.dataCriacao = "";
		this.dataModificacao = "";
	}
	
	public ListaCompras(int id, String nome, String dataCriacao, String dataModificacao) {
		this.id = id;
		this.nome = nome;
		this.dataCriacao = dataCriacao;
		this.dataModificacao = dataModificacao;
	}
	
	
	
	
}

package entities;

import java.io.Serializable;

public class Produto implements IProduto, Serializable {
	private int id;
	private String nome;
	private Double preco;
	private String dataCriacao;
	private String dataModificacao;
	
	@Override
	public void setDataCriacao(String dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	@Override
	public void setDataModificacao(String dataModificacao) {
		this.dataModificacao = dataModificacao;
	}
	
	@Override
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	@Override
	public String getDataCriacao() {
		return dataCriacao;
	}
	
	@Override
	public String getDataModificacao() {
		return dataModificacao;
	}
	
	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public String getNome() {
		return nome;
	}
	
	@Override
	public Double getPreco() {
		return preco;
	}
	
	public Produto() {
		this.id = 0;
		this.nome = "";
		this.preco = 0.00;
		this.dataCriacao = "";
		this.dataModificacao = "";
	}
	
	public Produto(int id, String nome, Double preco, String dataCriacao, String dataModificacao) {
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.dataCriacao = dataCriacao;
		this.dataModificacao = dataModificacao;
	}
	
	
	
}

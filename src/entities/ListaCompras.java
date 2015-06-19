package entities;

import java.util.ArrayList;


public class ListaCompras implements IListaCompras {
	private int id;
	private String nome;
	private String dataCriacao;
	private String dataModificacao;
	private ArrayList<IProduto> produtos;
	
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

	@Override
	public ArrayList<IProduto> getProdutos() {
		return this.produtos;
	}

	@Override
	public void setProdutos(ArrayList<IProduto> produtos) {
		this.produtos = produtos;		
	}

	@Override
	public void addProduto(IProduto produto) {
		this.produtos.add(produto);		
	}

	@Override
	public void removeProduto(IProduto produto) {
		this.produtos.remove(produto);
	}
	
	
	
	
}

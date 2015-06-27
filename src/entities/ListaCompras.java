package entities;

import java.util.ArrayList;


public class ListaCompras implements IListaCompras {
	private int id;
	private String nome;
	private String dataCriacao;
	private String dataModificacao;
	private ArrayList<IListaComprasProduto> produtos;
	
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
		this.produtos = new ArrayList<IListaComprasProduto>();
	}
	
	public ListaCompras(int id, String nome, String dataCriacao, String dataModificacao) {
		this.id = id;
		this.nome = nome;
		this.dataCriacao = dataCriacao;
		this.dataModificacao = dataModificacao;
		this.produtos = new ArrayList<IListaComprasProduto>();
	}

	@Override
	public ArrayList<IListaComprasProduto> getProdutos() {
		return this.produtos;
	}

	@Override
	public void setProdutos(ArrayList<IListaComprasProduto> produtos) {
		this.produtos = produtos;		
	}

	@Override
	public void addProduto(IListaComprasProduto produto) {
		this.produtos.add(produto);		
	}

	@Override
	public void removeProduto(IListaComprasProduto produto) {
		this.produtos.remove(produto);
	}
	
	@Override
	public String toString() {
		return this.nome;
	}
	
	
	
}

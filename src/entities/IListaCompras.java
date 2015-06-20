package entities;

import java.util.ArrayList;

public interface IListaCompras {

	// Setters e Getters
	public abstract void setDataCriacao(String dataCriacao);

	public abstract void setDataModificacao(String dataModificacao);

	public abstract void setId(int id);

	public abstract void setNome(String nome);

	public abstract String getDataCriacao();

	public abstract String getDataModificacao();

	public abstract int getId();

	public abstract String getNome();
	
	public abstract ArrayList<IListaComprasProduto> getProdutos();
	
	public abstract void setProdutos(ArrayList<IListaComprasProduto> produtos);
	
	public abstract void addProduto(IListaComprasProduto produto);
	
	public abstract void removeProduto(IListaComprasProduto produto);

}
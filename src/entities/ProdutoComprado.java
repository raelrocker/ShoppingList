package entities;

public class ProdutoComprado {
	private IProduto produto;
	private Double quantidade;
	
	public ProdutoComprado(IProduto produto, Double quantidade) {
		this.produto = produto;
		this.quantidade = quantidade;
	}

	public IProduto getProduto() {
		return produto;
	}

	public void setProduto(IProduto produto) {
		this.produto = produto;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
	
	@Override
	public String toString() {
		return produto.getNome() + " " + quantidade;
	}
	
	
}

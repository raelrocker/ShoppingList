package entities;

public class ListaComprasProduto implements IListaComprasProduto {
	private IProduto produto;
	private double quantidade;
	private String unidade;
	private boolean noCarrinho;
	private String dataCriacao;
	private String dataModificacao;
	private Double preco;
	
	@Override
	public IProduto getProduto() {
		return produto;
	}
	
	@Override
	public void setProduto(IProduto produto) {
		this.produto = produto;
	}
	
	@Override
	public double getQuantidade() {
		return quantidade;
	}
	
	@Override
	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}
	
	@Override
	public String getUnidade() {
		return unidade;
	}
	
	@Override
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	
	@Override
	public boolean isNoCarrinho() {
		return noCarrinho;
	}
	
	@Override
	public void setNoCarrinho(boolean noCarrinho) {
		this.noCarrinho = noCarrinho;
	}
	
	@Override
	public String getDataCriacao() {
		return dataCriacao;
	}
	
	@Override
	public void setDataCriacao(String dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	@Override
	public String getDataModificacao() {
		return dataModificacao;
	}
	
	@Override
	public void setDataModificacao(String dataModificacao) {
		this.dataModificacao = dataModificacao;
	}
	
	@Override
	public void setPreco(Double preco) {
		this.preco = preco;
	}

	@Override
	public Double getPreco() {
		return this.preco;
	}
	
	public ListaComprasProduto(IProduto produto, double quantidade,
			String unidade, boolean noCarrinho, String dataCriacao,
			String dataModificacao, Double preco) {
		super();
		this.produto = produto;
		this.quantidade = quantidade;
		this.unidade = unidade;
		this.noCarrinho = noCarrinho;
		this.dataCriacao = dataCriacao;
		this.dataModificacao = dataModificacao;
		this.preco = preco;
	}

	public ListaComprasProduto() {
		this.produto = null;
		this.quantidade = 0;
		this.unidade = "";
		this.noCarrinho = false;
		this.dataCriacao = "";
		this.dataModificacao = "";
		this.preco = 0.0;
	}

	
	
	
	
}

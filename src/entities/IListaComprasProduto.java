package entities;

public interface IListaComprasProduto {

	public IProduto getProduto();

	public void setProduto(IProduto produto);

	public double getQuantidade();

	public void setQuantidade(double quantidade);

	public String getUnidade();

	public void setUnidade(String unidade);

	public boolean isNoCarrinho();

	public void setNoCarrinho(boolean noCarrinho);

	public String getDataCriacao();

	public void setDataCriacao(String dataCriacao);

	public String getDataModificacao();

	public void setDataModificacao(String dataModificacao);
	
	public void setPreco(Double preco);
	
	public Double getPreco();

}
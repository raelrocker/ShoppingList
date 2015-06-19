package entities;

public interface IProduto {

	public void setDataCriacao(String dataCriacao);

	public void setDataModificacao(String dataModificacao);

	public void setId(int id);

	public void setNome(String nome);

	public void setPreco(Double preco);

	public String getDataCriacao();

	public String getDataModificacao();

	public int getId();

	public String getNome();

	public Double getPreco();

}
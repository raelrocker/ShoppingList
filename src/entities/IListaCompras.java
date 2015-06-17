package entities;

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

}
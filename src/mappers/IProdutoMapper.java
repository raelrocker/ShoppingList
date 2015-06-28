package mappers;

import java.util.ArrayList;

import entities.IProduto;

public interface IProdutoMapper {

	public Boolean save(IProduto produto);

	public Boolean update(IProduto produto);

	public Boolean delete(int id);

	public IProduto find(int id);

	public ArrayList<IProduto> findAll();
	
	public Boolean isProdutoInListaCompras(int id);

}
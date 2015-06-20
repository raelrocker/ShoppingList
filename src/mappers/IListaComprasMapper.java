package mappers;

import java.util.ArrayList;

import entities.IListaCompras;
import entities.IListaComprasProduto;

public interface IListaComprasMapper {

	public abstract Boolean save(IListaCompras listaCompras);

	public abstract Boolean update(IListaCompras listaCompras);

	public abstract Boolean delete(int id);

	public abstract IListaCompras find(int id);

	public abstract ArrayList<IListaCompras> findAll();
	
	public abstract Boolean saveProduto(IListaCompras listaCompras, IListaComprasProduto listaComprasProduto);
	
	public abstract Boolean updateProduto(IListaCompras listaCompras, IListaComprasProduto listaComprasProduto);
	
	public abstract Boolean deleteProduto(IListaCompras listaCompra, int idProduto);
	
	public abstract IListaComprasProduto findProduto(IListaCompras listaCompra, int idProduto);
	
	public abstract ArrayList<IListaComprasProduto> findAllProdutos(IListaCompras listaCompra);

}
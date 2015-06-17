package mappers;

import java.util.ArrayList;

import entities.IListaCompras;

public interface IListaComprasMapper {

	public abstract Boolean save(IListaCompras listaCompras);

	public abstract Boolean update(IListaCompras listaCompras);

	public abstract Boolean delete(int id);

	public abstract IListaCompras find(int id);

	public abstract ArrayList<IListaCompras> findAll();

}
package controllers;

import java.util.ArrayList;

import mappers.IListaComprasMapper;
import entities.IListaCompras;

public class ListaComprasController {
	
	private IListaComprasMapper listaComprasMapper;
	
	public ListaComprasController(IListaComprasMapper listaComprasMapper) {
		this.listaComprasMapper = listaComprasMapper;
	}
	
	public Boolean saveListaCompras(IListaCompras listaCompras) {
		return this.listaComprasMapper.save(listaCompras);
	}
	
	public Boolean updateListaCompras(IListaCompras listaCompras) {
		return this.listaComprasMapper.update(listaCompras);
	}
	
	public Boolean deleteListaCompras(int id) {
		return this.listaComprasMapper.delete(id);
	}
	
	public IListaCompras findListaCompras(int id) {
		return this.listaComprasMapper.find(id);
	}
	
	public ArrayList<IListaCompras> findAllListaCompras() {
		return this.listaComprasMapper.findAll();
	}
}

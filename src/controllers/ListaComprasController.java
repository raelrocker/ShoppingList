package controllers;

import java.util.ArrayList;

import mappers.ListaComprasMapper;
import entities.ListaCompras;

public class ListaComprasController {
	
	private ListaComprasMapper mapper;
	
	public ListaComprasController(ListaComprasMapper mapper) {
		this.mapper = mapper;
	}
	
	public Boolean saveListaCompras(ListaCompras listaCompras) {
		return this.mapper.save(listaCompras);
	}
	
	public Boolean updateListaCompras(ListaCompras listaCompras) {
		return this.mapper.update(listaCompras);
	}
	
	public Boolean deleteListaCompras(int id) {
		return this.mapper.delete(id);
	}
	
	public ListaCompras findListaCompras(int id) {
		return this.mapper.find(id);
	}
	
	public ArrayList<ListaCompras> findAllListaCompras() {
		return this.mapper.findAll();
	}
}

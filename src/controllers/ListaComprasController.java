package controllers;

import java.util.ArrayList;

import android.R.integer;
import mappers.IListaComprasMapper;
import entities.IListaCompras;
import entities.IListaComprasProduto;

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
	
	public Boolean saveProduto(IListaCompras listaCompras, IListaComprasProduto produto) {
		return this.listaComprasMapper.saveProduto(listaCompras, produto);
	}
	
	public Boolean updateProduto(IListaCompras listaCompras, IListaComprasProduto produto) {
		return this.listaComprasMapper.updateQuantidadeProduto(listaCompras, produto);
	}
	
	public Boolean deleteProduto(IListaCompras listaCompras, int id) {
		return this.listaComprasMapper.deleteProduto(listaCompras, id);
	}
	
	public IListaComprasProduto findProduto(IListaCompras listaCompras, int id) {
		return this.listaComprasMapper.findProduto(listaCompras, id);
	}
	
	public ArrayList<IListaComprasProduto> findAllProdutos(IListaCompras listaCompras) {
		ArrayList<IListaComprasProduto> produtos = this.listaComprasMapper.findAllProdutos(listaCompras);
		listaCompras.setProdutos(produtos);
		return produtos;
	}
	
	public Boolean validarListaCompras(IListaCompras lista) throws Exception {
		if (lista.getNome().isEmpty()) {
			throw new Exception("Informe o nome da lista de compras");
		}
		return true;
	}
	
	public int totalDeProdutos(IListaCompras lista) {
		int itens = 0;
		for (IListaComprasProduto item : lista.getProdutos()) {
			itens += (int)item.getQuantidade();
		}
		return itens;
	}
	
	public Double valorTotalDeProdutos(IListaCompras lista) {
		Double total = 0.0;
		for (IListaComprasProduto item : lista.getProdutos()) {
			total += item.getQuantidade() * item.getProduto().getPreco();
		}
		return total;
	}
}

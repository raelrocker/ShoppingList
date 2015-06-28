package controllers;

import java.util.ArrayList;

import mappers.IProdutoMapper;
import entities.IProduto;

public class ProdutoController {
	private IProdutoMapper produtoMapper;
	
	public ProdutoController(IProdutoMapper produtoMapper) {
		this.produtoMapper = produtoMapper;
	}
	
	public Boolean saveProduto(IProduto produto) {
		return this.produtoMapper.save(produto);
	}
	
	public Boolean updateProduto(IProduto produto) {
		return this.produtoMapper.update(produto);
	}
	
	public Boolean deleteProduto(int id) throws Exception {
		if (!this.produtoMapper.isProdutoInListaCompras(id)) {
			return this.produtoMapper.delete(id);
		}
		throw new Exception();
	}
	
	public IProduto findProduto(int id) {
		return this.produtoMapper.find(id);
	}
	
	public ArrayList<IProduto> findAllProdutos() {
		return this.produtoMapper.findAll();
	}
	
	public Boolean validarProduto(IProduto produto) throws Exception {
		if (produto.getNome().isEmpty()) {
			throw new Exception("Informe o nome do produto");
		}
		return true;
	}
}

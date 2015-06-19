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
	
	public Boolean deleteProduto(int id) {
		return this.produtoMapper.delete(id);
	}
	
	public IProduto findProduto(int id) {
		return this.produtoMapper.find(id);
	}
	
	public ArrayList<IProduto> findAllProdutos() {
		return this.produtoMapper.findAll();
	}
}

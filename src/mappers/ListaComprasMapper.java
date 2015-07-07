package mappers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import entities.IListaCompras;
import entities.IListaComprasProduto;
import entities.ListaCompras;
import entities.ListaComprasProduto;

public class ListaComprasMapper implements IListaComprasMapper {
	
	private SQLiteDatabase banco;
	private String nomeTabela = "lista_compras";
	private String nomeTabelaProdutos = "lista_compras_produtos";
	
	public ListaComprasMapper(SQLiteDatabase banco) {
		this.banco = banco;
	}
	
	@Override
	public Boolean save(IListaCompras listaCompras) {
		
		ContentValues contentValues = new ContentValues();
		contentValues.put("nome", listaCompras.getNome());
		contentValues.put("data_criacao", listaCompras.getDataCriacao());
		contentValues.put("data_modificacao", listaCompras.getDataModificacao());
		
		int id = (int)this.banco.insert(this.nomeTabela, null, contentValues);
		if (id > 0) {
			listaCompras.setId(id);
			
			return true;
		}
		
		return false;			
	}
	
	@Override
	public Boolean update(IListaCompras listaCompras) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("nome", listaCompras.getNome());
		contentValues.put("data_criacao", listaCompras.getDataCriacao());
		contentValues.put("data_modificacao", listaCompras.getDataModificacao());
		return this.banco.update(this.nomeTabela, contentValues, "id = ?", new String[]{String.valueOf(listaCompras.getId())}) > 0;
	}
	
	@Override
	public Boolean delete(int id) {
		return this.banco.delete(this.nomeTabela, "id = ?", new String[]{String.valueOf(id)}) > 0;
	}
	
	@Override
	public IListaCompras find(int id) {
		IListaCompras lista = null;
		Cursor cursor = banco.query(this.nomeTabela,
                new String[]{"id", "nome", "data_criacao", "data_modificacao"},
                "id = ?",
                new String[]{ String.valueOf(id) },
                null,
                null,
                null);
		if (cursor != null) {
            cursor.moveToFirst();
			lista = new ListaCompras(cursor.getInt(cursor.getColumnIndex("id")),
												  cursor.getString(cursor.getColumnIndex("nome")),
												  cursor.getString(cursor.getColumnIndex("data_criacao")),
												  cursor.getString(cursor.getColumnIndex("data_modificacao")));
		}
		lista.setProdutos(this.findAllProdutos(lista));
		return lista;
		
	}
	
	@Override
	public ArrayList<IListaCompras> findAll() {
		ArrayList<IListaCompras> listas = new ArrayList<IListaCompras>();
		Cursor cursor = banco.query(this.nomeTabela,
					                new String[]{"id", "nome", "data_criacao", "data_modificacao"},
					                null,
					                null,
					                null,
					                null,
					                null);
		

        while(cursor.moveToNext()){
        	try {
	        	ListaCompras lista = new ListaCompras(cursor.getInt(cursor.getColumnIndex("id")),
	        										  cursor.getString(cursor.getColumnIndex("nome")),
	        										  cursor.getString(cursor.getColumnIndex("data_criacao")),
	        										  cursor.getString(cursor.getColumnIndex("data_modificacao")));
	        	listas.add(lista);
        	} catch (Exception ex) {
        		Log.i("ERRO", ex.getMessage());
        	}
        }
		
		return listas;
	}
	
	@Override
	public Boolean saveProduto(IListaCompras listaCompras, IListaComprasProduto listaComprasProduto) {
		IListaComprasProduto produtoNaLista = findProduto(listaCompras, listaComprasProduto.getProduto().getId());
		if (produtoNaLista == null) {
		
			ContentValues contentValues = new ContentValues();
			contentValues.put("lista_compras_id", listaCompras.getId());
			contentValues.put("produto_id", listaComprasProduto.getProduto().getId());
			contentValues.put("quantidade", listaComprasProduto.getQuantidade());
			contentValues.put("unidade", listaComprasProduto.getUnidade());
			contentValues.put("no_carrinho", listaComprasProduto.isNoCarrinho());
			contentValues.put("data_criacao", listaCompras.getDataCriacao());
			contentValues.put("data_modificacao", listaCompras.getDataModificacao());
			contentValues.put("preco", listaComprasProduto.getPreco());
			
			int id = (int)this.banco.insert(this.nomeTabelaProdutos, null, contentValues);
			if (id > 0) {
				return true;
			}
		} else {
			Double qnt = produtoNaLista.getQuantidade() + listaComprasProduto.getQuantidade();
			Double preco = listaComprasProduto.getPreco();
			produtoNaLista.setQuantidade(qnt);
			produtoNaLista.setPreco(preco);
			produtoNaLista.setDataModificacao(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
			return this.updateProduto(listaCompras, produtoNaLista);
		}
		
		return false;
			
	}
	
	@Override
	public Boolean updateProduto(IListaCompras listaCompras, IListaComprasProduto listaComprasProduto) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("lista_compras_id", listaCompras.getId());
		contentValues.put("produto_id", listaComprasProduto.getProduto().getId());
		contentValues.put("quantidade", listaComprasProduto.getQuantidade());
		contentValues.put("unidade", listaComprasProduto.getUnidade());
		contentValues.put("no_carrinho", listaComprasProduto.isNoCarrinho());
		contentValues.put("data_criacao", listaCompras.getDataCriacao());
		contentValues.put("data_modificacao", listaCompras.getDataModificacao());
		contentValues.put("preco", listaComprasProduto.getPreco());
		
		int id = (int)this.banco.update(this.nomeTabelaProdutos, contentValues, "lista_compras_id = ? AND produto_id = ?", new String[]{String.valueOf(listaCompras.getId()), String.valueOf(listaComprasProduto.getProduto().getId())});
		if (id > 0) {
						
			return true;
		}
		
		return false;			
	}
	
	@Override
	public Boolean updateQuantidadeProduto(IListaCompras listaCompras, IListaComprasProduto listaComprasProduto) {
		String update = "UPDATE " + this.nomeTabelaProdutos + " SET quantidade = " + listaComprasProduto.getQuantidade();
		
		int rowsAffect = 0;
		try {
			this.banco.execSQL(update);
		} catch (Exception ex) {
			Log.i("ERRO", ex.getMessage());
		}
		return true;
	}
	
	@Override
	public Boolean deleteProduto(IListaCompras listaCompra, int idProduto) {
		try {
			return this.banco.delete(this.nomeTabelaProdutos, "lista_compras_id = ? AND produto_id = ?", new String[]{String.valueOf(listaCompra.getId()), String.valueOf(idProduto)}) > 0;
		} catch (Exception ex) {
			Log.i("EROROROROR", ex.getMessage());
		}
		return false;
	}
	
	@Override
	public IListaComprasProduto findProduto(IListaCompras listaCompra, int idProduto) {
		IListaComprasProduto lista = null;
		Cursor cursor = banco.query(this.nomeTabelaProdutos,
                new String[]{"produto_id", "quantidade", "unidade", "no_carrinho", "data_criacao", "data_modificacao", "preco"},
                "lista_compras_id = ? AND produto_id = ?",
                new String[]{ String.valueOf(listaCompra.getId()), String.valueOf(idProduto)},
                null,
                null,
                null);
		if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
			lista = new ListaComprasProduto(new ProdutoMapper(this.banco).find(cursor.getInt(cursor.getColumnIndex("produto_id"))),
											cursor.getInt(cursor.getColumnIndex("quantidade")),
											cursor.getString(cursor.getColumnIndex("unidade")),
											cursor.getInt(cursor.getColumnIndex("no_carrinho")) == 1 ? true : false,
											cursor.getString(cursor.getColumnIndex("data_criacao")),
    										cursor.getString(cursor.getColumnIndex("data_modificacao")), 
    										cursor.getDouble(cursor.getColumnIndex("preco")));
		}
		return lista;
		
	}
	
	@Override
	public ArrayList<IListaComprasProduto> findAllProdutos(IListaCompras listaCompra) {
		ArrayList<IListaComprasProduto> listas = new ArrayList<IListaComprasProduto>();
		Cursor cursor = banco.query(this.nomeTabelaProdutos,
					                new String[]{"produto_id", "quantidade", "unidade", "no_carrinho", "data_criacao", "data_modificacao", "preco"},
					                "lista_compras_id = ?",
					                new String[]{ String.valueOf(listaCompra.getId())},
					                null,
					                null,
					                null);
		

        while(cursor.moveToNext()){
        	try {
	        	IListaComprasProduto lista = new ListaComprasProduto(new ProdutoMapper(this.banco).find(cursor.getInt(cursor.getColumnIndex("produto_id"))),
																	cursor.getInt(cursor.getColumnIndex("quantidade")),
																	cursor.getString(cursor.getColumnIndex("unidade")),
																	cursor.getInt(cursor.getColumnIndex("no_carrinho")) == 1 ? true : false,
																	cursor.getString(cursor.getColumnIndex("data_criacao")),
																	cursor.getString(cursor.getColumnIndex("data_modificacao")),
																	cursor.getDouble(cursor.getColumnIndex("preco")));
	        	listas.add(lista);
        	} catch (Exception ex) {
        		Log.i("ERRO", ex.getMessage());
        	}
        }
		
		return listas;
	}
	
	
}

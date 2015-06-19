package mappers;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import entities.IProduto;
import entities.Produto;

public class ProdutoMapper implements IProdutoMapper {
	private SQLiteDatabase banco;
	private String nomeTabela = "produtos";
	
	public ProdutoMapper(SQLiteDatabase banco) {
		this.banco = banco;
	}
	
	@Override
	public Boolean save(IProduto produto) {
		
		ContentValues contentValues = new ContentValues();
		contentValues.put("nome", produto.getNome());
		contentValues.put("preco", produto.getPreco());
		contentValues.put("data_criacao", produto.getDataCriacao());
		contentValues.put("data_modificacao", produto.getDataModificacao());
		
		int id = (int) this.banco.insert(this.nomeTabela, null, contentValues);
		if (id > 0) {
			produto.setId(id);
			return true;
		}
		
		return false;
			
	}
	
	@Override
	public Boolean update(IProduto produto) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("nome", produto.getNome());
		contentValues.put("preco", produto.getPreco());
		contentValues.put("data_criacao", produto.getDataCriacao());
		contentValues.put("data_modificacao", produto.getDataModificacao());
		return this.banco.update(this.nomeTabela, contentValues, "id = ?", new String[]{String.valueOf(produto.getId())}) > 0;
	}
	
	@Override
	public Boolean delete(int id) {
		return this.banco.delete(this.nomeTabela, "id = ?", new String[]{String.valueOf(id)}) > 0;
	}
	
	@Override
	public IProduto find(int id) {
		IProduto produto = null;
		Cursor cursor = banco.query(this.nomeTabela,
                new String[]{"id", "nome", "preco", "data_criacao", "data_modificacao"},
                "id = ?",
                new String[]{ String.valueOf(id) },
                null,
                null,
                null);
		if (cursor != null) {
            cursor.moveToFirst();
			produto = new Produto(cursor.getInt(cursor.getColumnIndex("id")),
									cursor.getString(cursor.getColumnIndex("nome")),
									cursor.getDouble(cursor.getColumnIndex("preco")),
									cursor.getString(cursor.getColumnIndex("data_criacao")),
									cursor.getString(cursor.getColumnIndex("data_modificacao")));
		}
		return produto;
		
	}
	
	@Override
	public ArrayList<IProduto> findAll() {
		ArrayList<IProduto> produtos = new ArrayList<IProduto>();
		Cursor cursor = banco.query(this.nomeTabela,
					                new String[]{"id", "nome", "preco", "data_criacao", "data_modificacao"},
					                null,
					                null,
					                null,
					                null,
					                null);
		

        while(cursor.moveToNext()){
        	try {
        		Produto produto = new Produto(cursor.getInt(cursor.getColumnIndex("id")),
										cursor.getString(cursor.getColumnIndex("nome")),
										cursor.getDouble(cursor.getColumnIndex("preco")),
										cursor.getString(cursor.getColumnIndex("data_criacao")),
										cursor.getString(cursor.getColumnIndex("data_modificacao")));
	        	produtos.add(produto);
        	} catch (Exception ex) {
        		Log.i("ERRO", ex.getMessage());
        	}
        }
		
		return produtos;
	}

}

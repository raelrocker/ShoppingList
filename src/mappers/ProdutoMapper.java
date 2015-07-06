package mappers;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import entities.IProduto;
import entities.Produto;
import entities.ProdutoComprado;

public class ProdutoMapper implements IProdutoMapper {
	private SQLiteDatabase banco;
	private String nomeTabela = "produtos";
	
	public ProdutoMapper(SQLiteDatabase banco) {
		this.banco = banco;
	}
	
	@Override
	public Boolean save(IProduto produto) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String data = sdf.format(new Date());
		ContentValues contentValues = new ContentValues();
		contentValues.put("nome", produto.getNome());
		contentValues.put("preco", produto.getPreco());
		contentValues.put("data_criacao", data);
		contentValues.put("data_modificacao", data);
		
		int id = (int) this.banco.insert(this.nomeTabela, null, contentValues);
		if (id > 0) {
			produto.setId(id);
			produto.setDataCriacao(data);
			produto.setDataModificacao(data);
			return true;
		}
		
		return false;
			
	}
	
	@Override
	public Boolean update(IProduto produto) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String data = sdf.format(new Date());
		ContentValues contentValues = new ContentValues();
		contentValues.put("nome", produto.getNome());
		contentValues.put("preco", produto.getPreco());
		contentValues.put("data_criacao", produto.getDataCriacao());
		contentValues.put("data_modificacao", data);
		if (this.banco.update(this.nomeTabela, contentValues, "id = ?", new String[]{String.valueOf(produto.getId())}) > 0) {
			produto.setDataModificacao(data);
			return true;
		} else {
			return false;
		}
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
	
	@Override
	public Boolean isProdutoInListaCompras(int id) {
		int quant = 0;
		Cursor cursor = banco.rawQuery("SELECT COUNT(*) quant FROM lista_compras_produtos WHERE produto_id = ?", new String[]{String.valueOf(id)});
		
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			quant = cursor.getInt(cursor.getColumnIndex("quant"));
		}
		
		return quant > 0;
	}
	
	
	@Override
	public ArrayList<ProdutoComprado> findMaisComprados() {		
		Cursor cursor = banco.rawQuery("SELECT produto_id, SUM(quantidade) qnt FROM lista_compras_produtos "
										+ "GROUP BY produto_id "
										+ "ORDER BY qnt DESC ", null);
		ArrayList<ProdutoComprado> array = new ArrayList<ProdutoComprado>();
        while(cursor.moveToNext()){
        	try {
        		IProduto produto = find(cursor.getInt(cursor.getColumnIndex("produto_id")));
        		Double qnt = cursor.getDouble(cursor.getColumnIndex("qnt"));
        		ProdutoComprado pc = new ProdutoComprado(produto, qnt);
        		array.add(pc);
        	} catch (Exception ex) {
        		Log.i("ERRO", ex.getMessage());
        	}
        }
		
		return array;
	}

}

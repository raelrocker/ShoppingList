package mappers;

import java.util.ArrayList;

import bancoDeDados.IBancoDeDados;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import entities.IListaCompras;
import entities.ListaCompras;

public class ListaComprasMapper implements IListaComprasMapper {
	
	private SQLiteDatabase banco;
	private String nomeTabela = "lista_compras";
	
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
	
	
}

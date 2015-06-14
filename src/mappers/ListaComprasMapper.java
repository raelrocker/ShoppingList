package mappers;

import java.util.ArrayList;

import bancoDeDados.BancoDeDados;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import entities.ListaCompras;

public class ListaComprasMapper {
	
	private SQLiteDatabase banco;
	
	public ListaComprasMapper(SQLiteDatabase banco) {
		this.banco = banco;
	}
	
	public Boolean save(ListaCompras listaCompras) {
		
		ContentValues contentValues = new ContentValues();
		contentValues.put("nome", listaCompras.getNome());
		contentValues.put("data_criacao", listaCompras.getDataCriacao());
		contentValues.put("data_modificacao", listaCompras.getDataModificacao());
		
		int id = (int)this.banco.insert(BancoDeDados.TABLE_LISTA_COMPRAS, null, contentValues);
		if (id > 0) {
			listaCompras.setId(id);
			return true;
		}
		
		return false;
			
	}
	
	public Boolean update(ListaCompras listaCompras) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("nome", listaCompras.getNome());
		contentValues.put("data_criacao", listaCompras.getDataCriacao());
		contentValues.put("data_modificacao", listaCompras.getDataModificacao());
		return this.banco.update(BancoDeDados.TABLE_LISTA_COMPRAS, contentValues, "id = ?", new String[]{String.valueOf(listaCompras.getId())}) > 0;
	}
	
	public Boolean delete(int id) {
		return this.banco.delete(BancoDeDados.TABLE_LISTA_COMPRAS, "id = ?", new String[]{String.valueOf(id)}) > 0;
	}
	
	public ListaCompras find(int id) {
		ListaCompras lista = null;
		Cursor cursor = banco.query(BancoDeDados.TABLE_LISTA_COMPRAS,
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
	
	public ArrayList<ListaCompras> findAll() {
		ArrayList<ListaCompras> listas = new ArrayList<ListaCompras>();
		Cursor cursor = banco.query(BancoDeDados.TABLE_LISTA_COMPRAS,
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

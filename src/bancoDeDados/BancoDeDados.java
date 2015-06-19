package bancoDeDados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class BancoDeDados implements IBancoDeDados {
	
	private static SQLiteDatabase banco = null;	
	private static final String DB_NAME = "ShoppingList.db";
	private static final String DB_CREATE_TABLE_LISTA_COMPRAS = "CREATE TABLE IF NOT EXISTS  lista_compras (id integer primary key autoincrement, nome text not null, data_criacao text not null, data_modificacao text not null);";
	private static final String DB_CREATE_TABLE_PRODUTOS = "CREATE TABLE IF NOT EXISTS  produtos (id integer primary key autoincrement, nome text not null, preco double not null, data_criacao text not null, data_modificacao text not null);";
	
	public BancoDeDados() {}
	
	@Override
	public SQLiteDatabase getBancoDeDados(Context context) {
		
		if (BancoDeDados.banco == null) {
			BancoDeDados.banco = this.SetBancoDeDados(context);
		}		
		return BancoDeDados.banco;
	}	
	
	private SQLiteDatabase SetBancoDeDados(Context context) {
		SQLiteDatabase bd = context.openOrCreateDatabase(BancoDeDados.DB_NAME, Context.MODE_PRIVATE, null);
		bd.execSQL(BancoDeDados.DB_CREATE_TABLE_LISTA_COMPRAS);
		bd.execSQL(BancoDeDados.DB_CREATE_TABLE_PRODUTOS);
		return bd;
	}
	
}

package bancoDeDados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDeDados {
	
	private static SQLiteDatabase banco = null;
	private SQLiteOpenHelper helper;
	
	private static final String DB_NAME = "ShoppingList.db"; 
	public static final String TABLE_LISTA_COMPRAS = "lista_compras"; 
	private static final String DB_CREATE_QUERY = "CREATE TABLE IF NOT EXISTS  " + BancoDeDados.TABLE_LISTA_COMPRAS + " (id integer primary key autoincrement, nome text not null, data_criacao text not null, data_modificacao text not null);";


	public BancoDeDados() {}
	
	public SQLiteDatabase getBancoDeDados(Context context) {
		
		if (BancoDeDados.banco == null) {
			BancoDeDados.banco = this.SetBancoDeDados(context);
		}		
		return BancoDeDados.banco;
	}	
	
	private SQLiteDatabase SetBancoDeDados(Context context) {
		SQLiteDatabase bd = context.openOrCreateDatabase(BancoDeDados.DB_NAME, context.MODE_PRIVATE, null);
		bd.execSQL(BancoDeDados.DB_CREATE_QUERY);
		return bd;
	}
	
}

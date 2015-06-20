package bancoDeDados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class BancoDeDados implements IBancoDeDados {
	
	private static SQLiteDatabase banco = null;	
	private static final String DB_NAME = "ShoppingList.db";
	private static final String DB_CREATE_TABLE_LISTA_COMPRAS = "CREATE TABLE IF NOT EXISTS  lista_compras (id integer primary key autoincrement, nome text not null, data_criacao text not null, data_modificacao text not null);";
	private static final String DB_CREATE_TABLE_PRODUTOS = "CREATE TABLE IF NOT EXISTS  produtos (id integer primary key autoincrement, nome text not null, preco double not null, data_criacao text not null, data_modificacao text not null);";
	private static final String DB_CREATE_TABLE_LISTA_COMPRAS_PRODUTOS = "CREATE TABLE IF NOT EXISTS  lista_compras_produtos (lista_compras_id integer not null, produto_id integer not null, quantidade double, unidade character not null, no_carrinho boolean not null, data_criacao text not null, data_modificacao text not null, PRIMARY KEY(lista_compras_id, produto_id), FOREIGN KEY(lista_compras_id) REFERENCES lista_compras(id), FOREIGN KEY(produto_id) REFERENCES produtos(id));";
	
	public BancoDeDados() {}
	
	@Override
	public SQLiteDatabase getBancoDeDados(Context context) {
		
		if (BancoDeDados.banco == null) {
			BancoDeDados.banco = this.SetBancoDeDados(context);
		}		
		return BancoDeDados.banco;
	}
	
	@Override
	public SQLiteDatabase getBancoDeDados() {		
		return BancoDeDados.banco;
	}	
	
	private SQLiteDatabase SetBancoDeDados(Context context) {
		SQLiteDatabase bd = context.openOrCreateDatabase(BancoDeDados.DB_NAME, Context.MODE_PRIVATE, null);
		bd.execSQL(BancoDeDados.DB_CREATE_TABLE_LISTA_COMPRAS);
		bd.execSQL(BancoDeDados.DB_CREATE_TABLE_PRODUTOS);
		bd.execSQL(BancoDeDados.DB_CREATE_TABLE_LISTA_COMPRAS_PRODUTOS);
		return bd;
	}
	
}

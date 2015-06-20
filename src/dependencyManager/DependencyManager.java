package dependencyManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import bancoDeDados.*;
import mappers.*;
import controllers.*;

public final class DependencyManager {
	
	public static final ListaComprasController GetListaComprasController(Context context) {
		return new ListaComprasController(DependencyManager.GetListaComprasMapper(context));
	}
	
	public static final IListaComprasMapper GetListaComprasMapper(Context context) {
		return new ListaComprasMapper(DependencyManager.GetBancoDeDados(context));
	}
	
	public static final ProdutoController GetProdutoController(Context context) {
		return new ProdutoController(DependencyManager.GetProdutoMapper(context));
	}
	
	public static final IProdutoMapper GetProdutoMapper(Context context) {
		return new ProdutoMapper(DependencyManager.GetBancoDeDados(context));
	}
	
	private static final SQLiteDatabase GetBancoDeDados(Context context) {
		IBancoDeDados bancoDeDados = new BancoDeDados();
		return bancoDeDados.getBancoDeDados(context);		 
	}
}

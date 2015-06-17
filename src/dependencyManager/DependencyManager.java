package dependencyManager;

import bancoDeDados.BancoDeDados;
import bancoDeDados.IBancoDeDados;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import mappers.IListaComprasMapper;
import mappers.ListaComprasMapper;
import controllers.ListaComprasController;

public final class DependencyManager {
	
	public static final ListaComprasController GetListaComprasController(Context context) {
		return new ListaComprasController(DependencyManager.GetListaComprasMapper(context));
	}
	
	private static final IListaComprasMapper GetListaComprasMapper(Context context) {
		return new ListaComprasMapper(DependencyManager.GetBancoDeDados(context));
	}
	
	private static final SQLiteDatabase GetBancoDeDados(Context context) {
		IBancoDeDados bancoDeDados = new BancoDeDados();
		return bancoDeDados.getBancoDeDados(context);
		 
	}
}

package bancoDeDados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public interface IBancoDeDados {

	public abstract SQLiteDatabase getBancoDeDados(Context context);
	
	public abstract SQLiteDatabase getBancoDeDados();

}
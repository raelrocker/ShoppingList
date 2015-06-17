package com.example.shoppinglist;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import controllers.ListaComprasController;
import dependencyManager.DependencyManager;
import entities.IListaCompras;
import entities.ListaCompras;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Boolean r = false;
		setContentView(R.layout.activity_main);
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ListaComprasController l = DependencyManager.GetListaComprasController(this);
		ListaCompras lista = new ListaCompras(0, "LISTA 3", sdf.format(new Date()), sdf.format(new Date()));
		r = l.saveListaCompras(lista);
		lista.setNome("LISTA 3 MODIFICADA");
		r = l.updateListaCompras(lista);
		//r = l.deleteListaCompras(lista.getId());
		ArrayList<IListaCompras> listas = l.findAllListaCompras();
		
		for (IListaCompras lc : listas) {
			Log.i("LISTA", lc.getId() + " " + lc.getNome() + " " + lc.getDataCriacao() + " " + lc.getDataModificacao());
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

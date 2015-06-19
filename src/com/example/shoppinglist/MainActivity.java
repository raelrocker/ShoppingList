package com.example.shoppinglist;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import controllers.ListaComprasController;
import controllers.ProdutoController;
import dependencyManager.DependencyManager;
import entities.IListaCompras;
import entities.IProduto;
import entities.ListaCompras;
import entities.Produto;
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
		ProdutoController l = DependencyManager.GetProdutoController(this);
		
		Produto lista = new Produto(0, "PRODUTO 2", 10.25, sdf.format(new Date()), sdf.format(new Date()));
		r = l.saveProduto(lista);
		lista.setNome("PRODUTO 2 MODIFICADO");
		r = l.updateProduto(lista);
		r = l.deleteProduto(lista.getId());
		ArrayList<IProduto> listas = l.findAllProdutos();
		
		for (IProduto lc : listas) {
			Log.i("PRODUTO", lc.getId() + " " + lc.getNome() + " " + lc.getPreco() + lc.getDataCriacao() + " " + lc.getDataModificacao());
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

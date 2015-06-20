package com.example.shoppinglist;

import java.text.SimpleDateFormat;
import java.util.Date;

import controllers.ListaComprasController;
import controllers.ProdutoController;
import dependencyManager.DependencyManager;
import entities.IListaCompras;
import entities.IListaComprasProduto;
import entities.IProduto;
import entities.ListaComprasProduto;
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
		ProdutoController p = DependencyManager.GetProdutoController(this);
		IListaCompras listaCompras = l.findListaCompras(1);
		IProduto produto = p.findProduto(1);
		IListaComprasProduto item = new ListaComprasProduto(produto, 1, "UN", false, sdf.format(new Date()), sdf.format(new Date()));
		listaCompras.addProduto(item);
		r = l.saveProduto(listaCompras, item);
		
		for (IListaComprasProduto i : l.findAllProdutos(listaCompras)) {
			Log.i("ITEM", i.getProduto().getNome() + " " + i.getQuantidade() + " " + i.getUnidade());
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

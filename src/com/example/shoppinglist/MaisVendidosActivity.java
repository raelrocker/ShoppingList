package com.example.shoppinglist;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import controllers.ProdutoController;
import dependencyManager.DependencyManager;
import entities.IProduto;
import entities.ProdutoComprado;

public class MaisVendidosActivity extends ListActivity {

	private ProdutoController pc;
	private ArrayList<ProdutoComprado> lstProdutosComprados;
	private ArrayAdapter<ProdutoComprado> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		pc = DependencyManager.GetProdutoController(this);
		lstProdutosComprados = pc.findMaisComprados();		
		adapter = new ArrayAdapter<ProdutoComprado>(this, android.R.layout.simple_list_item_1, lstProdutosComprados);
		this.getListView().setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mais_vendidos, menu);
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

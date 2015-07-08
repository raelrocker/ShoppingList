package com.example.shoppinglist;

import java.util.ArrayList;

import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import controllers.ProdutoController;
import dependencyManager.DependencyManager;
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
		this.getListView().setBackgroundColor(Color.argb(255, 232, 232, 232));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.mais_vendidos, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

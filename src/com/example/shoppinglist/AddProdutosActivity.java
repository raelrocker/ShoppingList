package com.example.shoppinglist;

import java.util.ArrayList;

import controllers.ListaComprasController;
import controllers.ProdutoController;
import dependencyManager.DependencyManager;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import entities.IListaComprasProduto;
import entities.IProduto;
import entities.ListaCompras;

public class AddProdutosActivity extends Activity implements OnItemClickListener  {
	
	private EditText txtProcurar;
	private ArrayAdapter<IProduto> adapterProdutos;
	private ArrayList<IProduto> arrayProdutos;
	private ListView lstProdutos;
	private ProdutoController pc;
	private ListaComprasController lc;
	private Intent intent;
	private ListaCompras listaCompras;
	private ListaComprasController listaController;
	private ArrayList<IListaComprasProduto> lsItens;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_produtos);		
		
		
		// LISTA DE COMPRAS
		intent = getIntent();
		lc = DependencyManager.GetListaComprasController(this);
		listaCompras =  (ListaCompras)lc.findListaCompras(intent.getIntExtra("ListaComprasId", 0));
		lsItens = listaCompras.getProdutos();
		
		// LISTA DE PRODUTOS
		pc = DependencyManager.GetProdutoController(this);
		lstProdutos = (ListView)findViewById(R.id.lstListaProdutos);
		arrayProdutos = pc.findAllProdutos();
		adapterProdutos = new ArrayAdapter<IProduto>(this, android.R.layout.simple_list_item_1, arrayProdutos);
		lstProdutos.setAdapter(adapterProdutos);
		
		// PROCURAR
		txtProcurar = (EditText)findViewById(R.id.txtProcurar);
		txtProcurar.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// BUSCAR PRODUTOS
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {}
		});
		CompararListas();
	}
	
	public void CompararListas() {
		for (int i = 0; i < arrayProdutos.size(); i++) {
			for (IListaComprasProduto item : lsItens) {
				if (arrayProdutos.get(i).getId() == item.getProduto().getId()) {
					View v = getViewByPosition(i, lstProdutos);
					v.setBackgroundColor(Color.BLUE);
					
					
				}
					
			}
		}
		
	}
	
	public View getViewByPosition(int pos, ListView listView) {
	    final int firstListItemPosition = listView.getFirstVisiblePosition();
	    final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

	    if (pos < firstListItemPosition || pos > lastListItemPosition ) {
	        return listView.getAdapter().getView(pos, null, listView);
	    } else {
	        final int childIndex = pos - firstListItemPosition;
	        return listView.getChildAt(childIndex);
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_produtos, menu);
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}
}

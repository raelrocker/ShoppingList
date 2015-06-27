package com.example.shoppinglist;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.zip.Inflater;

import controllers.ListaComprasController;
import dependencyManager.DependencyManager;
import entities.IListaCompras;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ListView lstListaCompras;
	ArrayAdapter<IListaCompras> adapter;
	ArrayList<IListaCompras> lstListas;
	public static int MAIN_ACTIVITY = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Boolean r = false;
		setContentView(R.layout.activity_main);
		this.lstListaCompras = (ListView)findViewById(R.id.lstListaCompras);		
		this.FillListaCompras();
		
		//SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		
		
	}
	
	 @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.main, menu);
        return true;
    }
	
	 @Override
    public boolean onOptionsItemSelected(MenuItem item){
		Intent i;
        switch(item.getItemId()){
	        case R.id.menuNovaLista:
	        	// 1. Instantiate an AlertDialog.Builder with its constructor
//	        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//	        	// 2. Chain together various setter methods to set the dialog characteristics
//	        	builder.setMessage("Olá")
//	        	       .setTitle("OPA");
//	        	LayoutInflater inflater = this.getLayoutInflater();
//	        	builder.setView(inflater.inflate(R.layout.activity_produtos, null));
//	        	// 3. Get the AlertDialog from create()
//	        	AlertDialog dialog = builder.create();
//	        	dialog.show();
	            return true;
	        case R.id.menuNovoProduto:
	        	///showDialog(DIALOG_INFO);
	        	i = new Intent(this, ProdutosActivity.class);
	    		startActivityForResult(i, MAIN_ACTIVITY);
	            return true;
	        case R.id.menuVerProdutos:
	            ///showDialog(DIALOG_INFO);
	        	i = new Intent(this, MostarProdutosActivity.class);
	    		startActivityForResult(i, MAIN_ACTIVITY);
	            return true;
	        case R.id.menuSobre:
	            ///showDialog(DIALOG_INFO);
	            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 1) {
			Toast.makeText(this, "PRODUTO SALVO ", Toast.LENGTH_LONG).show();
		}
	}
	
	private void FillListaCompras() {
		ListaComprasController ls = DependencyManager.GetListaComprasController(this);
		this.lstListas = ls.findAllListaCompras();
		this.adapter = new ArrayAdapter<IListaCompras>(this, android.R.layout.simple_list_item_1, lstListas);
		this.lstListaCompras.setAdapter(adapter);
	}
	
}

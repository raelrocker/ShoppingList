package com.example.shoppinglist;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import controllers.ListaComprasController;
import dependencyManager.DependencyManager;
import entities.IListaCompras;
import entities.ListaCompras;
import adapters.ProdutoAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
		setContentView(R.layout.activity_main);
		
		this.lstListaCompras = (ListView)findViewById(R.id.lstListaCompras);	
		this.lstListas = new ArrayList<IListaCompras>();
		adapter = new ArrayAdapter<IListaCompras>(this, android.R.layout.simple_list_item_1, this.lstListas);
		this.lstListaCompras.setAdapter(adapter);
		this.FillListaCompras();
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
	        	novaListaDialog();
	            return true;
	        case R.id.menuNovoProduto:
	        	i = new Intent(this, ProdutosActivity.class);
	    		startActivityForResult(i, MAIN_ACTIVITY);
	            return true;
	        case R.id.menuVerProdutos:
	        	i = new Intent(this, MostarProdutosActivity.class);
	    		startActivityForResult(i, MAIN_ACTIVITY);
	            return true;
	        case R.id.menuSobre:
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
		adapter.clear();
		adapter.addAll(this.lstListas);
		adapter.notifyDataSetChanged();
	}
	
	public void novaListaDialog() {
		LayoutInflater inflater = this.getLayoutInflater();
	    final View inflator = inflater.inflate(R.layout.nova_lista_de_compras, null);
	    final EditText txtNomeNovaLista = (EditText)inflator.findViewById(R.id.txtNomeNovaLista);
		
	    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	        //@Override
	        public void onClick(DialogInterface dialog, int which) {
	            switch (which){
		            case DialogInterface.BUTTON_POSITIVE:
		            	NovaLista(txtNomeNovaLista.getText().toString());
		                break;
	
		            case DialogInterface.BUTTON_NEGATIVE:
		                break;
	            }
	        }
	    };
	    
	    AlertDialog.Builder ab = new AlertDialog.Builder(this);
	    ab.setView(inflator);
	    ab.setMessage("Nova Lista de Compras")
	        .setPositiveButton("Salvar", dialogClickListener)
	        .setNegativeButton("Cancelar", dialogClickListener)
	        .show();
	}
	
	public void NovaLista(String nomeNovaLista) {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ListaComprasController ls = DependencyManager.GetListaComprasController(this);
		IListaCompras lista = new ListaCompras();
		lista.setNome(nomeNovaLista);
		lista.setDataCriacao(sdf.format(new Date()));
		lista.setDataModificacao(sdf.format(new Date()));
		if (ls.saveListaCompras(lista)) {
			FillListaCompras();
			Toast.makeText(this, "Nova lista: " + nomeNovaLista, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "ERRO", Toast.LENGTH_LONG).show();
		}
	}
}

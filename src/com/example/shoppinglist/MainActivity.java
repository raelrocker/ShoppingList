package com.example.shoppinglist;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import controllers.ListaComprasController;
import dependencyManager.DependencyManager;
import entities.IListaCompras;
import entities.IProduto;
import entities.ListaCompras;
import entities.Produto;
import adapters.ListaComprasAdapter;
import adapters.ProdutoAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	ListView lstListaCompras;
	ListaComprasAdapter adapter;
	ArrayList<IListaCompras> lstListas = new ArrayList<IListaCompras>();
	IListaCompras listaSelecionada = null;
	public static int MAIN_ACTIVITY = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		registerForContextMenu(getListView());
		
		adapter = new ListaComprasAdapter(this, R.layout.listview_item_row_lista_compras, this.lstListas);
		
		this.getListView().setAdapter(adapter);
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
	
	public void novaListaDialog(String nome) {
		LayoutInflater inflater = this.getLayoutInflater();
	    final View inflator = inflater.inflate(R.layout.nova_lista_de_compras, null);
	    final EditText txtNomeNovaLista = (EditText)inflator.findViewById(R.id.txtNomeNovaLista);
	    txtNomeNovaLista.setText(nome);
	    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	        //@Override
	        public void onClick(DialogInterface dialog, int which) {
	            switch (which){
		            case DialogInterface.BUTTON_POSITIVE:
		            	salvarLista(txtNomeNovaLista.getText().toString());
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
	
	public void deleteDialog() {
	    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	        //@Override
	        public void onClick(DialogInterface dialog, int which) {
	            switch (which){
		            case DialogInterface.BUTTON_POSITIVE:
		            	removerLista();
		                break;
	
		            case DialogInterface.BUTTON_NEGATIVE:
		                break;
	            }
	        }
	    };
	    AlertDialog.Builder ab = new AlertDialog.Builder(this);
	    
	    ab.setMessage("Tem certeza ?")
	        .setPositiveButton("Confirmar", dialogClickListener)
	        .setNegativeButton("Cancelar", dialogClickListener)
	        .show();
	}
	
	public void removerLista() {
		if (listaSelecionada != null) {
			ListaComprasController ls = DependencyManager.GetListaComprasController(this);
			if (ls.deleteListaCompras(listaSelecionada.getId())) {
				FillListaCompras();
				Toast.makeText(this, "Lista " + listaSelecionada.getNome() + " deletada", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, "ERRO", Toast.LENGTH_LONG).show();
			}
		}
		listaSelecionada = null;
	}
	
	public void salvarLista(String nomeNovaLista) {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ListaComprasController ls = DependencyManager.GetListaComprasController(this);
		if (listaSelecionada == null) {
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
		} else {
			listaSelecionada.setNome(nomeNovaLista);
			listaSelecionada.setDataModificacao(sdf.format(new Date()));
			if (ls.updateListaCompras(listaSelecionada)) {
				FillListaCompras();
				listaSelecionada = null;
				Toast.makeText(this, "Lista editada: " + nomeNovaLista, Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, "ERRO", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	        ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    AdapterView.AdapterContextMenuInfo info;
	    try {
	        info = (AdapterView.AdapterContextMenuInfo) menuInfo;
	    } catch (ClassCastException e) {
	        Log.e("sad", "bad menuInfo", e);
	    return;
	    }

	    IListaCompras lista =  this.adapter.getItem(info.position);
	    menu.setHeaderTitle(lista.getNome());
	    menu.add(1, 0, 0, "Editar");
	    menu.add(1, 1, 0, "Deletar");
	    
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		if (item == null || item.getGroupId() == 1) {
		
		    AdapterView.AdapterContextMenuInfo info;
		    IListaCompras  lista = null;
		    try {
		        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		        lista = this.adapter.getItem(info.position);
		    } catch (ClassCastException e) {
		        Log.e("sad", "bad menuInfo", e);
		        return false;
		    }
		    
		    
		    switch (item.getItemId()) {
			case 0:
				listaSelecionada = lista;
				novaListaDialog(lista.getNome());
				break;
			case 1:
				listaSelecionada = lista;
				deleteDialog();
				break;
	
			default:
				break;
			}
		} else {
			Intent i;
	        switch(item.getItemId()){
		        case R.id.menuNovaLista:
		        	novaListaDialog("");
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
		}
	    
	    return true;
	}
}

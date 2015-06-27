package com.example.shoppinglist;

import java.util.ArrayList;

import controllers.ProdutoController;
import dependencyManager.DependencyManager;
import entities.IProduto;
import entities.Produto;
import adapters.ProdutoAdapter;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

public class MostarProdutosActivity extends ListActivity {

	public static int MOSTRAPRODUTOS_ACTIVITY = 2;
	public static final int PRODUTO_EDITADO = 2;
	public static final int PRODUTO_REMOVIDO = 3;
	public static final int DIALOG_REMOVER = 1;
	public ArrayList<IProduto> arrProdutos = new ArrayList<IProduto>();
	public ProdutoAdapter adapter;
	public ProdutoController pc;
	public IProduto removerProduto;
	public int removerProdutoResultado = -1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		registerForContextMenu(getListView());
		adapter = new ProdutoAdapter(this, R.layout.listview_item_row_produtos, arrProdutos);
		View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row_produtos, null);
		this.getListView().addHeaderView(header);
		this.setListAdapter(adapter);
		FillLista();
		    
		
	}
	
	private void FillLista() {
		pc = DependencyManager.GetProdutoController(this);
		arrProdutos = pc.findAllProdutos();
		adapter.clear();
		adapter.addAll(arrProdutos);
		adapter.notifyDataSetInvalidated();
		
		 
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

	    IProduto produto = (IProduto) getListAdapter().getItem(info.position - 1);
	    menu.setHeaderTitle(produto.getNome());
	    menu.add(0, 0, 0, "Editar");
	    menu.add(0, 1, 0, "Deletar");
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
	    AdapterView.AdapterContextMenuInfo info;
	    Produto produto;
	    try {
	        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
	        produto = (Produto) getListAdapter().getItem(info.position - 1);
	    } catch (ClassCastException e) {
	        Log.e("sad", "bad menuInfo", e);
	        return false;
	    }
	    
	    
	    switch (item.getItemId()) {
		case 0:
			Intent i = new Intent(this, ProdutosActivity.class);
			i.putExtra("Produto", produto);
    		startActivityForResult(i, MOSTRAPRODUTOS_ACTIVITY);
			break;
		case 1:
			removerProduto = produto;
			deleteDialog();			
			
			break;

		default:
			break;
		}
	    
	    return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
			case PRODUTO_EDITADO:
				FillLista();
				break;
			case PRODUTO_REMOVIDO:			
				break;
	
			default:
				break;
		}
	}
	
	
	public void deleteDialog() {
	    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	        //@Override
	        public void onClick(DialogInterface dialog, int which) {
	            switch (which){
	            case DialogInterface.BUTTON_POSITIVE:
	                //Do your Yes progress
	            	RemoverProduto();
	                break;

	            case DialogInterface.BUTTON_NEGATIVE:
	                //Do your No progress
	            	removerProdutoResultado = 0;
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
	
	public void RemoverProduto() {
		if(pc.deleteProduto(removerProduto.getId())) {
			Toast.makeText(this, "Produto removido", Toast.LENGTH_LONG).show();
			FillLista();
			removerProduto = null;
		} else {
			removerProduto = null;
		}
	}

}
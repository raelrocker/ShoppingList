package com.example.shoppinglist;

import java.util.ArrayList;

import adapters.ProdutoAdapter;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import controllers.ProdutoController;
import dependencyManager.DependencyManager;
import entities.IProduto;
import entities.Produto;

public class MostarProdutosActivity extends ListActivity {

	public static int MOSTRAPRODUTOS_ACTIVITY = 2;
	public static final int PRODUTO_ADICIONADO = 1;
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
		this.setListAdapter(adapter);
		this.getListView().setBackgroundColor(Color.argb(255, 232, 232, 232));
		FillLista();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.main, menu);
        menu.getItem(0).setVisible(false);
        menu.getItem(1).setVisible(false);
        menu.getItem(3).setVisible(false);
        return true;
    }
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	        ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    AdapterView.AdapterContextMenuInfo info;
	    try {
	        info = (AdapterView.AdapterContextMenuInfo) menuInfo;
	    } catch (ClassCastException e) {
	        Log.e("Erro", "bad menuInfo", e);
	    return;
	    }

	    IProduto produto = (IProduto) getListAdapter().getItem(info.position);
	    menu.setHeaderTitle(produto.getNome());
	    menu.add(1, 0, 0, "Editar");
	    menu.add(1, 1, 0, "Deletar");
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
	    AdapterView.AdapterContextMenuInfo info;
	    Produto produto;
	    
	    if (item.getGroupId() == 1) {
		    
		    try {
		        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		        produto = (Produto) getListAdapter().getItem(info.position);
		    } catch (ClassCastException e) {
		        Log.e("Erro", "bad menuInfo", e);
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
	    } else {
	    	if (item.getItemId() == R.id.menuNovoProduto) {
	    		Intent i = new Intent(this, ProdutosActivity.class);
	    		startActivityForResult(i, MOSTRAPRODUTOS_ACTIVITY);
	    	}
	    }		    
	    return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
			case PRODUTO_ADICIONADO: 
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
	    	
	        public void onClick(DialogInterface dialog, int which) {
	            switch (which){
	            case DialogInterface.BUTTON_POSITIVE:
	            	RemoverProduto();
	                break;

	            case DialogInterface.BUTTON_NEGATIVE:
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
		try {
			if(pc.deleteProduto(removerProduto.getId())) {
				Toast.makeText(this, "Produto removido", Toast.LENGTH_LONG).show();
				FillLista();
				removerProduto = null;
			} else {
				removerProduto = null;
			}
		} catch (Exception ex) {
			Toast.makeText(this, "Este produto não pode ser deletado", Toast.LENGTH_LONG).show();
		}
	}
	
	private void FillLista() {
		pc = DependencyManager.GetProdutoController(this);
		arrProdutos = pc.findAllProdutos();
		adapter.clear();
		adapter.addAll(arrProdutos);
		adapter.notifyDataSetChanged();
	}

}

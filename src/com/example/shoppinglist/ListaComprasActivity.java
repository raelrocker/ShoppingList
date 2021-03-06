package com.example.shoppinglist;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import adapters.ListaComprasProdutoAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import controllers.ListaComprasController;
import controllers.ProdutoController;
import dependencyManager.DependencyManager;
import entities.IListaComprasProduto;
import entities.IProduto;
import entities.ListaCompras;
import entities.ListaComprasProduto;

public class ListaComprasActivity extends Activity implements OnItemLongClickListener, OnItemClickListener {

	private ListView lsListaComprasProdutos;
	private Intent intent;
	private ListaCompras listaCompras;
	private ListaComprasController listaController;
	private ProdutoController produtoController;
	private ListaComprasProdutoAdapter produtosAdapter;
	private ArrayList<IListaComprasProduto> lsProdutosNaLista;
	private ArrayList<IProduto> lsProdutos;
	private IListaComprasProduto itemSelecionado = null;
	private TextView lblTotalItens;
	private TextView lblValorTotal;
	private ArrayAdapter<IProduto> spinnerAdapter;;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_compras);
		
		intent = getIntent();
		listaController = DependencyManager.GetListaComprasController(this);
		listaCompras =  (ListaCompras)listaController.findListaCompras(intent.getIntExtra("ListaComprasId", 0));
		lsProdutosNaLista = new ArrayList<IListaComprasProduto>();
		lsListaComprasProdutos = (ListView)findViewById(R.id.lstListaCompras2);
		lsListaComprasProdutos.setBackgroundColor(Color.argb(255, 232, 232, 232));
		lblTotalItens = (TextView)findViewById(R.id.lblTotalItens);
		lblValorTotal = (TextView)findViewById(R.id.lblValorTotal);
		produtosAdapter = new ListaComprasProdutoAdapter(this, R.layout.listview_item_row_itens ,lsProdutosNaLista);
		lsListaComprasProdutos.setAdapter(produtosAdapter);
		atualizarListaProdutos();
		
		// LISTA DE PODUTOS
		produtoController = DependencyManager.GetProdutoController(this);
		lsProdutos = produtoController.findAllProdutos();
		lsListaComprasProdutos.setOnItemLongClickListener(this);
		lsListaComprasProdutos.setOnItemClickListener(this);
		this.setTitle(listaCompras.getNome());
		this.getWindow().getDecorView().setBackgroundColor(Color.argb(255, 200, 200, 200));
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_lista_compras, menu);
        return true;
    }
	
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	if (item != null && item.getGroupId() == 1) {
    		AdapterView.AdapterContextMenuInfo info;
		    IListaComprasProduto produto = null;
		    try {
		        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		        produto = this.produtosAdapter.getItem(info.position);
		    } catch (ClassCastException e) {
		        Log.e("Erro", "bad menuInfo", e);
		        return false;
		    }
    		switch (item.getItemId()) {
				case 0:
					itemSelecionado = produto;
					break;
		
				default:
					break;
			}
    	} else {
    		if (item.getItemId() == R.id.menuAddProduto) {
	    		addProdutoDialog();
	    	}
    		
    	}
    	return super.onMenuItemSelected(featureId, item);    	
    }
    
    public void addProdutoDialog() {
    	
		LayoutInflater inflater = this.getLayoutInflater();
	    final View inflator = inflater.inflate(R.layout.add_produto, null);
	    final Spinner spinnerProdutos = (Spinner)inflator.findViewById(R.id.spinnerProdutos);
	    final EditText txtQuantidade = (EditText)inflator.findViewById(R.id.txtItemQuantidade);
	    final EditText txtValor = (EditText)inflator.findViewById(R.id.txtItemNovoValor);
	    final TextView lblValor = (TextView)inflator.findViewById(R.id.lblItemNovoValor);
	    
	    
	    if (itemSelecionado != null) {
	    	
	    	ArrayList<IProduto> ls = new ArrayList<IProduto>();
	    	ls.add(itemSelecionado.getProduto());
	    	spinnerAdapter = new ArrayAdapter<IProduto>(this, android.R.layout.simple_spinner_dropdown_item, ls);
		    spinnerProdutos.setAdapter(spinnerAdapter);
		    txtQuantidade.setText(String.valueOf((int)itemSelecionado.getQuantidade()));
		    txtValor.setText(String.valueOf(itemSelecionado.getPreco()));
		    
	    } else {
	    	
	    	spinnerAdapter = new ArrayAdapter<IProduto>(this, android.R.layout.simple_spinner_dropdown_item, lsProdutos);
		    spinnerProdutos.setAdapter(spinnerAdapter);
		    txtValor.setVisibility(View.INVISIBLE);
		    lblValor.setVisibility(View.INVISIBLE);
		    
	    }
	    
	    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	        //@Override
	        public void onClick(DialogInterface dialog, int which) {
	            switch (which){
	            
		            case DialogInterface.BUTTON_POSITIVE:
		            	IProduto produto = (IProduto)spinnerProdutos.getSelectedItem();
		            	String q = txtQuantidade.getText().toString();
		            	q = q.isEmpty() ? "0" : q;
		            	String preco = txtValor.getText().toString();
		            	preco = preco.isEmpty() ? "0.0" : preco; 
		            	salvarLista(produto, Double.parseDouble(q), Double.parseDouble(preco));
		                break;
	
		            case DialogInterface.BUTTON_NEGATIVE:
		                break;
	            }
	        }
	    };
	    
	    AlertDialog.Builder ab = new AlertDialog.Builder(this);
	    ab.setView(inflator);
	    ab.setMessage("Adicionar Produto")
	        .setPositiveButton("Salvar", dialogClickListener)
	        .setNegativeButton("Cancelar", dialogClickListener)
	        .show();
	}
    
    public void salvarLista(IProduto p, Double qnt, Double preco) {
    	
    	try {
    		if (qnt == 0) {
    			throw new Exception();
    		}
	    	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	if (itemSelecionado != null && p.getId() == itemSelecionado.getProduto().getId()) {
	    		itemSelecionado.setDataModificacao(sdf.format(new Date()));
	    		itemSelecionado.setQuantidade(qnt);
	    		itemSelecionado.setPreco(preco);
		    	if (listaController.updateProduto(this.listaCompras, itemSelecionado)) {
		    		itemSelecionado.getProduto().setPreco(preco);
		    		itemSelecionado.getProduto().setDataModificacao(sdf.format(new Date()));
		    		produtoController.updateProduto(itemSelecionado.getProduto());
		    	}
		    	
	    	} else {
		    	ListaComprasProduto item = new ListaComprasProduto(p, qnt, "UN", false, sdf.format(new Date()), sdf.format(new Date()), p.getPreco());
		    	listaController.saveProduto(this.listaCompras, item);
	    	}
    	} catch (Exception ex) {
    		Toast.makeText(this, "Erro ao adicionar produto � lista de compras", Toast.LENGTH_LONG).show();
    	}
    	itemSelecionado = null;
    	atualizarListaProdutos();
    }
    
    public void atualizarListaProdutos() {
    	
    	lsProdutosNaLista.clear();
    	lsProdutosNaLista.addAll(listaController.findAllProdutos(listaCompras));
    	produtosAdapter.notifyDataSetChanged();
    	lblTotalItens.setText(String.valueOf(listaController.totalDeProdutos(listaCompras) + " PRODUTOS"));
    	
    	lblValorTotal.setText("TOTAL R$ " + String.valueOf(NumberFormat.getCurrencyInstance(new Locale ("pt", "BR")).format(listaController.valorTotalDeProdutos(listaCompras))));
    	
    	if (lsProdutos != null) {
	    	lsProdutos.clear();
	    	lsProdutos.addAll(produtoController.findAllProdutos());
    	}
    	
    }

	@Override
	public boolean onItemLongClick (AdapterView<?> parent, View view, int position, long id) {
		itemSelecionado = produtosAdapter.getItem(position);
		deleteDialog();
		return true;
	}  
	
	public void deleteDialog() {
	    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	        //@Override
	        public void onClick(DialogInterface dialog, int which) {
	            switch (which){
		            case DialogInterface.BUTTON_POSITIVE:
		            	removerProduto();
		                break;
	
		            case DialogInterface.BUTTON_NEGATIVE:
		            	itemSelecionado = null;
		                break;
	            }
	        }
	    };
	    AlertDialog.Builder ab = new AlertDialog.Builder(this);
	    ab.setTitle(itemSelecionado.getProduto().getNome());
	    ab.setMessage("Deseja remover?")
	        .setPositiveButton("Confirmar", dialogClickListener)
	        .setNegativeButton("Cancelar", dialogClickListener)
	        .show();
	}
    
	public void removerProduto() {
		try {
			if (listaController.deleteProduto(listaCompras, itemSelecionado.getProduto().getId())) {
				this.atualizarListaProdutos();
				itemSelecionado = null;
			}
		} catch (Exception ex) {
			Toast.makeText(this, "Erro ao remover produto da lista de compras", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		itemSelecionado = this.produtosAdapter.getItem(position);
		addProdutoDialog();
	}
    
}

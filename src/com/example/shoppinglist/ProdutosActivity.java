package com.example.shoppinglist;

import java.text.SimpleDateFormat;
import java.util.Date;

import controllers.ProdutoController;
import dependencyManager.DependencyManager;
import entities.IProduto;
import entities.Produto;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ProdutosActivity extends Activity implements OnClickListener {

	EditText txtNome;
	EditText txtPreco;
	Button btnSalvar;
	IProduto produto;
	int operacao = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_produtos);
		
		txtNome = (EditText)findViewById(R.id.txtProdutoNome);
		txtPreco = (EditText)findViewById(R.id.txtProdutoPreco);
		txtPreco.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		btnSalvar = (Button)findViewById(R.id.btnProdutoSalvar);
		btnSalvar.setOnClickListener(this);
		Intent i = getIntent();
		produto = new Produto();
		if (i.hasExtra("Produto")) {
			produto = (IProduto)i.getSerializableExtra("Produto");
			txtNome.setText(produto.getNome());
			txtPreco.setText(String.valueOf(produto.getPreco()));
			this.operacao = 2;
		}
	}

	@Override
	public void onClick(View v) {
		
		if (v.getId() == R.id.btnProdutoSalvar) {
			ProdutoController pc = DependencyManager.GetProdutoController(this);
			this.produto.setNome(txtNome.getText().toString());
			this.produto.setPreco(Double.parseDouble(txtPreco.getText().toString()));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.produto.setDataModificacao(sdf.format(new Date()));
			if (this.produto.getId() == 0) {
				this.produto.setDataCriacao(sdf.format(new Date()));
				pc.saveProduto(produto);
			} else {
				pc.updateProduto(produto);
			}
			setResult(this.operacao);
			finish();
			
		}
		
	}
}

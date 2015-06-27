package adapters;

import java.util.ArrayList;

import com.example.shoppinglist.R;

import entities.IProduto;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ProdutoAdapter extends ArrayAdapter<IProduto> {

	Context context; 
    int layoutResourceId;    
    ArrayList<IProduto> data = null;
	
	public ProdutoAdapter(Context context, int layoutResourceId, ArrayList<IProduto> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ProdutoHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new ProdutoHolder();
            holder.lblNomeProduto = (TextView)row.findViewById(R.id.lblNomeProduto);
            holder.lblPrecoProduto = (TextView)row.findViewById(R.id.lblPrecoProduto);
            
            row.setTag(holder);
        }
        else
        {
            holder = (ProdutoHolder)row.getTag();
        }
        
        IProduto produto = data.get(position);
        holder.lblNomeProduto.setText(produto.getNome());
        holder.lblPrecoProduto.setText("R$ " + String.valueOf(produto.getPreco()));
        
        return row;
    }
    
    static class ProdutoHolder
    {
    	TextView lblNomeProduto;
        TextView lblPrecoProduto;
    }
	
	
}

package adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shoppinglist.R;

import entities.IListaComprasProduto;
import entities.IProduto;

public class ListaComprasProdutoAdapter extends ArrayAdapter<IListaComprasProduto> {
	Context context; 
    int layoutResourceId;    
    ArrayList<IListaComprasProduto> data = null;
	
	public ListaComprasProdutoAdapter(Context context, int layoutResourceId, ArrayList<IListaComprasProduto> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new ItemHolder();
            holder.lblItemNome = (TextView)row.findViewById(R.id.lblItemNome);
            holder.lblItemQuantidade = (TextView)row.findViewById(R.id.lblItemQuantidade);
            
            row.setTag(holder);
        }
        else
        {
            holder = (ItemHolder)row.getTag();
        }
        
        IListaComprasProduto item = data.get(position);
        holder.lblItemNome.setText(item.getProduto().getNome());
        holder.lblItemQuantidade.setText("Qnt: " + String.valueOf(item.getQuantidade()));
        
        return row;
    }
    
    static class ItemHolder
    {
    	TextView lblItemNome;
        TextView lblItemQuantidade;
    }
}

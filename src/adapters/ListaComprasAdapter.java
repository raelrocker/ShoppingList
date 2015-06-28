package adapters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.example.shoppinglist.R;

import entities.IListaCompras;
import entities.IProduto;
import entities.ListaCompras;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListaComprasAdapter extends ArrayAdapter<IListaCompras> {

	Context context; 
    int layoutResourceId;    
    ArrayList<IListaCompras> data = null;
	
	public ListaComprasAdapter(Context context, int layoutResourceId, ArrayList<IListaCompras> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ListaComprasHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new ListaComprasHolder();
            holder.lblNomeLista = (TextView)row.findViewById(R.id.lblNomeListaCompras);
            holder.lblDataModificacao = (TextView)row.findViewById(R.id.lblDataModificacaoListaCompras);
            
            row.setTag(holder);
        }
        else
        {
            holder = (ListaComprasHolder)row.getTag();
        }
        
        IListaCompras lista = data.get(position);
        holder.lblNomeLista.setText(lista.getNome());
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date data = null;
		try {
			data = sdf.parse(lista.getDataModificacao());
		} catch (ParseException e) {
			e.printStackTrace();
		}
        holder.lblDataModificacao.setText(sdf2.format(data));
        
        return row;
    }
    
    static class ListaComprasHolder
    {
    	TextView lblNomeLista;
        TextView lblDataModificacao;
    }
	
	
}

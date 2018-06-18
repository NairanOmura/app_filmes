package com.martin.calculadorasimples;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


public class Adapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    protected DbHelper db;
    public String parametro;

    public Adapter(Context context, String parametro){
        this.parametro = parametro;
        this.db = new DbHelper(context);
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    public Adapter(Context context){
        this.db = new DbHelper(context);
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return parametro=="" || parametro ==null? db.getQuantidade():db.getQuantidade(parametro);
    }

    @Override
    public Object getItem(int i) {
        return parametro==null || parametro ==""? db.getItem(i):db.getItem(i, parametro);
    }

    @Override
    public long getItemId(int i) {
        return db.getItem(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_listview, null);
        }

        TextView tituloTextView = (TextView) view.findViewById(R.id.tituloTextView);
        //TextView urlTextView = (TextView) view.findViewById(R.id.urlTextView);
        TextView generoTextView = (TextView) view.findViewById(R.id.generoTextView);
        TextView avaliacaoTextView = (TextView) view.findViewById(R.id.avaliacaoTextView);
        TextView lancamentoTextView = (TextView) view.findViewById(R.id.lancamentoTextView);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);


        Filme filme = (Filme) getItem(i);

        if(filme.getImage() != null){
           Picasso.get().load(filme.getImage()).into(imageView);
        }

        Log.d("MEUAPP","Posição: "+ i);

        tituloTextView.setText(filme.getTitle());
        //urlTextView.setText(filme.getImage());
        generoTextView.setText(filme.getGenresSTR());
        avaliacaoTextView.setText(filme.getRating());
        lancamentoTextView.setText(filme.getReleaseYear());

        return view;
    }
}

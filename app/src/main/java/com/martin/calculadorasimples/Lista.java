package com.martin.calculadorasimples;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Lista extends AppCompatActivity {

    private Context context = this;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.putExtra("ID", 0);
                intent.putExtra("TIPO","INSERCAO");
                startActivity(intent);
            }
        });

        if (new DbHelper(context).getQuantidade() < 14) {
            consultasWS();
        }

        //new DbHelper(context).getWritableDatabase().execSQL("DELETE FROM tb_filme");

        final EditText txtSearch = (EditText) findViewById(R.id.txtSearch);

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                ListView listview = (ListView) findViewById(R.id.listviewFilmes);
                String parametro = String.valueOf(txtSearch.getText());
                adapter = new Adapter(context, parametro);

                listview.setAdapter(adapter);
            }
        });

        ListView listview = (ListView) findViewById(R.id.listviewFilmes);
        adapter = new Adapter(context);
        listview.setAdapter(adapter);

        /** Alterar item clicado **/
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.putExtra("ID", l);
                intent.putExtra("TIPO","ALTERACAO");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    public void consultasWS() {

        AsyncHttpClient cliente = new AsyncHttpClient();
        String url = "https://api.androidhive.info/json/movies.json";
        cliente.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (response.length() > 0) {
                    DbHelper db = new DbHelper(context);

                    Type type = new TypeToken<List<Filme>>() {}.getType();
                    String json = response.toString();
                    List<Filme> lista_filmes = new Gson().fromJson(json, type);

                    for (Filme filme : lista_filmes) {
                        db.inserir(filme);
                    }
                    db.close();
                    onResume();
                }
            }
        });
    }

}

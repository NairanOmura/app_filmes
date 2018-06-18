package com.martin.calculadorasimples;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity {
    private Context context = this;
    private ImageButton adicaoButton;
    private ImageButton removerButton;
    private EditText title;
    private EditText url;
    private EditText genre;
    private EditText rating;
    private EditText releaseYear;
    private Filme filme_old;

    private void MainActivity() {
        super.onResume();
        new Adapter(context).notifyDataSetChanged();

        filme_old = new Filme();

        adicaoButton = (ImageButton) findViewById(R.id.adicaoImageButton);
        removerButton = (ImageButton) findViewById(R.id.removerImageButton);

        title = (EditText) findViewById(R.id.title);
        url = (EditText) findViewById(R.id.url);
        genre = (EditText) findViewById(R.id.genre);
        rating = (EditText) findViewById(R.id.rating);
        releaseYear = (EditText) findViewById(R.id.releaseYear);
    }

    private void comparaValoresCampos() {
        /** Valores atuais */
        title = (EditText) findViewById(R.id.title);
        url = (EditText) findViewById(R.id.url);
        genre = (EditText) findViewById(R.id.genre);
        rating = (EditText) findViewById(R.id.rating);
        releaseYear = (EditText) findViewById(R.id.releaseYear);

        /** Novos valores */
        String newTitle = String.valueOf(title.getText());
        String newUrl = String.valueOf(url.getText());
        String newGenre = String.valueOf(genre.getText());
        String newRating = String.valueOf(rating.getText());
        String newReleaseYear = String.valueOf(releaseYear.getText());

        if (!filme_old.getTitle().equals(newTitle) ||
                !filme_old.getImage().equals(newUrl) ||
                !filme_old.getGenresSTR().equals(newGenre) ||
                !filme_old.getRating().equals(newRating) ||
                !filme_old.getReleaseYear().equals(newReleaseYear))
        {
            new DbHelper(context).alterar(new Filme(
                    filme_old.getId(),
                    newTitle,
                    newUrl,
                    newGenre,
                    newRating,
                    newReleaseYear
            ));
        }
    }

    private void validaCampos() {
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                comparaValoresCampos();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        url.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                comparaValoresCampos();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        genre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                comparaValoresCampos();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        rating.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                comparaValoresCampos();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        releaseYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                comparaValoresCampos();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        MainActivity();

        final long id = getIntent().getLongExtra("ID", 0);

        if (getIntent().getStringExtra("TIPO").equals("ALTERACAO")) {
            validaCampos();
            adicaoButton.setVisibility(GONE);

            DbHelper db = new DbHelper(context);
            Filme filme = db.getItemID(id);

            /** Guarda informações antes de edição */
            filme_old.setId(id);
            filme_old.setTitle(filme.getTitle());
            filme_old.setImage(filme.getImage());
            filme_old.setGenresSTR(filme.getGenresSTR());
            filme_old.setRating(filme.getRating());
            filme_old.setReleaseYear(filme.getReleaseYear());

            title.setText(filme.getTitle());
            url.setText(filme.getImage());
            genre.setText(filme.getGenresSTR());
            rating.setText(filme.getRating());
            releaseYear.setText(filme.getReleaseYear());
            db.close();

            Log.d("MEUAPP", filme.toString());
            Log.d("MEUAPP", String.valueOf(id));
        } else {
            removerButton.setVisibility(GONE);
        }

        adicaoButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(context);
                alerta.setTitle("Confirmation");
                alerta.setMessage("Insert "+'"'+ title.getText() +'"'+ " to movie list?");

                alerta.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        final DbHelper db = new DbHelper(context);
                        final Filme filme = new Filme();
                        filme.setTitle(String.valueOf(title.getText()));
                        filme.setImage(String.valueOf(url.getText()));
                        filme.setGenresSTR(String.valueOf(genre.getText()));
                        filme.setRating(String.valueOf(rating.getText()));
                        filme.setReleaseYear(String.valueOf(releaseYear.getText()));
                        long id = db.inserir(filme);

                        if (id>0) {
                            Toast.makeText(context,"Successful", Toast.LENGTH_SHORT).show();
                        }
                        db.close();
                        finish();
                    }
                });

                alerta.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context,"Canceled", Toast.LENGTH_SHORT).show();
                    }
                });

                alerta.show();
            }
        });

        removerButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(context);
                alerta.setTitle("Confirmation");
                alerta.setMessage("You want to remove "+'"'+ title.getText() +'"'+ " to movie list?");

                alerta.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        final DbHelper db = new DbHelper(context);
                        db.excluir(id);
                        Toast.makeText(context,"Successful", Toast.LENGTH_SHORT).show();
                        db.close();
                        finish();
                    }
                });

                alerta.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context,"Canceled", Toast.LENGTH_SHORT).show();
                    }
                });

                alerta.show();
            }
        });
    }
}









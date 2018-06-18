package com.martin.calculadorasimples;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.martin.calculadorasimples.Filme;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    private static final String NOME_BASE = "db.martin";
    private static final int VARSAO_BASE = 1;

    public DbHelper(Context context) {
        super(context, NOME_BASE, null, VARSAO_BASE);
    }

    public long inserir(Filme filme){
        ContentValues values = new ContentValues();
        values.put("title", filme.getTitle());
        values.put("image", filme.getImage());
        values.put("genre", filme.getGenresSTR());
        values.put("rating", filme.getRating());
        values.put("releaseYear", filme.getReleaseYear());

        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert("tb_filme", null, values);
        Log.d("LISTANDO", filme.toString());
        db.close();
        return id;
    }

    public void alterar(Filme filme) {

        ContentValues values = new ContentValues();
        values.put("title", filme.getTitle());
        values.put("image", filme.getImage());
        values.put("genre", filme.getGenresSTR());
        values.put("rating", filme.getRating());
        values.put("releaseYear", filme.getReleaseYear());
        String where = "id = ?";

        SQLiteDatabase db = getWritableDatabase();
        db.update("tb_filme", values, where,
                new String[]{String.valueOf(filme.getId())});
        db.close();
    }

    public void excluir(long id) {

        String whare = "id = ?";

        SQLiteDatabase db = getWritableDatabase();
        int ret = db.delete("tb_filme", whare,
                new String[]{String.valueOf(id)});
    }

    public int getQuantidade(){
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM tb_filme";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor.getCount();
    }

    public int getQuantidade(String parametro){
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery;
            selectQuery = "SELECT * FROM tb_filme WHERE " +
                    " title like '%" + parametro + "%' " +
                    " OR genre like '%"+ parametro+ "%'"+
                    " OR rating like '%"+parametro+ "%'" +
                    " OR releaseYear like '%"+ parametro +"%'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor.getCount();
    }

    public Filme getItem(int posicao, String parametro){
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM tb_filme WHERE " +
                " title like '%" + parametro + "%' " +
                " OR genre like '%"+ parametro+ "%'"+
                " OR rating like '%"+parametro+ "%'" +
                " OR releaseYear like '%"+ parametro +"%'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<Filme> filmeLista = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Filme filme = new Filme();
                filme.setId(cursor.getLong(0));
                filme.setTitle(cursor.getString(1));
                filme.setImage(cursor.getString(2));
                filme.setGenresSTR(cursor.getString(3));
                filme.setRating(cursor.getString(4));
                filme.setReleaseYear(cursor.getString(5));
                filmeLista.add(filme);
            } while (cursor.moveToNext());
            db.close();
        }
        return filmeLista.get(posicao);
    }

    public Filme getItem(int posicao){
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM tb_filme";
        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<Filme> filmeLista = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Filme filme = new Filme();
                filme.setId(cursor.getLong(0));
                filme.setTitle(cursor.getString(1));
                filme.setImage(cursor.getString(2));
                filme.setGenresSTR(cursor.getString(3));
                filme.setRating(cursor.getString(4));
                filme.setReleaseYear(cursor.getString(5));
                filmeLista.add(filme);
            } while (cursor.moveToNext());
            db.close();
        }
        return filmeLista.get(posicao);
    }

    public Filme getItemID(long id) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tb_filme WHERE id="+id;
        Cursor cursor = db.rawQuery(sql, null);

        cursor.moveToFirst();
        Filme filme = new Filme();
        filme.setId(cursor.getLong(0));
        filme.setTitle(cursor.getString(1));
        filme.setImage(cursor.getString(2));
        filme.setGenresSTR(cursor.getString(3));
        filme.setRating(cursor.getString(4));
        filme.setReleaseYear(cursor.getString(5));

        return filme;
    }


   @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CRIA_TABELA_filme = "CREATE TABLE tb_filme("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + "title text, "
                + "image text, "
                + "genre text, "
                + "rating text, "
                + "releaseYear text )";
        sqLiteDatabase.execSQL(CRIA_TABELA_filme);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String Delete = "DROP TABLE IF EXISTS tb_filmes";

        sqLiteDatabase.execSQL(Delete);
        onCreate(sqLiteDatabase);

    }



}

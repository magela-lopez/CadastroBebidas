package com.mobileexercicio.cadastrobebidas;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BebidaDB extends SQLiteOpenHelper {
    private static final String TAG = "sql";
    private static final String NOME_BANCO = "cadastroBebidas.sqLite";
    private static final int VERSAO= 1;

    public BebidaDB(@Nullable Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists bebidas(id integer primary key autoincrement, nome TEXT, preco REAL,codigo TEXT, tipo TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long update(Bebida bebida){
        long id = bebida.getId();
        SQLiteDatabase db = getWritableDatabase();
      try {
            ContentValues values = new ContentValues();
            values.put("nome", bebida.getNome());
            values.put("preco", bebida.getPreco());
            values.put("codigo", bebida.getCodigo());
            values.put("tipo",bebida.getTipo());

                //atualizar
                String id2 = String.valueOf(bebida.getId());
                String[] whereArgs = new String []{id2};
                int count = db.update("bebidas", values,"id=?", whereArgs);
                return count;

        }finally {
            db.close();
        }
    }

    public long save(Bebida bebida){
        long id = bebida.getId();
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nome", bebida.getNome());
        values.put("preco", bebida.getPreco());
        values.put("codigo", bebida.getCodigo());
        values.put("tipo",bebida.getTipo());

        id = db.insert("bebidas","",values);

        return id;

    }

    public long delete (long id){
        SQLiteDatabase db = getWritableDatabase();
        String id2 = String.valueOf(id);
        String[] whereArgs = new String []{id2};
        db.delete("bebidas","id=?",whereArgs);

        return id;
    }


    public List<Bebida> findAll(){
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor c = db.query("bebidas", null,null,null,null,null,null);
            return toList(c);
        }finally {
            db.close();
        }
    }

    @SuppressLint("Range")
    public List<Bebida> toList(Cursor c) {
        List<Bebida> bebidas = new ArrayList<Bebida>();
        if(c.moveToNext()){
            do{
                Bebida bebida = new Bebida();
                bebida.setId(c.getLong(c.getColumnIndex("id")));
                bebida.setNome(c.getString(c.getColumnIndex("nome")));
                bebida.setCodigo(c.getString(c.getColumnIndex("codigo")));
                bebida.setPreco(c.getDouble(c.getColumnIndex("preco")));
                bebida.setTipo(c.getString(c.getColumnIndex("tipo")));
                bebidas.add(bebida);
            }while(c.moveToNext());
        }
        return bebidas;
    }
}

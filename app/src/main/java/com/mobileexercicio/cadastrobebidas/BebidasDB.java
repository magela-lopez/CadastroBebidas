package com.mobileexercicio.cadastrobebidas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BebidasDB extends SQLiteOpenHelper {

    public BebidasDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql ="CREATE TABLE IF NOT EXISTS bebidas (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " nome TEXT, preco REAL, codigo TEXT , volume REAL, tipo TEXT);" ;
        db.execSQL(sql);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

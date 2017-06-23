package br.com.ufrpe.isantos.trilhainterpretativa.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.ufrpe.isantos.trilhainterpretativa.entity.Trail;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ivan on 22/06/2017.
 */

public class TrailSQLiteUtils {
    private SQLiteDatabase banco;
    private Cursor dados;
    public TrailSQLiteUtils(Context appContext){
        banco = appContext.openOrCreateDatabase("bancoNomes",MODE_PRIVATE,null);

        banco.execSQL("CREATE TABLE if not exists tb_nome (nome VARCHAR(255))");

    }

    public Trail getTrail(Integer id){
        return null;
    }
}

package com.example.allii;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created  by ali & sabrina 11/2018.
 */

public class HelperDB extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "intelli.db";
        final String SCORE="score",SAUVEGARDE="sauvgarde";

    protected SQLiteDatabase getDb() {
        if (mDb == null || !mDb.isOpen())
            mDb = getWritableDatabase();
        return mDb;
    }

    static protected SQLiteDatabase mDb = null;

    public HelperDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+SCORE+" (id integer primary key , name string(255),nbdeplacement integer,temps integer,nbsecdep integer);");
        sqLiteDatabase.execSQL("create table "+SAUVEGARDE+" (id integer primary key , name string(255),nbdeplacement integer," +
                "temps integer,niveau integer,grill string(255));");
        sqLiteDatabase.execSQL("insert into "+SCORE+" (id, name,nbdeplacement ,temps,nbsecdep )values" +
                "(1, \" \",0 ,0,10000 )," +
                "(2, \" \",0 ,0,10000 )," +
                "(3, \" \",0 ,0,10000 )," +
                "(4, \" \",0 ,0,10000 )," +
                "(5, \" \",0 ,0,10000 )," +
                "(6, \" \",0 ,0,10000 )," +
                "(7, \" \",0 ,0,10000 )," +
                "(8, \" \",0 ,0,10000 )," +
                "(9, \" \",0 ,0,10000 )," +
                "(10, \" \",0 ,0,10000 );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

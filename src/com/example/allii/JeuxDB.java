package com.example.allii;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Sabrina en 2018
 */

public class JeuxDB extends HelperDB {
    public JeuxDB(Context context) {
        super(context);
    }

    public Jeux addJeux(Jeux jeux){
        SQLiteDatabase db = getDb();
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();//pour inserer dans la base de donnée
        //Log.i("addJeux", "addJeux: "+jeux.getLevel());
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put("name", jeux.getName());
        values.put("nbdeplacement", jeux.getNbmove());
        values.put("temps", jeux.getNbtimer());
        values.put("niveau", jeux.getLevel());
        values.put("grill", Helper.ArrayToString(jeux.getMatrice()));
        long inserted = db.insert(SAUVEGARDE, null, values);
        jeux.setId((int) inserted);
        return jeux;
    }

    public void delJeux(int id){
        getDb().delete(SAUVEGARDE,"id = "+id,new String[]{});
    }

    public Jeux getJeux(int id)
    {
        String sql="Select * from "+SAUVEGARDE+" where id = ?";
        Cursor cursor;
        cursor=getDb().rawQuery(sql,new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        Jeux jeux=new Jeux(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("name")),
                cursor.getInt(cursor.getColumnIndex("nbdeplacement")),
                cursor.getInt(cursor.getColumnIndex("temps")),
                cursor.getInt(cursor.getColumnIndex("niveau")),
              Helper.StringToArray(cursor.getString(cursor.getColumnIndex("grill")))
        );
        return jeux;
    }

    public ArrayList<Jeux> getallJeux()
    {
        String sql="Select * from "+SAUVEGARDE;
        Cursor cursor;
        cursor = getDb().rawQuery(sql, new String[]{});
        ArrayList<Jeux> allJeux = new ArrayList<>();
        while (cursor.moveToNext()) {
            Jeux jeu=new Jeux();
            jeu.setId(cursor.getInt(cursor.getColumnIndex("id")));
            jeu.setName(cursor.getString(cursor.getColumnIndex("name")));
            jeu.setNbtimer(cursor.getInt(cursor.getColumnIndex("temps")));
            jeu.setNbmove(cursor.getInt(cursor.getColumnIndex("nbdeplacement")));
            jeu.setLevel(cursor.getInt(cursor.getColumnIndex("niveau")));
            jeu.setMatrice(Helper.StringToArray(cursor.getString(cursor.getColumnIndex("grill"))));
            allJeux.add(jeu);
        }
        return allJeux;
    }

}

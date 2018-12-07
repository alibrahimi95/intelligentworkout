package com.example.allii;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ALI  01/12/2018.
 */

public class ScoreDB extends HelperDB {
    public ScoreDB(Context context) {
        super(context);
    }



    public void updateScore(Score score)
    {
        SQLiteDatabase db = getDb();
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put("name", score.getName());
        values.put("nbdeplacement", score.getNbdeplacement());
        values.put("temps", score.getTemps());
        values.put("nbsecdep", score.getNbsecdep());
        db.update(SCORE, values,"id= "+score.getId(),null);

    }


    public ArrayList<Score> getallScore()
    {
        String sql="Select * from "+SCORE+" order by nbsecdep asc";
        Cursor cursor;
        cursor = getDb().rawQuery(sql, new String[]{});
        ArrayList<Score> allScore = new ArrayList<>();
        while (cursor.moveToNext()) {
            Score score=new Score(cursor.getInt(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getInt(cursor.getColumnIndex("nbdeplacement")),cursor.getInt(cursor.getColumnIndex("temps")),
                    cursor.getInt(cursor.getColumnIndex("nbsecdep"))  );
            allScore.add(score);
        }
        return allScore;
    }
}

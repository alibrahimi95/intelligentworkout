package com.example.allii;
import java.util.ArrayList;

import com.example.ali.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

// BestPlayerActivity affichage de la liste best score

public class BestPlayerActivity extends  Activity {
    ArrayList<Score> scorelist;
    @TargetApi(11)
    @Override

    //execute un fois l activity crée
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_player);
        scorelist=new ScoreDB(this).getallScore();
        RecyclerView recylcer=(RecyclerView) findViewById(R.id.Score_recyclerView);
        recylcer.setLayoutManager(new LinearLayoutManager(this));// set un layout pour l affichage
        recylcer.setAdapter(new BestPlayerAdapter(scorelist));//set addapteur pour remplir le recycler view
    }
}

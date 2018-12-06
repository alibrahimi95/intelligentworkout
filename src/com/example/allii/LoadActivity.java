package com.example.allii;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import com.example.ali.R;

public class LoadActivity extends  Activity{

    RecyclerView lvListe;
    ArrayList<Jeux> alljeux=new ArrayList<Jeux>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        alljeux= new JeuxDB(this).getallJeux();
        lvListe = (RecyclerView) findViewById(R.id.Load_recyclerView);
        lvListe.setLayoutManager(new LinearLayoutManager(this));
        lvListe.setAdapter(new LoadAdapter(alljeux));
    }
}

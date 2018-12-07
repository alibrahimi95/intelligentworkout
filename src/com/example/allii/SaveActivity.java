package com.example.allii;

import com.example.ali.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SaveActivity extends Activity{
    TextView savetexte;
    Jeux jeux=new Jeux();
    private Button button_jeux_save,bouton_jeux_cancel;
    private EditText jeux_save_name;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        context=this;
        savetexte=(TextView) findViewById(R.id.savetexte);
        jeux_save_name=(EditText) findViewById(R.id.jeux_save_name);
        jeux.setMatrice(Helper.StringToArray(getIntent().getStringExtra("carte")));
        jeux.setNbtimer(getIntent().getIntExtra("nbtimer",0));
        jeux.setNbmove(getIntent().getIntExtra("nbmove",0));
        jeux.setLevel(getIntent().getIntExtra("level",0));
        bouton_jeux_cancel=(Button) findViewById(R.id.bouton_jeux_cancel);
        bouton_jeux_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button_jeux_save=(Button) findViewById(R.id.button_jeux_save);
        button_jeux_save.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
			@Override
            public void onClick(View view) {
                if(jeux_save_name.getText().toString().isEmpty()){
                    Toast.makeText(context, "Veuillez saisir un nom pour la sauvgarde", Toast.LENGTH_SHORT).show();
                }
                else{
                    jeux.setName(jeux_save_name.getText().toString());
                    JeuxDB dbjeux=new JeuxDB(context);
                    jeux=dbjeux.addJeux(jeux);
                    Toast.makeText(context, "Partie sauvegardée", Toast.LENGTH_SHORT).show();
                    finish();


                }
            }
        });



    }
}

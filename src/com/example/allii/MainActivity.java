package com.example.allii;

import com.example.ali.R;
import com.example.allii.AccueilActivity;

import android.app.Activity;
import android.content.Intent;// pour y aller dune feunetre a une autre
import android.media.MediaPlayer; //musique
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements Runnable {
    MenuItem pauseResume,sound;
    private IntelligentView mIntelligent;
    TextView int_move,int_timer;
    Boolean runthread=true;
    private     Thread timer_thread;
    MediaPlayer music;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int_move=(TextView) this.findViewById(R.id.int_move);
        int_timer=(TextView) this.findViewById(R.id.int_timer);
        int_timer.setText("0");

        // recuperation de la vue une voie cree à partir de son id
        mIntelligent = (IntelligentView)findViewById(R.id.IntelligentView);
        // rend visible la vue
        mIntelligent.settextview(int_move,int_timer);
        mIntelligent.setVisibility(View.VISIBLE);
        int toload= getIntent().getIntExtra("toload",0);
        music= MediaPlayer.create(MainActivity.this,R.raw.zeldadubstep);

        music.setLooping(true);
        music.start();

       if(toload==1)
       {
           //nbtimer=getIntent().getIntExtra("nbtimer",0);
           int_move.setText(String.valueOf(getIntent().getIntExtra("nbmove",0)));
           int_timer.setText(String.valueOf(getIntent().getIntExtra("nbtimer",0)));
           mIntelligent.setCarte_m(Helper.getGrillRef(getIntent().getIntExtra("level",0)));
           mIntelligent.setCarte(Helper.StringToArray(getIntent().getStringExtra("carte")));
           mIntelligent.nbmove=getIntent().getIntExtra("nbmove",0);


       }
        mIntelligent.initparameters();



        timer_thread   = new Thread(this);
        if ((timer_thread!=null) && (!timer_thread.isAlive())) {
            timer_thread.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        music.stop();
    }


    @Override
    protected void onResume() {

        super.onResume();
        music.start();
        mIntelligent.resume();

    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mIntelligent.setCarte((int[][])  Helper.StringToArray(savedInstanceState.getString("carte")));
        mIntelligent.setCarte_m((int[][]) Helper.StringToArray(savedInstanceState.getString("carte_m")));
        int_timer.setText(String.valueOf(getIntent().getIntExtra("nbtimer",0)));
        mIntelligent.nbmove=savedInstanceState.getInt("nbmove");
        mIntelligent.initparameters();
        int_move.setText(String.valueOf(mIntelligent.nbmove));
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("carte",  Helper.ArrayToString(mIntelligent.getCarte()));
        savedInstanceState.putString("carte_m", Helper.ArrayToString(mIntelligent.getCarte_m()));
        savedInstanceState.putInt("nbtimer", Integer.valueOf( int_timer.getText().toString()));
        savedInstanceState.putInt("nbmove", mIntelligent.nbmove);
        super.onSaveInstanceState(savedInstanceState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.menu_accueil);
        pauseResume=menu.findItem(R.id.menu_pause);
        sound=menu.findItem(R.id.menu_sound);
        sound.setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_pause:
                Log.i("pause", "onOptionsItemSelected: "+mIntelligent.isIspause());
                if(!mIntelligent.isIspause()){
                    mIntelligent.setIspause(true);
                    pauseResume.setTitle("Resume");
                }
                else {
                    mIntelligent.setIspause(false);
                    pauseResume.setTitle("Pause");
                }
                return true;
            case R.id.menu_save:
                Intent intent=new Intent(MainActivity.this,SaveActivity.class);
                intent.putExtra("nbtimer",Integer.valueOf( int_timer.getText().toString()));
                intent.putExtra("nbmove",mIntelligent.nbmove);
                intent.putExtra("carte_m",Helper.ArrayToString(mIntelligent.getCarte_m()));
                intent.putExtra("carte",Helper.ArrayToString(mIntelligent.getCarte()));
                intent.putExtra("level",mIntelligent.niveau);
                mIntelligent.debutjeux=true;

                startActivity(intent);
                return true;
            case R.id.menu_accueil:
                Intent intent1= new Intent(MainActivity.this,AccueilActivity.class);
                startActivity(intent1);
                return true;
            case R.id.menu_sound:
                if(music.isPlaying()){
                    music.pause();

                    sound.setIcon(R.drawable.stopicone);
                }else {
                    music.start();

                    sound.setIcon(R.drawable.playicone);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void run() {
        while (runthread){
            try {

                timer_thread.sleep(1000);
                if(!mIntelligent.isIspause() && mIntelligent.debutjeux&& !mIntelligent.isWon()) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            incTimeer();
                        }
                    });
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void incTimeer(){
        int timeer=Integer.valueOf(int_timer.getText().toString());
        timeer++;
        int_timer.setText(String.valueOf(timeer));
    }
}

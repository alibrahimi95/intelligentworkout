package com.example.allii;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import com.example.ali.R;

//extends surfaceView permet d'ajouter intelligentView au fichier XML
public class IntelligentView extends SurfaceView implements SurfaceHolder.Callback, Runnable,GestureDetector.OnGestureListener {

    boolean ispause=false,debutjeux=false;
    TextView int_move,int_timer;
    Integer nbmove;
    int nbtimer=0;

    // Declaration des images
    private Bitmap 		rouge;
    private Bitmap 		rougem;
    private Bitmap 		bleu;
    private Bitmap 		bleum;
    private Bitmap[] 	zone = new Bitmap[4];
    private Bitmap 		win;
     Integer     niveau=0;
    private Bitmap      timer;

	// Declaration des objets Ressources et Context permettant d'accéder aux ressources de notre application et de les charger
    private Resources 	mRes;    
    private Context 	mContext;

    // tableau modelisant la carte du jeu
    private int[][] carte;
    public int[][] carte_m;
    
    // ancres pour pouvoir centrer la carte du jeu
    int        carteTopAnchor;                   // coordonnées en Y du point d'ancrage de notre carte
    int        carteLeftAnchor;                  // coordonnées en X du point d'ancrage de notre carte

    // ancres pour pouvoir centrer la carte du jeu
    int        carte_mTopAnchor;                   // coordonnées en Y du point d'ancrage de la miniature
    int        carte_mLeftAnchor;                  // coordonnées en X du point d'ancrage de la miniature

    GestureDetector detector;

    // tableau de reference du terrain



    // thread utiliser pour animer les zones de depot des diamants
        private     boolean in      = true;
        private     Thread  cv_thread;
        SurfaceHolder holder;

        Paint paint;
        
    /**
     * The constructor called from the main JetBoy activity
     * 
     * @param context 
     * @param attrs 
     */
    public IntelligentView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            detector=new GestureDetector(this.getContext(),this);
        }
        // permet d'ecouter les surfaceChanged, surfaceCreated, surfaceDestroyed
    	holder = getHolder();
        holder.addCallback(this);
        mContext	= context;

        // chargement des images
        mRes 		= mContext.getResources();
        rouge 		= BitmapFactory.decodeResource(mRes, R.drawable.rouge);
        rougem		= BitmapFactory.decodeResource(mRes, R.drawable.rougem);
    	bleu 		= BitmapFactory.decodeResource(mRes, R.drawable.bleu);
    	bleum 		= BitmapFactory.decodeResource(mRes, R.drawable.bleum);
    	win 		= BitmapFactory.decodeResource(mRes, R.drawable.win);
        carte_m           = new int[Helper.CARTEHEIGHT][Helper.CARTEWIDTH];
        setCarte(new int[Helper.CARTEHEIGHT][Helper.CARTEWIDTH]);
        loadleve();
        nbmove=0;
    	// initialisation des parmametres du jeu
    	initparameters();
    	// creation du thread
        cv_thread   = new Thread(this);

        // prise de focus pour gestion des touches
        setFocusable(true);

    }



    // chargement de la miniateur 1 _______________________________________________
   /* private int[][] loadlevelm() {
        return Helper.getGrillRef(niveau);
    }*/
    private void loadleve() {
        carte_m= Helper.getGrillRef(niveau);
        setCarte(Helper.getRandomGrill());
    }

    // initialisation du jeu
    public void initparameters() {
    	paint = new Paint();
    	paint.setColor(0xff0000);
    	paint.setDither(true);
    	paint.setColor(0xFFFFFF00);
    	paint.setStyle(Paint.Style.STROKE);
    	paint.setStrokeJoin(Paint.Join.ROUND);
    	paint.setStrokeCap(Paint.Cap.ROUND);
    	paint.setStrokeWidth(2);
    	paint.setTextAlign(Paint.Align.LEFT);
        carteTopAnchor  = (getHeight()- Helper.CARTEHEIGHT*Helper.CARTETILESIZE_MIN);
        carteTopAnchor  = (getHeight()- Helper.CARTEHEIGHT*Helper.CARTETILESIZE)/2;
        carteLeftAnchor = (getWidth()- Helper.CARTEWIDTH*Helper.CARTETILESIZE)/2;
        carte_mLeftAnchor = (getWidth()- Helper.CARTEWIDTH*Helper.CARTETILESIZE_MIN)/2;
        if ((cv_thread!=null) && (!cv_thread.isAlive())) {
            if (cv_thread.getState() == Thread.State.NEW)
        	    cv_thread.start();
        }
    }

    // dessin du gagne si gagne
    private void paintwin(Canvas canvas) {
    	canvas.drawBitmap(win, carteLeftAnchor+ 2*Helper.CARTETILESIZE, carteTopAnchor+ 2*Helper.CARTETILESIZE, null);
    }    
    
    // dessin de la carte du jeu
    private void paintcarte(Canvas canvas) {
    	for (int i=0; i< Helper.CARTEHEIGHT; i++) {
            for (int j=0; j< Helper.CARTEWIDTH; j++) {
                switch (getCarte()[i][j]) {
                    case Helper.CST_ROUGE:
                    	canvas.drawBitmap(rouge, carteLeftAnchor+ j*Helper.CARTETILESIZE, carteTopAnchor+ i*Helper.CARTETILESIZE, null);
                    	break;
                    case Helper.CST_BLEU:
                    	canvas.drawBitmap(bleu,carteLeftAnchor+ j*Helper.CARTETILESIZE, carteTopAnchor+ i*Helper.CARTETILESIZE, null);
                        break;
                }
            }
        }
    }

    // dessin de la carte minateur  du jeu
    private void paintcarte_m(Canvas canvas) {
        for (int i=0; i< Helper.CARTEHEIGHT; i++) {
            for (int j=0; j< Helper.CARTEWIDTH; j++) {
                switch (carte_m[i][j]) {
                    case Helper.CST_ROUGE:
                        canvas.drawBitmap(rougem, carte_mLeftAnchor+ j*Helper.CARTETILESIZE_MIN, carte_mTopAnchor+ i*Helper.CARTETILESIZE_MIN, null);
                        break;
                    case Helper.CST_BLEU:
                        canvas.drawBitmap(bleum,carte_mLeftAnchor+ j*Helper.CARTETILESIZE_MIN, carte_mTopAnchor+ i*Helper.CARTETILESIZE_MIN, null);
                        break;
                }
            }
        }
    }
    

    // permet d'identifier si la partie est gagnee (tous les diamants à leur place)
    public boolean isWon() {

        return Helper.isSame(getCarte(),carte_m);
    }
    
    // dessin du jeu (fond uni, en fonction du jeu gagne ou pas dessin du plateau et du joueur des diamants et des fleches)
    private void nDraw(Canvas canvas) {
		canvas.drawRGB(0,0,0);
    	if (isWon()) {
          //  painttimer(canvas);
            paintcarte(canvas);
        	paintcarte_m(canvas);
        	paintwin(canvas);        	
        } else {
          //  painttimer(canvas);
        	paintcarte(canvas);
            paintcarte_m(canvas);
            //paintPlayer(canvas);

        }    	   	
        
    }
    
    // callback sur le cycle de vie de la surfaceview
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    	initparameters();
    }

    public void surfaceCreated(SurfaceHolder arg0) {
    }

    public void surfaceDestroyed(SurfaceHolder arg0) {
    	in=false;
    }    

    /**
     * run (run du thread cr��)
     * on endort le thread, on modifie le compteur d'animation, on prend la main pour dessiner et on dessine puis on lib�re le canvas
     */
    public void run() {
    	Canvas c = null;

        while (in) {
            try {
                cv_thread.sleep(40);
                //nbtimer++;
               // int_timer.setText(String.valueOf(nbtimer));
               // currentStepZone = (currentStepZone + 1) % maxStepZone;
                try {
                    c = holder.lockCanvas(null);
                    nDraw(c);
                } finally {
                	if (c != null) {
                		holder.unlockCanvasAndPost(c);
                    }
                }
            } catch(Exception e) {
            	Log.e("-> RUN <-", "PB DANS RUN");
            }
        }
    }
    
    // verification que nous sommes dans le tableau
   /* private boolean IsOut(int x, int y) {
        if ((x < 0) || (x > Helper.CARTEWIDTH- 1)) {
            return true;
        }
        if ((y < 0) || (y > Helper.CARTEHEIGHT- 1)) {
            return true;
        }
        return false;
    }*/

    //controle de la valeur d'une cellule
   /* private boolean IsCell(int x, int y, int mask) {
        if (carte[y][x] == mask) {
            return true;
        }
        return false;
    }*/

    // controle si nous avons un diamant dans la case


    // met à jour la position d'un diamant

    // fonction permettant de recuperer les retours clavier

    public boolean onKeyDown(int keyCode, KeyEvent event,int x,int y) {



        int xchange 	= 0;
        int ychange 	= 0;
        if (keyCode == KeyEvent.KEYCODE_0) {


        }
    	
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
           setCarte(Helper.depColTop(getCarte(),y));
           nbmove++;
        	xchange = -1;
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            setCarte(Helper.depColDown(getCarte(),y));
            nbmove++;
        	xchange = 1;
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            setCarte(Helper.depLineLeft(getCarte(),x));
            nbmove++;
            ychange = -1;
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            if (ispause)
                ispause=false;
            setCarte(Helper.depLineRight(getCarte(),x));
            nbmove++;
            ychange = 1;
        }
        this.int_move.setText(String.valueOf(nbmove));

	    return true;   
    }

    // fonction permettant de recuperer les evenements tactiles
    @Override
    public boolean onTouchEvent (MotionEvent event) {
    	return detector.onTouchEvent(event);
    }


    public void getinfo(MotionEvent event,MotionEvent event1 ) {

        if(!ispause) {
            if (!isWon()) {
                float x1 = 0, x2, y1 = 0, y2, dx, dy;
                String direction;
                Float leftclick = event.getX() - carteLeftAnchor;
                Float topclick = event.getY() - carteTopAnchor;
                Log.i("TTTAG", "getinfo: ");
                if (leftclick > 0 && topclick > 0) {
                    Float xx = leftclick / Helper.CARTETILESIZE;
                    Float yy = topclick / Helper.CARTETILESIZE;
                    debutjeux = true;

                    if (xx < Helper.CARTEWIDTH && yy < Helper.CARTEHEIGHT) {
                        x1 = event.getX();
                        y1 = event.getY();
                        Log.i("www", "getinfo: bas ");
                        // onKeyDown(KeyEvent.KEYCODE_DPAD_DOWN, null,yy.intValue(),xx.intValue());
                        //Log.i("www", "getinfo: bas "+xx.intValue()+"ggg"+yy.intValue());
                        x2 = event1.getX();
                        y2 = event1.getY();
                        dx = x2 - x1;
                        dy = y2 - y1;
                        Log.i("www", "getinfo: haut ");
                        // Use dx and dy to determine the direction of the move
                        if (Math.abs(dx) > Math.abs(dy)) {
                            if (dx > 0)
                                onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null, yy.intValue(), xx.intValue());

                            else
                                onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null, yy.intValue(), xx.intValue());

                        } else {
                            if (dy > 0)
                                onKeyDown(KeyEvent.KEYCODE_DPAD_DOWN, null, yy.intValue(), xx.intValue());

                            else
                                onKeyDown(KeyEvent.KEYCODE_DPAD_UP, null, yy.intValue(), xx.intValue());
                        }
                    } else
                        Log.i("", " vous avez cliqué a l'exterieur du carree");
                } else {
                    Log.i("", " vous avez cliqué a l'exterieur du carree");
                }

                if (isWon())//
                {//
                    Score score = new Score();
                    score.setName(DateFormat.format("dd-MM-yyy hh:mm", new Date().getTime()).toString());
                    score.setNbdeplacement(Integer.valueOf(int_move.getText().toString()));
                    score.setTemps(Integer.valueOf(int_timer.getText().toString()));
                    ArrayList<Score> scoreListe = new ScoreDB(mContext).getallScore();
                    for (int bou = 0; bou < scoreListe.size(); bou++) {
                        Score scoredebd = scoreListe.get(bou);
                        if (score.getNbsecdep() < scoredebd.getNbsecdep()) {
                            score.setId(scoredebd.getId());
                            new ScoreDB(mContext).updateScore(score);
                            break;
                        }
                    }
                    niveau++;
                    if (niveau == Helper.ref.length) {
                        niveau = 0;
                    }
                }
            }else{
                nbmove = 0;
                int_move.setText("0");
                int_timer.setText("0");
                loadleve();
                initparameters();
            }
        }
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        if(isWon()){
            nbmove = 0;
            int_move.setText("0");
            int_timer.setText("0");
            loadleve();
            initparameters();
        }
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        getinfo( motionEvent ,motionEvent1);
        return true;
    }


    public boolean isIspause() {
        return this.ispause;
    }

    public void setIspause(boolean ispause) {
        this.ispause = ispause;
    }

    public void settextview(TextView int_move, TextView int_timer) {
        this.int_move=int_move;
        this.int_timer=int_timer;

    }

    public int[][] getCarte() {
        return carte;
    }

    public void setCarte(int[][] carte) {
        this.carte = carte;
    }

    public int[][] getCarte_m() {
        return carte_m;
    }

    public void setCarte_m(int[][] carte_m) {
        this.carte_m=carte_m;
    }

    public void resume() {
        in=true;
        cv_thread = new Thread(this);
        cv_thread.start();

    }
}

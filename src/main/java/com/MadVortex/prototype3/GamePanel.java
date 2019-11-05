package com.nazim2.prototype3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    MainThread thread=null;
    Typeface tp;


    //708
    public static final int WIDTH =708 ;
    public static final int HEIGHT = 354;
    public final int MOVINGSPEED = -5;

    int k=0;
    float myX=350, myY=HEIGHT/2;
    int money=0,mode=0,best=200,xCrash=100,yCrash=200,itemBerserk,solidCount=2,itemMode;
    long smokeStartTime,missileStartTime,berserkStartTime, modeStartTime,fruitStartTime,startReset,StartColision;

   // ArrayList<SmokePuff> smoke=null;
    ArrayList<Missiles> missiles=null;
    ArrayList<Berserk> devilFruit=null;

    Background bg=null;
            Background stars=null;
    Player player=null;

    Player enel=null;
    ironMan ironMan=null;

  //  Bitmap bitmap=null;



    Explosion explosion=null;
    Explosion crash=null;

    boolean reset,disapear,started,newGameCreated;
    boolean modeDisapear=false,modeTag=false,lightning=false,boom=false;

    public GamePanel(Context context) {

        super(context);

        //intercept surfaceHolderevents
        getHolder().addCallback(this);

        //to handle the events we set focusable to be true
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        //reducing Heap allocation
        System.gc();
        if(player!=null) player.sprite.recycle();
        if(explosion!=null)explosion.bitmap.recycle();
        if(crash!=null)crash.bitmap.recycle();
        if(enel!=null)enel.sprite.recycle();
        if(stars!=null)stars.image.recycle();
        if (bg!=null)bg.image.recycle();

        boolean retry = true;
        int counter = 0;

        while (retry && counter < 1000) {
            try {
                counter++;
                thread.running = false;
                thread.join();
                retry = false;
                thread = null;
            } catch (InterruptedException e) {Log.d("Nazim","Game Loop Thread Interruption");}
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {



        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background2),-1,WIDTH);
        stars=new Background(BitmapFactory.decodeResource(getResources(),R.drawable.stars2),-5,WIDTH);
        ironMan=new ironMan(20,20);

        crash =new Explosion(BitmapFactory.decodeResource(getResources(), R.drawable.exp3),xCrash,yCrash, 128,128,4);
        devilFruit = new ArrayList<Berserk>();
         // smoke = new ArrayList<SmokePuff>();
        missiles = new ArrayList<Missiles>();
        missileStartTime = System.nanoTime();
        smokeStartTime = System.nanoTime();
        berserkStartTime=System.nanoTime();
        fruitStartTime = System.nanoTime();


        thread = new MainThread(getHolder(), this);
        thread.running = true;
        thread.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if (event.getAction() == event.ACTION_DOWN ) {

            Log.d("Nazim"," GET Y "+ironMan.getY());
            myX = event.getX();
            myY = event.getY();
            ironMan.positionUpdate(myX, myY);
            Log.d("Nazim", " GET Y after " + ironMan.getY());


            if (!ironMan.isPlaying()&& newGameCreated && reset) {
                ironMan.setPlaying(true);

            }
            if(ironMan.isPlaying() ){
                if(!started) started=true;
                reset=false;

            }
            return true;
        }

        if (event.getAction() == event.ACTION_MOVE) {

            if(mode==0 || mode==2 || mode==3){
                myX = event.getX();
                myY = event.getY();
                ironMan.positionUpdate(myX, myY);
            }
            return true;
        }

        return super.onTouchEvent(event);
    }

    public void update() {

        if (ironMan.isPlaying()) {

          bg.update();
            stars.update();
            ironMan.setMode(mode);
            ironMan.update();

            //intersection with the x point (gettin' bonus)
            if( Rect.intersects(ironMan.getRectangle(),new Rect(15,HEIGHT-85,30,HEIGHT-70)) && itemBerserk>0 && !modeTag )  {

                if(itemMode==1) {
                    player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.mode3),150,150,5,0);
                    mode = 1;
                }

   else if(itemMode==3){

    enel=new Player(BitmapFactory.decodeResource(getResources(), R.drawable.mode1),214,214,10,2);
   mode=3;

  }
                modeStartTime=System.nanoTime();
                modeTag=true;
                modeDisapear=true;
                disapear=true;
                itemBerserk--;
            }

            if(modeTag ){
                if(mode==1) {

                    player.positionUpdate(ironMan.getX(), ironMan.getY());
                    player.update();
                }
                else if (mode==3){

                    enel.positionUpdate(ironMan.getX(),ironMan.getY());
                    enel.update();

                }
                else mode=0;
            }

            long missilesElapsed = (System.nanoTime() - missileStartTime) / 1000000;

            if (missilesElapsed > (1200 - ironMan.getScore()/ 4)) {
                //(int) (Math.random() * (HEIGHT - 40) instead of 20
                //-30+(int)(Math.random() * (HEIGHT-40)
                missiles.add(new Missiles(BitmapFactory.decodeResource(getResources(), R.drawable.met3), WIDTH + 10,-30+(int)(Math.random() * (HEIGHT-40)),186,114, ironMan.getScore(),45));
                missileStartTime = System.nanoTime();
            }

            for (int i = 0; i < missiles.size(); i++) {
                missiles.get(i).update();

            if (Rect.intersects(new Rect(missiles.get(i).getX(),missiles.get(i).getY(),missiles.get(i).getX()+40,missiles.get(i).getY()+40),new Rect(ironMan.getX(),ironMan.getY(),ironMan.getX()+30,ironMan.getY()+30)) && !modeTag){
                    xCrash=missiles.get(i).getX();
                    yCrash=missiles.get(i).getY();

                    crash.animation.setPlayedOnce(false);

                    missiles.remove(i);
                    ironMan.setPlaying(false);
                    break;
                }

                // borders
                if(ironMan.getY()<-35 || ironMan.getY()>HEIGHT-10 || ironMan.getX()<-10 || ironMan.getX() > WIDTH-30){
//                    xCrash=ironMan.getX();
//                    yCrash=ironMan.getY();
//                    crash.animation.setPlayedOnce(false);
//                    boom=true;
//                    StartColision=System.nanoTime();
//                    ironMan.setPlaying(false);
//                    break;

                    if(ironMan.getX()<10 && ironMan.getY()<-35){
                        myX=30;
                        ironMan.setX(30);
                        myY=55;
                        ironMan.setY(50);
                    }
                   if(ironMan.getX()<10){
                       myX=30;
                       ironMan.setX(30);
                   }
                   else if(ironMan.getY()<-35){
                        myY=55;
                       ironMan.setY(50);
                    }

                   else if(ironMan.getY()>HEIGHT-10) {
                       myY=380;
                       ironMan.setY(380);
                   }

                   else if (ironMan.getX()>WIDTH-30){
                       myX=WIDTH;
                       ironMan.setX(WIDTH);
                   }

                    if(ironMan.getX()>WIDTH-30 && ironMan.getY()>HEIGHT-10){
                        myY=380;
                        ironMan.setY(380);
                        myX=WIDTH;
                        ironMan.setX(WIDTH);
                    }
                    if(ironMan.getX()>WIDTH-30 && ironMan.getY()<-35){
                        myY=55;
                        ironMan.setY(50);
                        myX=WIDTH;
                        ironMan.setX(WIDTH);
                    }

                    if(ironMan.getX()< 10&& ironMan.getY()>HEIGHT-10){
                        myY=380;
                        ironMan.setY(380);
                        myX=30;
                        ironMan.setX(30);
                    }

                }
if(Rect.intersects(new Rect(missiles.get(i).getX(),missiles.get(i).getY(),missiles.get(i).getX()+40,missiles.get(i).getY()+50),new Rect(ironMan.getX(),ironMan.getY(),ironMan.getX()+60,ironMan.getY()+60))&& (missiles.get(i).getY()-(int)myY<50) && modeTag){

                    xCrash=missiles.get(i).getX();
                    yCrash=missiles.get(i).getY();
                    crash.animation.setPlayedOnce(false);
                    boom=true;
                    StartColision=System.nanoTime();

                    if(mode==1){
                        solidCount--;
                        ironMan.setScore(ironMan.getScore()+33);
                        if (solidCount==0){
                            modeTag = false;
                            player=null;
                            disapear = false;
                            mode = 0;
                            modeDisapear = false;
                        }
                    }
                    missiles.remove(i);
                    break;
                }

                if(Rect.intersects(new Rect(missiles.get(i).getX(),missiles.get(i).getY(),missiles.get(i).getX()+40,missiles.get(i).getY()+70),new Rect(ironMan.getX(),ironMan.getY(),ironMan.getX()+120,ironMan.getY()+120))&& (missiles.get(i).getY()-(int)myY<50) && modeTag && mode==3){

                    xCrash=missiles.get(i).getX();
                    yCrash=missiles.get(i).getY();
                    crash.animation.setPlayedOnce(false);
                    boom=true;
                    StartColision=System.nanoTime();
                    missiles.remove(i);
                    break;
                }
                else boom=false;

                if (missiles.get(i).getX() < -100) {
                    missiles.remove(i);
                    break;
                }}

             if (boom ){
    crash.XYupdate(xCrash, yCrash);
    crash.update();

}
              long colisionElapsed=(System.nanoTime()-StartColision)/1000000;
             if (colisionElapsed>2500) {

            //Code for Lightning Power
              long fruitelapsed = (System.nanoTime() - fruitStartTime) / 1000000;
             if (fruitelapsed >7421 && !modeDisapear && !modeTag) {
                 Bitmap bitmap;
                 k++;
if(k%2==0) bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.why);
                 else  bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.why2);


                 devilFruit.add(new Berserk(bitmap,WIDTH + 10, (int) (Math.random() * (HEIGHT - 40)), 50, 50));
                 fruitStartTime = System.nanoTime();
             }
             for (int i = 0; i < devilFruit.size(); i++) {
                devilFruit.get(i).update();
                if(Rect.intersects(new Rect(devilFruit.get(i).getX(),devilFruit.get(i).getY(),devilFruit.get(i).getX()+40,devilFruit.get(i).getY()+40),new Rect(ironMan.getX(),ironMan.getY(),ironMan.getX()+60,ironMan.getY()+50))){

                    if(k%2==0){
                     enel=new Player(BitmapFactory.decodeResource(getResources(), R.drawable.mode1),214,214,10,2);
                     modeStartTime = System.nanoTime();
                     modeDisapear = true;
                     disapear = true;
                     mode = 3;
                     modeTag = true;
                     devilFruit.remove(i);
                     break;}
                    else {
                        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.mode3),150,150,5,0);
                        modeStartTime = System.nanoTime();
                        modeDisapear = true;
                        disapear = true;
                        mode = 1;
                        modeTag = true;
                        boom=false;
                        devilFruit.remove(i);
                        break;
                    }
                 }
                 if (devilFruit.get(i).getBy() > HEIGHT-10 || modeTag) devilFruit.remove(i);
             }
            //  Code for the solide power

//              long elapsed = (System.nanoTime() - smokeStartTime) / 1000000;
//             if (elapsed > 50) {
//                // smoke.add(new SmokePuff(ironMan.getX(), ironMan.getY() + 45));
//                 smokeStartTime = 0;
//             }
//             for (int i = 0; i < smoke.size(); i++) {
//
//                 smoke.get(i).update();
//                 if (smoke.get(i).getX() < -10) smoke.remove(i);
//             }
            long modeElapsed = (System.nanoTime() - modeStartTime) / 1000000;
            if (modeElapsed > 10000 && modeTag) {

                fruitStartTime=System.nanoTime();
                disapear = false;
                modeTag = false;
                mode = 0;
                modeDisapear = false;
                solidCount = 2;
            }
             }
        } else {
            ironMan.resetDY();
            if(!reset){
                newGameCreated=false;
                startReset=System.nanoTime();
                reset=true;
                disapear=true;
                modeTag=false;
                solidCount=2;
                modeDisapear=false;
                boom=false;
                mode=0;
                explosion=new Explosion(BitmapFactory.decodeResource(getResources(), R.drawable.exp3),ironMan.getX(),ironMan.getY(),128,128,4);

            }
            explosion.update();

            long resetElapsed=(System.nanoTime()-startReset)/1000000;
            if(resetElapsed>2500 && !newGameCreated) {
                newGame();
            }
        }
    }

    public void newGame(){

        disapear=false;
        modeDisapear=false;
        modeTag=false;
        mode=0;
        if(ironMan.getScore()*3 > best) best=ironMan.getScore()*3;

        money=money+(ironMan.getScore()*3)/10;
        boom=false;
        ironMan.resetDY();
        solidCount=2;
        ironMan.resetScore();
          //smoke.clear();
        missiles.clear();
        devilFruit.clear();

        ironMan.setHeight(HEIGHT / 2);

        newGameCreated=true;
    }

    @Override
    public void draw(Canvas canvas) {

        super.draw(canvas);
        final float xScale = getWidth() / (WIDTH * 1.f);
        final float yScale = getHeight() / (HEIGHT * 1.f);
        if (canvas != null) {
            final int initalState = canvas.save();
            canvas.scale(xScale, yScale);
           bg.draw(canvas);
           stars.draw(canvas);

            if(modeTag && disapear ){
                if(mode==1) player.draw(canvas);

              else if (mode==3){
                  enel.draw(canvas);

              }
                else mode=0;
            }
            if(!disapear )ironMan.draw(canvas, (int)myX, (int)myY);

            for (Missiles m : missiles) m.draw(canvas);
             //  for (SmokePuff sp : smoke) sp.draw(canvas);
            if(!modeDisapear && !modeTag ) {

                 for (Berserk f : devilFruit) f.draw(canvas); ;
            }

        if (boom ) crash.draw(canvas);

            if(started ){
                explosion.draw(canvas);
               // explosion.draw2(canvas,xCrash,yCrash);
            }
             drawText(canvas);
            canvas.restoreToCount(initalState);
        }
    }
    public void drawText(Canvas canvas){

        Paint paint=new Paint();
        paint.setColor(Color.WHITE);
        paint.setTypeface(tp);

        paint.setTextSize(30);
        canvas.drawText(" " + itemBerserk, 30, HEIGHT - 5, paint);
        canvas.drawText("Best: "+best,WIDTH/2,(HEIGHT / 2)-110,paint);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.item_button), 15, HEIGHT - 85, null);
        paint.setTextSize(50);
        canvas.drawText("Score: "+ironMan.getScore()*3, (WIDTH / 2)-12, (HEIGHT / 2) - 141, paint);

//        paint.setTextSize(50);
//        canvas.drawText(""+ironMan.getScore()*3,WIDTH/2,(HEIGHT/2)-110,paint);

        if(!ironMan.isPlaying() && newGameCreated && reset){
            Paint paint1=new Paint();
            paint1.setTextSize(40);
            paint1.setColor(Color.WHITE);
            paint1.setTypeface(tp);
            canvas.drawText("Press To Start", (WIDTH / 2) - 50, HEIGHT / 2, paint1);
        }
    }
}

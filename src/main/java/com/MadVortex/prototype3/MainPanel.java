package com.nazim2.prototype3;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by BaRon45 on 04/02/2016.
 */
public class MainPanel extends SurfaceView implements SurfaceHolder.Callback {

    boolean ThreadTag=true;
    MyThread thread;
    Player player=null;
    float myX=0,myY=0;
    boolean disapear=false;
    int r=0;

   public static int WIDTH=1000,HEIGHT=562;


    Background bg=null;
    boolean lightning =false;

    public MainPanel(Context context){

        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        System.gc();
        if(bg!=null)bg.image.recycle();
        if(player!=null)player.sprite.recycle();
        boolean retry = true;
        int counter = 0;
        while (retry && counter < 1000) {
            try {
                counter++;
                thread.running = false;
                thread.join();
                retry = false;
                thread=null;
            } catch (InterruptedException e) {
            }

        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

//        Bitmap bitmap;
//
//        Random rando = new Random();
//        int random = rando.nextInt(3);
//
//        Log.i("Nazim",""+random);
//        if(random==0) bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.loading2);
//
//        else if (random==1){
//            r=1;
//            bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.loading);
//        }
//        else bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.loading3);

        bg = new Background(BitmapFactory.decodeResource(getResources(),R.drawable.load),0, WIDTH);
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.copy), 289,280,25,0);


        ThreadTag = true;
        thread = new MyThread(getHolder(), this);
        thread.running = true;
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return true;
        }

    public void update(){

if(r==1)player.positionUpdate(300, 300);
else player.positionUpdate(300,314);

        player.update();

    }

     public void draw(Canvas canvas){
         super.draw(canvas);
         final float xScale = getWidth() / (WIDTH * 1.f);
       final float yScale = getHeight() / (HEIGHT * 1.f);

         if(canvas!=null){
             final int initalState = canvas.save();
             canvas.scale(xScale, yScale);
           bg.draw(canvas);

             player.draw(canvas);


             canvas.restoreToCount(initalState);
         }
     }


    }

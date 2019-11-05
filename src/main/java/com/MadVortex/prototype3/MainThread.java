package com.nazim2.prototype3;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by BaRon45 on 06/01/2016.
 */
public class MainThread extends Thread {
    public static  Canvas canvas;
    private int fps=300;
    private GamePanel gamePanel;
    private SurfaceHolder surfaceHolder;
    public boolean running;

    MainThread(SurfaceHolder sHolder,GamePanel gp ){

        super();
      this.gamePanel=gp;
       this.surfaceHolder=sHolder;
    }

    @Override
    public void run(){

        long targetTime=1000/fps;
    long timeMillis;
    long waitTime;
    long startTime;

    while (running){

     startTime=System.nanoTime();
     canvas=null;

       try{

       canvas=this.surfaceHolder.lockCanvas();
        synchronized (surfaceHolder){

            this.gamePanel.update();
            this.gamePanel.draw(canvas);

        }} catch (Exception e){}

        finally {
           if(canvas!=null){
               try {
                   surfaceHolder.unlockCanvasAndPost(canvas);

               }catch (Exception e){}}}

//timeMillis= the time tooked to update the game,we devide by one million to get it in milli seconds
        timeMillis=(System.nanoTime()-startTime)/1000000;
       //how longue till we can goto the loop again
        waitTime=targetTime-timeMillis;
        try{
            this.sleep(waitTime);
        }catch (Exception e){}
    }}}

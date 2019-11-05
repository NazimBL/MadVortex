package com.nazim2.prototype3;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by BaRon45 on 04/02/2016.
 */
public class MyThread extends Thread {

    public static Canvas canvas;


    private MainPanel Panel;
    private SurfaceHolder surfaceHolder;
    public boolean running;

    MyThread(SurfaceHolder sHolder,MainPanel panel ){

        super();
        this.Panel=panel;
        this.surfaceHolder=sHolder;
    }

    @Override
    public void run(){

        //targetTime is the time of each game loop  ==> greter targetTime == slowmotion
        long targetTime=1000/30;
        long timeMillis;

        long waitTime;
        long startTime;


        while (running){

            startTime=System.nanoTime();
            canvas=null;

            try{

                canvas=this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){

                    this.Panel.update();
                    this.Panel.draw(canvas);

                }

            } catch (Exception e){}

            finally {
                if(canvas!=null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);

                    }catch (Exception e){}
                }
            }


//timeMillis= the time tooked to update the game,we devide by one million to get it in milli seconds
            timeMillis=(System.nanoTime()-startTime)/1000000;
            //how longue till we can goto the loop again
            waitTime=targetTime-timeMillis;

            try{
                this.sleep(waitTime);

            }catch (Exception e){}

        }

    }
}

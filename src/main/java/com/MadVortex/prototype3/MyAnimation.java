package com.nazim2.prototype3;

import android.graphics.Bitmap;

/**
 * Created by BaRon45 on 07/01/2016.
 */
public class MyAnimation {

private long starTime;
private Bitmap[] frames;
private int currentFrame;
private long delay;
private boolean playedOnce;

    public void setFrames(Bitmap[] frames) {
        this.frames = frames;
        currentFrame=0;
        starTime=System.nanoTime();
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
    public void setFrames(int i){currentFrame=i;}

    public void update(){

        long elapsed=(System.nanoTime()-starTime)/1000000;
        if(elapsed>delay){
            currentFrame++;
            starTime=System.nanoTime();
        }
    // for animation that do'nt need to be repeated

        if(currentFrame==frames.length)
        {
            currentFrame=0;
            playedOnce=true;
        }

    }

    public Bitmap getImage(){return frames[currentFrame];}

    public boolean isPlayedOnce() {
        return playedOnce;
    }
    public void setPlayedOnce(boolean played){
        playedOnce=played;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }
}

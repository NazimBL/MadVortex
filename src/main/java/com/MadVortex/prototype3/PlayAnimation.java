package com.nazim2.prototype3;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

/**
 * Created by BaRon45 on 04/02/2016.
 */
public class PlayAnimation extends GameObject  {

    Bitmap sprite;
    int row;
    int work=0;
    MyAnimation animation=new MyAnimation();

    public PlayAnimation(Bitmap bitmap,int xx,int yy,int w,int h,int frames,int type) {

        x=xx;
        y=yy;

       height=h;
        width=w;
        sprite=bitmap;
        work=type;

        Bitmap[] image=new Bitmap[frames];
        for(int i=0;i<image.length;i++){
            if(work==0) {
          if(i%3==0 && i>0) row++;
           image[i]=Bitmap.createBitmap(sprite,(i-(row*3))*width,row*height,width,height);
}
else if(work==1) {
    if (i % 2 == 0 && i > 0) row++;
    image[i] = Bitmap.createBitmap(sprite, (i - (row * 2)) * width, row * height, width, height);
}
 else image[i] = Bitmap.createBitmap(sprite, width * i, 0, width, height);

        }
        animation.setFrames(image);
        animation.setDelay(100);

    }

    public void update(){

        if(work==0 || work==1) {
            if (!animation.isPlayedOnce()) animation.update();
        }
        else  animation.update();
    }

    public void positionUpdate(int ex,int way){
        x=ex;
        y=way;
    }

    public void draw(Canvas canvas){

        if(work==0 || work==1) {
            if(!animation.isPlayedOnce())   canvas.drawBitmap(animation.getImage(), x, y, null);
        }
       else   canvas.drawBitmap(animation.getImage(), x, y, null);
    }

    public void setWork(int work){

        this.work=work;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}


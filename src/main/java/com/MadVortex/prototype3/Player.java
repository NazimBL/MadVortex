package com.nazim2.prototype3;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Player extends GameObject  {

    Bitmap sprite;
    int row=0;
    int shape=0;
    int modulo=5;
 MyAnimation animation=new MyAnimation();


  public Player(Bitmap bitmap,int w,int h,int numFrames,int code){


      this.shape=code;
      if (code==0 ) modulo=5;
      else modulo=8;

      if(numFrames==25) {
          x = 220;
          y = 50;
      }

      height=h;
      width=w;

     sprite=bitmap;

   Bitmap[] image=new Bitmap[numFrames];

     int j=0;
      for(int i=0;i<numFrames;i++)
      {
//
//    if (i % modulo == 0) {
//        row++;
//        j = 0;
//    }
//    image[i] = Bitmap.createBitmap(sprite, j * width, height * (row - 1), width, height);
//
//    j++;
          image[i]=Bitmap.createBitmap(sprite,i*width,0,width,height);

 }
      animation.setFrames(image);
      animation.setDelay(100);

  }

    public void update(){

        animation.update();
    }
public void draw(Canvas canvas){

    canvas.drawBitmap(animation.getImage(),x,y,null);
}

    public void positionUpdate(int xx,int yy){
        x=(xx-20);
        y=(yy-20);
    }

}

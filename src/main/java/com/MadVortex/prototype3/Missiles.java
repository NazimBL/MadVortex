package com.nazim2.prototype3;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

/**
 * Created by BaRon45 on 14/01/2016.
 */
public class Missiles extends GameObject {

private int score;
 private int speed;
    int row=0;
    private MyAnimation animation=new MyAnimation();

     Bitmap bitmap;

    public Missiles(Bitmap res,int x,int y,int w,int h,int s,int frameNum){

        super.x=x;
        super.y=y;

        height=h;
        width=w;

        score=s;

        speed=12+(int) (Math.random()*score/10);


        bitmap=res;
      //  Bitmap scaled=Bitmap.createScaledBitmap(bitmap,2700,129,false);

        Bitmap[] images=new Bitmap[frameNum];
         int j=0;
        for(int i=0;i<15;i++)
        {

            images[i]=Bitmap.createBitmap(bitmap,i*width,0,width,height);
        }

        animation.setFrames(images);
        animation.setDelay(200);

    }

    public void update(){

        x-=speed;
        animation.update();
    }
public void draw(Canvas canvas){

//   Paint paint=new Paint();
//    paint.setColor(Color.YELLOW);
//    paint.setStyle(Paint.Style.FILL);
//    canvas.drawCircle(x,y,20,paint);

canvas.drawBitmap(animation.getImage(),x,y,null);


}

    public int getWidth(){

        //to make thecollision more realistic
        return width-10;
    }

}

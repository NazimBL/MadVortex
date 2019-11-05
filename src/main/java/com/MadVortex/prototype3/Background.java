package com.nazim2.prototype3;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/*
 * Created by BaRon45 on 06/01/2016.
 */
public class Background {

 Bitmap image;
private int x,y=0,dx,w;
    Paint paint=new Paint();

    public Background(Bitmap bitmap,int speed,int width){

        image=bitmap;

        dx=speed;
        w=width;

    }

    public  void update(){

        x+=dx;
        if(x<-1629) x=0;
    }


    public void draw(Canvas canvas){

        canvas.drawBitmap(image,x,y,paint);
        if(x<0 ){

            canvas.drawBitmap(image,x-w+1692,y,paint);
        }
    }

}

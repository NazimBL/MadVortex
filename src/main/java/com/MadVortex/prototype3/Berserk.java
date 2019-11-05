package com.nazim2.prototype3;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by BaRon45 on 30/01/2016.
 */
public class Berserk extends GameObject {

int speed,couleur;
 Paint paint=new Paint();
    Bitmap sprite;

public Berserk(Bitmap bitmap,int x,int y,int w,int h)
{

    super.x=x;
    super.y=y;

    width=w;
    height=h;

    speed=7;
sprite=bitmap;
    paint.setStyle(Paint.Style.FILL);
    paint.setColor(Color.CYAN);
    paint.setShadowLayer(60, 0, 0, Color.RED);


}
    public void update(){
        x-=speed;
    }
    public void draw(Canvas canvas){

   //    canvas.drawCircle(x,y,15,paint);
canvas.drawBitmap(sprite,x,y,null);

    }

    public int getBy(){
        return y;
    }

    public int getX(){return x;}
}

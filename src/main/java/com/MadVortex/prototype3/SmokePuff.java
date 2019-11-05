package com.nazim2.prototype3;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.SweepGradient;

/**
 * Created by BaRon45 on 09/01/2016.
 */
public class SmokePuff extends GameObject {

  //  public int r;
public SmokePuff(int x,int y){

  //  r=5;
    super.x=x;
    super.y=y;
}

    //distance between every paint
    public void update(){
        x-=5;
    }
 public void draw(Canvas canvas){

     Paint paint=new Paint();
     paint.setColor(Color.WHITE);
     paint.setStyle(Paint.Style.FILL);
     paint.setShader(new LinearGradient(x + 6, y - 38, x + 15, y +2, Color.WHITE,Color.LTGRAY, Shader.TileMode.CLAMP));


    //canvas.drawRect(new Rect(x+6,y-2,x+25,y+7),paint);
     canvas.drawRect(new Rect(x + 6, y - 2, x + 25, y + 7), paint);
//     canvas.drawCircle(x-r,y-r,r,paint);
//     canvas.drawCircle(x-r+2,y-r-2,r,paint);
//     canvas.drawCircle(x-r+4,y-r+1,r,paint);
}
}

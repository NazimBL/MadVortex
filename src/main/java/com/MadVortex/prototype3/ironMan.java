package com.nazim2.prototype3;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Shader;
import android.util.Log;


public class ironMan extends GameObject {


    private long startTime;
    private int score;
    private boolean playing;
    int mode=0;
    Bitmap bitmap;
    Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint tail=new Paint();


    public ironMan(int w,int h){

//start position
    x = 20;
    y = GamePanel.HEIGHT / 2;


        height = h;
        width = w;

        //to make a mirror image
//        shadow.setShadowLayer(200, 50, 50, Color.RED);
//        shadow.setStyle(Paint.Style.STROKE);
//        shadow.setColor(Color.WHITE);

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setShadowLayer(20, 0, 0, Color.CYAN);


        tail.setColor(Color.WHITE);
        tail.setStyle(Paint.Style.STROKE);
        tail.setStrokeWidth(8);
        tail.setAntiAlias(true);


        LinearGradient gradient = new LinearGradient(30, 0, 0, 0,
                new int[] {Color.RED,Color.CYAN,Color.WHITE}, null, Shader.TileMode.CLAMP);
        tail.setShader(gradient);
        PathEffect cornerEffect = new CornerPathEffect(10);
        tail.setPathEffect(cornerEffect);

        score = 0;
    }

public void setMode(int modo){
    mode=modo;
}

    public void update() {

        int time;
        if(mode==0)time=300;
        else time=100;

        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if (elapsed > time) {
            score++;
            startTime = System.nanoTime();
        }
    }

    public void draw(Canvas canvas,int myX,int myY){

//        Path path=new Path();
//        path.moveTo(myX-150,myY-38);
//        path.lineTo(myX-15,myY-38);
//        canvas.drawPath(path,tail);
        canvas.drawCircle(myX-10,myY-40,15,paint);


    }

    public void positionUpdate(float xx,float yy){

        //50,90
        x=((int)xx-50);
        y=((int)yy-90);
    }

    public void setX(int x){
        this.x=x-50;
    }

    public void setY(int y){
        this.y=y-90;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int scogh){
        score=scogh;
    }
    public boolean isPlaying() {
        return playing;
    }
    public void resetDY(){dy=0;}
    public void resetScore(){score=0;}
    public void setPlaying(boolean playing) {
        this.playing = playing;
    }


}

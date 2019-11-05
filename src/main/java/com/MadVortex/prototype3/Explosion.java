package com.nazim2.prototype3;


import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Explosion {

  private int x;
  private int y;
  private int width;
    private int height;
     Bitmap bitmap;
    public MyAnimation animation=new MyAnimation();
    private int row;

 public Explosion(Bitmap res,int x,int y,int w,int h,int frameNum){
     this.x=x;
     this.y=y;
     this.width=w;
     this.height=h;


     Bitmap[] image=new Bitmap[frameNum];
     bitmap=res;

     for(int i=0;i<image.length;i++){

         if(i%2==0 && i>0) row++;
         image[i]=Bitmap.createBitmap(bitmap,(i-(row*2))*width,row*height,width,height);

     }
     animation.setFrames(image);
     animation.setDelay(200);
 }

 public void XYupdate(int xx , int yy){
     this.x=xx;
     this.y=yy;
 }

   public void update(){

       if(!animation.isPlayedOnce()) animation.update();
}

    public void draw(Canvas canvas){

        if(!animation.isPlayedOnce()) canvas.drawBitmap(animation.getImage(), x, y, null);
    }
    public void draw2(Canvas canvas,int xx,int yy){

        if(!animation.isPlayedOnce()) canvas.drawBitmap(animation.getImage(), xx, yy, null);
    }

    public int getHeight() {
        return height;
    }
}

package com.nazim2.prototype3;

import android.graphics.Rect;


public abstract class GameObject {

protected int x;
    protected int y;
    protected int dx;
    protected int dy;
    protected int width;
    protected int height;

public int getX(){
    return x;
}

public void setX(int value){
    this.x=value;
}

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {

        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getDy() {

        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getY(){

    return  y;
}
public void setY(int value){
    this.y=value;
}

public Rect getRectangle(){

    return  new Rect(x,y,x+width,y+height);
}

}

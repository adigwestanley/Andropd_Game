package com.example.ruan_lopes.fluky.actor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Ruan_Souza on 15-03-26.
 */
public class Character {

    public Bitmap image;
    public Point initial, current;

    public int halfWidth;
    public int halfHeight;

    public Boolean isVisible;
    Boolean selected;
    public Rect r;

    public Character(Context c, Point point, int drawable)
    {
        this.image = BitmapFactory.decodeResource(c.getResources(), drawable);
        this.initial = new Point(point.x, point.y);
        this.current = new Point(point.x, point.y);

        isVisible = true;
        this.halfHeight = image.getHeight() / 2;
        this.halfWidth = image.getWidth() / 2;

        r = new Rect(this.getLeft(), this.getTop(), this.getLeft() + this.getImageWidth(),this.getTop() + this.getImageHeight());
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Rect getRectangle()
    {
        return r;
    }

    public void resetRectangle()
    {
        r.set(this.getLeft(), this.getTop(), this.getLeft() + this.getImageWidth(),this.getTop() + this.getImageHeight());
    }

    public int getImageWidth() {
        return this.getImage().getWidth();
    }

    public int getImageHeight() {
        return this.getImage().getHeight();
    }

    public void moveTo(int x, int y)
    {
        setLeft(x);
        setTop(y);
        resetRectangle();
    }

    public void setTop(int y)
    {
        this.current.y = y;
    }

    public void setLeft(int x)
    {
        this.current.x = x;
    }

    public int getTop()
    {
        return this.current.y;
    }

    public int getLeft()
    {
        return this.current.x;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Point getInitial() {
        return initial;
    }

    public void setInitial(Point initial) {
        this.initial = initial;
    }

    public Point getCurrent() {
        return current;
    }

    public void setCurrent(Point current) {
        this.current = current;
    }

    public int getHalfWidth() {
        return halfWidth;
    }

    public void setHalfWidth(int halfWidth) {
        this.halfWidth = halfWidth;
    }

    public int getHalfHeight() {
        return halfHeight;
    }

    public void setHalfHeight(int halfHeight) {
        this.halfHeight = halfHeight;
    }

    public void setVisible(Boolean bVisible)
    {
        isVisible = bVisible;
    }

    public Boolean getVisible()
    {
        return isVisible;
    }

}

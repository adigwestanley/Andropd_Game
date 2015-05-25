package com.example.ruan_lopes.fluky.actor;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

/**
 * Created by restroom3 on 15-03-16.
 */
public class Bug extends Character{

    DisplayMetrics dm;
    int numberBug;

    public Bug(Context c, Point point, int drawable, DisplayMetrics dm, int number)
    {
          super(c, point, drawable);

          this.dm = dm;
          this.numberBug = number;
    }

    public void update(){

        if(this.getTop() >= dm.heightPixels + this.getImageHeight())
        {
           //reset position
            this.moveTo(this.getInitial().x, this.getInitial().y);
        }
        else
        {
            this.setTop(this.getTop() + 2);
        }

        this.resetRectangle();

    }

    public int getLetter() {
        return numberBug;
    }

    public void setNumberBug(int numberBug) {
        this.numberBug = numberBug;
    }
}

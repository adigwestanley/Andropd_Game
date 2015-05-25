package com.example.ruan_lopes.fluky.actor;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

/**
 * Created by restroom3 on 15-03-16.
 */
public class DonnutWords extends Character{

    DisplayMetrics dm;
    String letterBug;

    public DonnutWords(Context c, Point point, int drawable, DisplayMetrics dm, String letter)
    {
          super(c, point, drawable);

          this.dm = dm;
          this.letterBug = letter;
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

    public String getLetter() {
        return letterBug;
    }

    public void setLetter(String letter) {
        this.letterBug = letter;
    }
}

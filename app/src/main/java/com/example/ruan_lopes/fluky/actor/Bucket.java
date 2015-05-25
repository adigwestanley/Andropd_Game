package com.example.ruan_lopes.fluky.actor;

import android.content.Context;
import android.graphics.Point;

/**
 * Created by restroom3 on 15-03-16.
 */
public class Bucket extends Character{

    public Context c;
    String nameBucket;

    public Bucket(Context c, Point point, int drawable, String name)
    {
        super(c, point, drawable);
        this.c = c;
        this.nameBucket = name;
    }

    public String getNameBucket() {
        return nameBucket;
    }

    public void setNameBucket(String nameBucket) {
        this.nameBucket = nameBucket;
    }
}

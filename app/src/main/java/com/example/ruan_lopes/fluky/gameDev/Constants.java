package com.example.ruan_lopes.fluky.gameDev;

import com.example.ruan_lopes.fluky.R;

/**
 * Created by restroom3 on 15-03-17.
 */
public class Constants {

    public final static String DONUT_FONT = "PWYummyDonuts.ttf";
    public static final String SHAREDPREF = "GameSettings";
    public static final String SETTINGS_ENEMY = "enemies";
    public static final int SETTINGS_ONE = 1;
    public static boolean killMe = false;
    public static int mTime = 30;
    public static int mScore = 0;
    public static int mLife = 5;
    public static int mLevelStage = 20; //set by sql
    public static String mWordStage = "apple";
    public static String mWord = "";


    public static int setImageBug(int i) {

        switch (i) {
            case 0:
                return R.drawable.d1;
            case 1:
                return R.drawable.d2;
            case 2:
                return R.drawable.d1_bitten;
            case 3:
                return R.drawable.d2_bitten;


        }
        return 4;
    }


    public static String setName(int i) {

        switch (i) {
            case 0:
                return "white";
            case 1:
                return "red";
            case 2:
                return "blue";
            case 3:
                return "green";


        }
        return null;
    }



}
package com.example.ruan_lopes.fluky.gameDev.WordsGame;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.ruan_lopes.fluky.R;
import com.example.ruan_lopes.fluky.actor.Bucket;
import com.example.ruan_lopes.fluky.actor.DonnutWords;
import com.example.ruan_lopes.fluky.gameDev.Constants;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by restroom3 on 15-03-16.
 */
public class DragWordsView extends View{

    Paint paint;
    public Bucket mBucket;
    public Bucket mBucketRed;
    public Bucket mBucketBlue;
    public Bucket mBucketGreen;
    public DonnutWords touchedDonnutWords;
    public DonnutWords differentDonnutWords;
    public Constants mConstants = new Constants();

    String ramdomLetter;
    int ramdomImage;

    public DisplayMetrics metrics;
    public Context mContext;
    private SharedPreferences gameSettings;
    int numEnemies;
    ArrayList<DonnutWords> DonnutWordsList;
    Handler mHandler;

    public DragWordsView(Context context, DisplayMetrics dm)
    {
        super(context);
        mContext = context;

        metrics = dm;
        this.gameSettings = mContext.getSharedPreferences(Constants.SHAREDPREF, mContext.MODE_PRIVATE);
        numEnemies = gameSettings.getInt(Constants.SETTINGS_ENEMY, Constants.SETTINGS_ONE);

        DonnutWordsList = new ArrayList<DonnutWords>();
        Random rand = new Random();

// Creating TEXT that is gonna appear on the donnuts
        paint= new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        paint.setStyle(Paint.Style.FILL);
        paint.setShadowLayer(10f, 10f, 10f, Color.BLACK);

// CREATING BUCKETS --------------------------------------------

        Point pBucket = new Point(0,0);
        mBucket = new Bucket(context, pBucket, R.drawable.stroke_1_bucket, "white");
        mBucket.moveTo(10, dm.heightPixels - (int) (dm.heightPixels * .2));

        Point pBucketRed = new Point(0,0);
        mBucketRed = new Bucket(context, pBucketRed, R.drawable.stroke_2_bucket, "red");
       // mBucketRed.moveTo(mBucketRed.getImage().getWidth() +40 , dm.heightPixels - (int) (dm.heightPixels * .2));
        mBucketRed.moveTo(mBucket.getCurrent().x + (mBucketRed.getImage().getWidth() + mBucketRed.getHalfWidth()), dm.heightPixels - (int) (dm.heightPixels * .2));


// END ----------------------------------------------------------

// CREATING DonnutWordsS --------------------------------------------

        Point pDonnutWords;
        DonnutWords newDonnutWords;

        for( int i = 0 ; i < 4 ; i++){

          int ramdomLoc = rand.nextInt(dm.widthPixels);
          ramdomImage = rand.nextInt(4);
          ramdomLetter = RandomStringUtils.randomAlphabetic(1);


            pDonnutWords = new Point(ramdomLoc,rand.nextInt(dm.heightPixels) - dm.heightPixels);

            DonnutWordsList.add(new DonnutWords(context, pDonnutWords, Constants.setImageBug(ramdomImage), metrics,ramdomLetter));



        invalidate();

        mHandler = new Handler();
        this.updateRunnable.run();


        }

    }

 // END ----------------------------------------------------------


    @Override
    protected void onDraw(Canvas canvas)
    {

        for (DonnutWords DonnutWords : DonnutWordsList) {
            if(DonnutWords.getVisible())
                canvas.drawBitmap(DonnutWords.getImage(), DonnutWords.getLeft(), DonnutWords.getTop(), null);
                canvas.drawText(DonnutWords.getLetter(), DonnutWords.getLeft(), DonnutWords.getTop(), paint);
        }//DonnutWords.getLetter()

        canvas.drawBitmap(mBucket.getImage(), mBucket.getCurrent().x, mBucket.getCurrent().y, null);
        canvas.drawBitmap(mBucketRed.getImage(), mBucketRed.getCurrent().x, mBucketRed.getCurrent().y, null);

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch(action)
        {
            case MotionEvent.ACTION_DOWN:

                touchedDonnutWords = didTouchDonnutWords(x, y);

                if(touchedDonnutWords != null) {

                    touchedDonnutWords.setSelected(true);
                    Toast t = Toast.makeText(mContext, "Selected", Toast.LENGTH_SHORT);
                    t.show();
                }

                break;
            case MotionEvent.ACTION_MOVE:


                if(touchedDonnutWords != null && touchedDonnutWords.getSelected()) {

                    int resultX = x - touchedDonnutWords.getHalfWidth();
                    int resultY = y - touchedDonnutWords.getHalfHeight();

                    if (x > 100 && (x ) < metrics.widthPixels - touchedDonnutWords.getImageWidth()  ){
                        touchedDonnutWords.moveTo(resultX, touchedDonnutWords.getTop());
                    }
                }

                break;

        }

        invalidate();
        return true;
    }

    public DonnutWords didTouchDonnutWords(int x, int y) {

        for (DonnutWords DonnutWords : DonnutWordsList) {

            if (DonnutWords.getLeft() < x && DonnutWords.getLeft() + DonnutWords.getImageWidth() > x &&
                    DonnutWords.getTop() < y && DonnutWords.getTop() + DonnutWords.getImageHeight() > y &&
                    DonnutWords.getVisible()) {

                return DonnutWords;

            }
        }

        return null;
    }



    protected Runnable updateRunnable = new Runnable(){
        public void run(){

            for(int i = 0; i < DonnutWordsList.size(); i++) {
                DonnutWordsList.get(i).update();
            }

            if(Constants.killMe != true) {

                mHandler.postDelayed(updateRunnable,10);


            } else {
                mHandler.removeCallbacks(updateRunnable);
            }



        //collision - DonnutWords to Bucket
            for(int i = 0; i < DonnutWordsList.size(); i++)
            {
                differentDonnutWords = DonnutWordsList.get(i);

                if(mBucket.getRectangle().intersect(differentDonnutWords.getRectangle())) {

                    if(Constants.mWordStage.indexOf(differentDonnutWords.getLetter()) >= 0) {

                        Constants.mWord += differentDonnutWords.getLetter();
                        DonnutWordsList.get(i).moveTo(DonnutWordsList.get(i).getInitial().x, DonnutWordsList.get(i).getInitial().y);
                    } else {
                        missedScore();
                        DonnutWordsList.get(i).setLetter(RandomStringUtils.randomAlphabetic(1));
                        DonnutWordsList.get(i).moveTo(DonnutWordsList.get(i).getInitial().x, DonnutWordsList.get(i).getInitial().y);
                    }

                } else if (mBucketRed.getRectangle().intersect(DonnutWordsList.get(i).getRectangle())) {


                    if(Constants.mWordStage.indexOf(differentDonnutWords.getLetter()) >= 0) {

                        Constants.mWord += differentDonnutWords.getLetter();
                        DonnutWordsList.get(i).moveTo(DonnutWordsList.get(i).getInitial().x, DonnutWordsList.get(i).getInitial().y);
                    } else {
                        missedScore();
                        DonnutWordsList.get(i).setLetter(RandomStringUtils.randomAlphabetic(1));
                        DonnutWordsList.get(i).moveTo(DonnutWordsList.get(i).getInitial().x, DonnutWordsList.get(i).getInitial().y);
                    }
                }
            }

        }
    };

    public void setScore(String letter) {

        String currentScore = Constants.mWord;

        Constants.mWord += letter;

    }


    public void missedScore() {


        int currentScore = Constants.mLife;
        Constants.mLife = currentScore - 1;


    }

    public String getCurrentProgress() {

        String progress = "";
        for (char letter : Constants.mWordStage.toCharArray()) {
            char display = '-';
            if (Constants.mWord.indexOf(letter) >= 0 ){
                display = letter;
            }

            progress += display;
        }

        return progress;
    }



}
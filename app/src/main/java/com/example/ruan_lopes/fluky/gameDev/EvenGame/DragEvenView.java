package com.example.ruan_lopes.fluky.gameDev.EvenGame;

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

import com.example.ruan_lopes.fluky.actor.Bucket;
import com.example.ruan_lopes.fluky.actor.Bug;
import com.example.ruan_lopes.fluky.gameDev.Constants;
import com.example.ruan_lopes.fluky.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by restroom3 on 15-03-16.
 */
public class DragEvenView extends View{

    Paint paint;
    public Bucket mBucket;
    public Bucket mBucketRed;
    public Bucket mBucketBlue;
    public Bucket mBucketGreen;
    public Bug touchedBug;
    public Constants mConstants = new Constants();

    int ramdomNumber;
    int ramdomImage;

    public DisplayMetrics metrics;
    public Context mContext;
    private SharedPreferences gameSettings;
    int numEnemies;
    ArrayList<Bug> bugList;
    Handler mHandler;

    public DragEvenView(Context context, DisplayMetrics dm)
    {
        super(context);
        mContext = context;

        metrics = dm;
        this.gameSettings = mContext.getSharedPreferences(Constants.SHAREDPREF, mContext.MODE_PRIVATE);
        numEnemies = gameSettings.getInt(Constants.SETTINGS_ENEMY, Constants.SETTINGS_ONE);

        bugList = new ArrayList<Bug>();
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

// CREATING BUGS --------------------------------------------

        Point pBug;
        Bug newBug;

        for( int i = 0 ; i < 5 ; i++){

          int ramdomLoc = rand.nextInt(dm.widthPixels);
          ramdomImage = rand.nextInt(4);
          ramdomNumber = rand.nextInt(Constants.mLevelStage);


            pBug = new Point(ramdomLoc,rand.nextInt(dm.heightPixels) - dm.heightPixels);

            bugList.add(new Bug(context, pBug, Constants.setImageBug(ramdomImage), metrics, ramdomNumber));



        invalidate();

        mHandler = new Handler();
        this.updateRunnable.run();


        }

    }

 // END ----------------------------------------------------------


    @Override
    protected void onDraw(Canvas canvas)
    {

        for (Bug bug : bugList) {
            if(bug.getVisible())
                canvas.drawBitmap(bug.getImage(), bug.getLeft(), bug.getTop(), null);
                canvas.drawText(Integer.toString(bug.getLetter()), bug.getLeft(), bug.getTop(), paint);
        }

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

                touchedBug = didTouchBug(x, y);

                if(touchedBug != null) {

                    touchedBug.setSelected(true);
                    Toast t = Toast.makeText(mContext, "Selected", Toast.LENGTH_SHORT);
                    t.show();
                }

                break;
            case MotionEvent.ACTION_MOVE:


                if(touchedBug != null && touchedBug.getSelected()) {

                    int resultX = x - touchedBug.getHalfWidth();
                    int resultY = y - touchedBug.getHalfHeight();

                    if (x > 100 && (x ) < metrics.widthPixels - touchedBug.getImageWidth()  ){
                        touchedBug.moveTo(resultX, touchedBug.getTop());
                    }
                }

                break;

        }

        invalidate();
        return true;
    }

    public Bug didTouchBug(int x, int y) {

        for (Bug bug : bugList) {

            if (bug.getLeft() < x && bug.getLeft() + bug.getImageWidth() > x &&
                    bug.getTop() < y && bug.getTop() + bug.getImageHeight() > y &&
                    bug.getVisible()) {

                return bug;

            }
        }

        return null;
    }



    protected Runnable updateRunnable = new Runnable(){
        public void run(){

            for(int i = 0; i < bugList.size(); i++) {
                bugList.get(i).update();
            }

            if(Constants.killMe != true) {

                mHandler.postDelayed(updateRunnable,10);


            } else {
                mHandler.removeCallbacks(updateRunnable);
            }



        //collision - bug to Bucket
            for(int i = 0; i < bugList.size(); i++)
            {
                Bug differentBug = bugList.get(i);

                if(mBucket.getRectangle().intersect(differentBug.getRectangle())) {

                    if((differentBug.getLetter() % 2) == 0) {

                        setScore(1);
                        bugList.get(i).moveTo(bugList.get(i).getInitial().x, bugList.get(i).getInitial().y);
                    } else {
                        missedScore();
                        bugList.get(i).moveTo(bugList.get(i).getInitial().x, bugList.get(i).getInitial().y);
                    }

                } else if (mBucketRed.getRectangle().intersect(bugList.get(i).getRectangle())) {



                    if((differentBug.getLetter() % 2) != 0) {

                        setScore(1);
                        bugList.get(i).moveTo(bugList.get(i).getInitial().x, bugList.get(i).getInitial().y);
                    } else {
                        missedScore();
                        bugList.get(i).moveTo(bugList.get(i).getInitial().x, bugList.get(i).getInitial().y);
                    }

                }
            }

        }
    };

    public void setScore(int newValue) {

        int currentScore = Constants.mScore;

        int result = currentScore + newValue;
        Constants.mScore = result;
    }


    public void missedScore() {


        int currentScore = Constants.mLife;
        Constants.mLife = currentScore - 1;


    }


}

    /*public void updateScore() {



        if(touchedBug.getSelected()) {

            setScore(1);

        } else if (touchedBug.getSelected() != true) {

            missedScore();

        }
    }
*/
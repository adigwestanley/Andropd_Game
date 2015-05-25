package com.example.ruan_lopes.fluky.gameDev.WordsGame;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ruan_lopes.fluky.gameDev.WordsGame.AlertDialogWordy;
import com.example.ruan_lopes.fluky.R;
import com.example.ruan_lopes.fluky.gameDev.Constants;

public class GameWordyActivity extends Activity {

    public Constants mConstants = new Constants();

    TextView timeValue, scoreValue, lifeValue;
    RelativeLayout rel;

    private Handler mHandler;

    DragWordsView dv;
    DisplayMetrics metrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setFlags(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION,View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_game_screen);

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        timeValue = (TextView) findViewById(R.id.timeValue);
        scoreValue = (TextView) findViewById(R.id.scoreValue);
        lifeValue = (TextView) findViewById(R.id.lifeValue);
        rel = (RelativeLayout) findViewById(R.id.drag_view_layout);

        //create dragview and link it
        dv = new DragWordsView(getApplicationContext(), metrics);
        rel.addView(dv);

        timeValue.setText(Integer.toString(Constants.mTime));
        scoreValue.setText(dv.getCurrentProgress());
        mHandler = new Handler();
        this.updateTimeRunnable.run();


    }

    public Runnable updateTimeRunnable = new Runnable(){
        //count down time

        public void run() {

            if (Constants.killMe != true) {

                Constants.mTime--;

                if (Constants.mTime > 0) {
                    timeValue.setText(Integer.toString(Constants.mTime));
                } else if (Constants.mTime < 0) {

                    alertUser("Your time is up!");
                    Constants.killMe = true;
                }

                scoreValue.setText(dv.getCurrentProgress());

                if (Constants.mLife > 0) {
                    lifeValue.setText(Integer.toString(Constants.mLife));
                } else if (Constants.mLife <= 0) {

                    lifeValue.setText("0");
                    alertUser("You dont have more life!");
                    Constants.killMe = true;
                }

                mHandler.postDelayed(updateTimeRunnable, 1000);
            }
        }
    };

    private void alertUser(String message) {
        Bundle messageArgs = new Bundle();
        messageArgs.putString(AlertDialogWordy.TITLE_ID, "You Lost!");
        messageArgs.putString(AlertDialogWordy.MESSAGE_ID, message);

        AlertDialogWordy dialog = new AlertDialogWordy();
        dialog.setArguments(messageArgs);
        dialog.show(getFragmentManager(), "error_dialog");
    }
}

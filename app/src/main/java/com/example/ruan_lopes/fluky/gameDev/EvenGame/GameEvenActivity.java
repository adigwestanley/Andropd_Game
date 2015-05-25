package com.example.ruan_lopes.fluky.gameDev.EvenGame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ruan_lopes.fluky.R;
import com.example.ruan_lopes.fluky.gameDev.Constants;
import com.example.ruan_lopes.fluky.gameDev.WordsGame.AlertDialogWordy;


public class GameEvenActivity extends Activity {

    public Constants mConstants = new Constants();

    TextView timeValue, scoreValue, lifeValue;
    RelativeLayout rel;

    private Handler mHandler;

    DragEvenView dv;
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
        dv = new DragEvenView(getApplicationContext(), metrics);
        rel.addView(dv);

        timeValue.setText(Integer.toString(Constants.mTime));
        scoreValue.setText(Integer.toString(Constants.mScore));
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


                scoreValue.setText(Integer.toString(Constants.mScore));

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


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                mHandler.removeCallbacks(updateTimeRunnable);

                finish();
            }
        });
        builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }

}

package com.example.ruan_lopes.fluky.gameDev;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruan_lopes.fluky.R;


public class SettingsActivity extends Activity {

    TextView tvBack;
    private SharedPreferences gameSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);

        tvBack = (TextView) findViewById(R.id.back_button);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                //onBackPressed();

            }
        });

        this.gameSettings = getSharedPreferences(Constants.SHAREDPREF, MODE_PRIVATE);
    }

    public void createMultiple(View v)
    {
        final SharedPreferences.Editor ed = gameSettings.edit();
        ed.putInt(Constants.SETTINGS_ENEMY, 5);
        ed.commit();

        Toast t = Toast.makeText(getApplicationContext(), "Multiple Enemies", Toast.LENGTH_LONG);
        t.show();
    }

    public void createSingle(View v)
    {
        final SharedPreferences.Editor ed = gameSettings.edit();
        ed.putInt(Constants.SETTINGS_ENEMY, 1);
        ed.commit();

        Toast t = Toast.makeText(getApplicationContext(), "Single Enemy", Toast.LENGTH_LONG);
        t.show();
    }





}

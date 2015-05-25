package com.example.ruan_lopes.fluky.gameDev;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ruan_lopes.fluky.R;


public class GameOverActivity extends ActionBarActivity {

    TextView lifeResult, scoreResult;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);


        lifeResult = (TextView) findViewById(R.id.lifeResult);
        lifeResult.setText(Integer.toString(Constants.mLife));

        scoreResult = (TextView) findViewById(R.id.scoreResult);
        scoreResult.setText(Integer.toString(Constants.mScore));

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constants.mTime = 30;
                Constants.mScore = 0;
                Constants.mLife = 5;
                Constants.killMe = false;

                Intent intent = new Intent(GameOverActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_over, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

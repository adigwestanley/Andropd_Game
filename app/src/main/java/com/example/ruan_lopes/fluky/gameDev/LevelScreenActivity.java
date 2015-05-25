package com.example.ruan_lopes.fluky.gameDev;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruan_lopes.fluky.actor.Level;
import com.example.ruan_lopes.fluky.R;
import com.example.ruan_lopes.fluky.gameDev.EvenGame.GameEvenActivity;
import com.example.ruan_lopes.fluky.gameDev.WordsGame.GameWordyActivity;

import java.util.ArrayList;

public class LevelScreenActivity extends Activity implements OnClickListener{
	
	
	DatabaseHandler db;
	private ImageButton go;
	private LinearLayout levelLayout;
	
	private Typeface font;
	String	word ;
	TextView textView1;
	ArrayList<Level> levels;
	int selectedLevel;
	int numMoves;
	public LinearLayout adHolder;

    public int currentType = 0;


    ImageButton buttonOdd;
    ImageButton buttonWord;
    ImageButton buttonMult;

    TextView wordText;
    TextView oddText;
    TextView multText;
	
	//private SoundFX sound;
	 
		@Override
		 protected void onCreate(Bundle savedInstanceState) {
			 super.onCreate(savedInstanceState);
		     setContentView(R.layout.layout_levelscreen_new);
		    
		     textView1 = (TextView) findViewById(R.id.title_text);
			 buttonOdd = (ImageButton) findViewById(R.id.odd_button);
             buttonWord = (ImageButton) findViewById(R.id.word_button);
             buttonMult = (ImageButton) findViewById(R.id.multiple_button);
			 levelLayout = (LinearLayout) findViewById(R.id.layout_level_select);

             oddText = (TextView) findViewById(R.id.oddtext);
             multText = (TextView) findViewById(R.id.multtext);
             wordText = (TextView) findViewById(R.id.wordtext);

		     levels = new ArrayList<Level>();
		     db = new DatabaseHandler(this);
				
				try{
					db.createDataBase();
					db.openDataBase();
				}catch(Exception e){
				
				}
				
			 levels = db.getLevels(currentType);
			 db.close();
			 word = "";

			 //sounds
			 //sound = new SoundFX();
			 //initializeSounds();
			 
			 //set font
			 font = Typeface.createFromAsset(this.getAssets(), Constants.DONUT_FONT);
             textView1.setText("Odd and Even Game");
             textView1.setTypeface(font);
             oddText.setTypeface(font);
             wordText.setTypeface(font);
             multText.setTypeface(font);

			 buttonOdd.setOnClickListener(this);
			 buttonWord.setOnClickListener(this);
             buttonMult.setOnClickListener(this);

             addLevelstoLayout();
		 }
		
		
		@SuppressWarnings("deprecation")
		@SuppressLint("ResourceAsColor") public void addLevelstoLayout()
		{
			int levelCounter = 1;
			LinearLayout.LayoutParams outsideContainterParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);outsideContainterParams.setMargins(20, 10, 20, 10);
			LayoutParams titleContainterParams = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			
			//create a layout for each level
			LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(0,LayoutParams.MATCH_PARENT, 1);
			
			for(int i = 0; i < ((int) levels.size()/3); i++)
			{
				LinearLayout outsideContainer = new LinearLayout(this.getApplicationContext());
				outsideContainer.setLayoutParams(outsideContainterParams);
				outsideContainer.setOrientation(LinearLayout.HORIZONTAL);
				outsideContainer.setPadding(8, 8, 8, 4);
				
				for(int j = 0; j < 3; j++)
				{
					
					LinearLayout innerLayout = new LinearLayout(this.getApplicationContext());
					innerLayout.setLayoutParams(param);
					innerLayout.setOrientation(LinearLayout.VERTICAL);
					innerLayout.setOnClickListener(this);
					innerLayout.setTag(Integer.valueOf(levelCounter));
					//innerLayout = (LinearLayout) findViewById(R.layout.layout_levelscreen_new);
					
					ImageView levelIm = new ImageView(this.getApplicationContext());
					//levelIm = (ImageView) findViewById(R.id.level_image);
					
					TextView levelText = new TextView(this.getApplicationContext());
				    levelText.setTypeface(font);
				    levelText.setTextColor(R.color.black);
					levelText.setText("Level " + (levelCounter));
					levelText.setGravity(Gravity.CENTER);
					levelText.setPadding(30, 15, 15, 15);

                    //CREATE THIRD DONUTS
					//set transparency of image if locked
					if(levels.get(levelCounter -1).getEnabled() == 0) {
                        if (currentType == 0){
                            levelIm.setImageResource(R.drawable.d1);

                        }
                        else if(currentType == 1)
                        {
                            levelIm.setImageResource(R.drawable.d2);


                        }
                        else if(currentType == 2)
                        {
                            levelIm.setImageResource(R.drawable.d2);

                        }
					}
					else{
                        //CREATE FIRST DONUTS
						//if the next level hasnt been played yet put exclamation mark
						if(((levelCounter + 1) < levels.size()) && !(levels.get(levelCounter).getEnabled() == 0)){
                            if (currentType == 0){
                                levelIm.setImageResource(R.drawable.d1_bitten);
                                levelIm.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(getApplicationContext(), GameEvenActivity.class));
                                    }
                                });

                            }
                            else if(currentType == 1)
                            {
                                levelIm.setImageResource(R.drawable.d2_bitten);
                            }
                            else if(currentType == 2)
                            {
                                levelIm.setImageResource(R.drawable.d3_bitten);
                            }
                        }

                        //CREATE SECOND DONUTS
						else {
                            if (currentType == 0){
                                levelIm.setImageResource(R.drawable.d1);

                            }
                            else if(currentType == 1)
                            {
                                levelIm.setImageResource(R.drawable.d2);
                                levelIm.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(getApplicationContext(), GameWordyActivity.class));
                                    }
                                });
                            }
                            else if(currentType == 2)
                            {
                                levelIm.setImageResource(R.drawable.d3);
                            } // exclamation
                        }
					}
					levelIm.setPadding(0, 2, 0, 2);
					
					innerLayout.addView(levelIm);
					innerLayout.addView(levelText);
					outsideContainer.addView(innerLayout);
					
					levelCounter++;
				}
				
				levelLayout.addView(outsideContainer);
				
			}
			
		}

    @Override
		public void onClick(View v) {
			
			//Intent intent = null;
			int id = v.getId();
			if(id == R.id.odd_button){
                System.out.println("clicked odd");
                currentType=0;
				textView1.setText("Odd and Even Game");
                levelLayout.removeAllViews();
                db = new DatabaseHandler(this);

                try{
                    db.createDataBase();
                    db.openDataBase();
                }catch(Exception e){

                }

                levels = db.getLevels(currentType);
                db.close();
                addLevelstoLayout();
			}
            else if(id == R.id.word_button){
                System.out.println("clicked word");
                currentType = 1;
                textView1.setText("Letter selector game");
                levelLayout.removeAllViews();
                db = new DatabaseHandler(this);

                try{
                    db.createDataBase();
                    db.openDataBase();
                }catch(Exception e){
                }

                levels = db.getLevels(currentType);
                addLevelstoLayout();

            }
            else if(id == R.id.multiple_button){
                currentType = 2;
                textView1.setText("Multiples game");
                levelLayout.removeAllViews();
                db = new DatabaseHandler(this);
                try{
                    db.createDataBase();
                    db.openDataBase();
                }catch(Exception e){

                }

                levels = db.getLevels(currentType);
                addLevelstoLayout();
            }
			else{//if the user clicked on a level
				int tag = ((Integer) v.getTag()) -1;
				if(levels.get(tag).getEnabled() == 1){


				}
				else{
					 //sound.playSound(MusicKeys.ERROR, 0);
                    Toast toast = Toast.makeText(this.getApplicationContext(), "Level not unlocked", Toast.LENGTH_SHORT);
                    toast.show();

				}
			}
		}
		
   @Override
   public void onBackPressed()
   {
	   super.onBackPressed();
	   //overridePendingTransition(R.anim.translate_down_onscreen, R.anim.translate_down_offscreen);
   }
   
   private void initializeSounds() {
		/*this.sound.initSounds(getApplicationContext());
		this.sound.addSound(MusicKeys.GAME_OVER_WIN, R.raw.game_over_win);
		this.sound.addSound(MusicKeys.GAME_OVER_LOSS, R.raw.game_over_lose);
		this.sound.addSound(MusicKeys.COUNTDOWN_BEEP, R.raw.sound_countdown_beep);
		this.sound.addSound(MusicKeys.POP, R.raw.sound_pop);
		this.sound.addSound(MusicKeys.ERROR, R.raw.wrong_selection);*/
	}

   private void onLevelClicked ()
   {
       startActivity(new Intent(LevelScreenActivity.this, GameEvenActivity.class));
   }
}



package com.example.ruan_lopes.fluky.gameDev.WordsGame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.ruan_lopes.fluky.R;
import com.example.ruan_lopes.fluky.gameDev.Constants;
import com.example.ruan_lopes.fluky.gameDev.EvenGame.GameEvenActivity;
import com.example.ruan_lopes.fluky.gameDev.GameOverActivity;

/**
 * Created by Ruan_Lopes on 15-03-27.
 */

    public class AlertDialogWordy extends DialogFragment {
        public static final String TITLE_ID = "title";
        public static final String MESSAGE_ID = "message";

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Bundle messages = getArguments();

            Context context = getActivity();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    getActivity().finish();

                    Intent intent = new Intent(getActivity(),GameOverActivity.class);
                    startActivity(intent);
                }
            });

            builder.setPositiveButton(context.getString(R.string.error_ok_button_text), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    getActivity().finish();

                    Constants.mTime = 30;
                    Constants.mScore = 0;
                    Constants.mLife = 5;
                    Constants.killMe = false;


                    Intent intent = new Intent(getActivity(),GameWordyActivity.class);
                    startActivity(intent);


                }
            });



            if(messages != null) {
                builder.setTitle(messages.getString(TITLE_ID, "Sorry"));
                builder.setMessage(messages.getString(MESSAGE_ID, "There was an error."));
            }
            else {
                builder.setTitle("Sorry");
                builder.setMessage("There was an error.");
            }

            AlertDialog dialog = builder.create();
            return dialog;
        }
    }


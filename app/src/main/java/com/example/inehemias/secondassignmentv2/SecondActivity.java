package com.example.inehemias.secondassignmentv2;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import static com.example.inehemias.secondassignmentv2.R.drawable.*;
import static com.example.inehemias.secondassignmentv2.R.drawable.inter;

public class SecondActivity extends AppCompatActivity {

    public  TextView winner;
    public  TextView difference;
    private ImageView imageView;


    public static int localDif=0;
    public static String TAG = "Second Activity Log";
    private String messageOut="";
    public String winnerTeam=" ";
    public static String TeamName= "winner team";
    public static String dif= "difference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        settingViews();
        extractingData();
        settingMessage();
        settingImage();


    }

    //Initializes the intent and checks if data exists.
    // If there is data then it will extract it and save it in this activity.
    private void extractingData() {
        Intent intent = getIntent();
        Bundle bundle =  intent.getExtras();

        if(bundle!=null)
            localDif= bundle.getInt(MainActivity.dif);
            winnerTeam= bundle.getString(MainActivity.TeamName);

    }

    private void settingViews() {
        winner=(TextView)findViewById(R.id.winner);
        difference=(TextView) findViewById(R.id.Difference);
        imageView=(ImageView)findViewById(R.id.imageView);

    }

    //Sets the message to be print on the winner view.
    private String settingMessage() {
        //setting the score difference message
        if(localDif<=1){
            messageOut= "Won by "+ Integer.toString(localDif) +" Point";
        }
        else {
            messageOut= "Won by "+ Integer.toString(localDif) +" Points";
        }
        return messageOut;
    }

    //Sets the corect image depending on who's the winner.
    private void settingImage() {
        //if statement to set team's background
        if (winnerTeam.contentEquals("Juventus")){
            imageView.setImageResource(juventus);
            winner.setText(winnerTeam);
            difference.setText(messageOut);


        }
        else {
            imageView.setImageResource(inter);
            winner.setText(winnerTeam);
            difference.setText(messageOut);
        }
    }
    public void OpenThirdActivity(View view) {
        Intent intent = new Intent(this, OtherActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(TeamName, winnerTeam + " " + messageOut );
        bundle.putInt(dif, localDif);

        intent.putExtras(bundle);

        startActivity(intent);


    }
    }

package com.example.inehemias.secondassignmentv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private Button Team1;
    private Button Team2;
    private TextView t1score;
    private TextView t2score;


    private static int score1;
    private static int score2;
    private final int defaultVal=0;
    private static int difference=0;


    public static String TeamName= "winner team";
    public static String dif= "difference";
    private final String TAG= "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setters();
        clickListeners();


    }

    //Function thqt will start the new intent and
    // start the next activity once a team reaches 5 points.
    private void sendResult(String name, int difference){
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(TeamName, name );
        bundle.putInt(dif, difference);

        intent.putExtras(bundle);
        startActivity(intent);
    }


    private void setters(){

        Team1 =(Button) findViewById(R.id.Team1);
        Team2 =(Button) findViewById(R.id.Team2);
        t1score=(TextView) findViewById(R.id.t1score);
        t2score= (TextView) findViewById(R.id.t2score);

    }


    //setting the onClick listeners + counting up the score
    private void clickListeners(){

        Team1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Team 1 was clicked");
                score1++;
                t1score.setText(String.valueOf(score1));

                if(score1==5){
                    Log.d(TAG, "Team 1 is the Winner!");

                    TeamName="Juventus";
                    difference= score1-score2;
                    sendResult(TeamName, difference);
                    score1=defaultVal;
                    score2=defaultVal;
                }
            }
        });
        Team2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Team 2 was clicked");
                score2++;
                t2score.setText(String.valueOf(score2));

                if(score2==5){
                    Log.d(TAG, "Team 2 is the Winner!");
                    difference=score2-score1;
                    TeamName="Inter Milan";
                    sendResult(TeamName, difference);
                    score2=defaultVal;
                    score1=defaultVal;

                }
            }
        });
    }


}

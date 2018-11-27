package com.example.inehemias.secondassignmentv2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{


    private Button Team1;
    private Button Team2;
    private TextView t1score;
    private TextView t2score;
    private FloatingActionButton fab;
    private LinearLayout mainpage;



    private static int score1;
    private static int score2;
    private final int defaultVal=0;
    private static int difference=0;


    public static String TeamName= "winner team";
    public static String dif= "difference";
    private final String TAG= "prefs";
    private String favTeam="Team 1";
    private String notFavTeam="Team 2";
    private String favSport="none";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadPrefs();
        setters();
        clickListeners();
        setPrefs();
        setBackground();

    }
    @Override
    public void onResume(){
        super.onResume();
        loadPrefs();
        setters();
        clickListeners();
        setPrefs();
        setBackground();


    }

    private void loadPrefs() {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        favSport = sharedPrefs.getString("example_list_sports","Football");
        favTeam = sharedPrefs.getString("favorite","");
        //Logs for debug usage
        Log.d(TAG, "favorite sport " + favSport);
        Log.d(TAG, "favorite Team " + favTeam);



    }

    //Function thqt will start the news intent and
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

        getTeams();
        Team1 =(Button) findViewById(R.id.Team1);
        Team1.setText(favTeam);
        Team2 =(Button) findViewById(R.id.Team2);
        Team2.setText(notFavTeam);
        t1score=(TextView) findViewById(R.id.t1score);
        t2score= (TextView) findViewById(R.id.t2score);
        fab =  findViewById(R.id.fab);
        mainpage = findViewById(R.id.mainpage);

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

                    TeamName=favTeam;
                    difference= score1-score2;
                    sendResult(TeamName, difference);

                    reset();
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
                    TeamName=notFavTeam;
                    sendResult(TeamName, difference);
                    reset();

                }
            }
        });
    }


    private void getTeams(){

        if (favTeam.contains("Barcelona")){

            notFavTeam="Real Madrid";

        }
        else if (favTeam.contains("Juventus")){

            notFavTeam="Inter Millan";
        }
        else if (favTeam.contains("Real Madrid")){
            notFavTeam="Barcelona";
        }
        else if (favTeam.contains("Inter Millan")){
            notFavTeam="Juventus";
        }
        else {
            favTeam="Team 1";
            notFavTeam="Team 2";
        }



    }

    private void reset() {
        //After we have a winner reset the score
        score1=defaultVal;
        score2=defaultVal;
        t1score.setText(String.valueOf(score1));
        t2score.setText(String.valueOf(score2));

    }


    private void setPrefs() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }


    private void setBackground(){

        if(favSport.contains("Soccer")){

            mainpage.setBackgroundResource(R.drawable.soccer);
            Log.d(TAG, "fav sport was "+ favSport);


        } else if(favSport.contains("Football")){

            mainpage.setBackgroundResource(R.drawable.football_background2);
            Log.d(TAG, "fav sport was "+ favSport);


        }
        else {
            mainpage.setBackgroundResource(R.drawable.baseball3);
            Log.d(TAG, "fav sport was "+ favSport);


        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

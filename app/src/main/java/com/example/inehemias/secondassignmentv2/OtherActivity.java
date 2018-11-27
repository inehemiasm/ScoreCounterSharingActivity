package com.example.inehemias.secondassignmentv2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class OtherActivity extends AppCompatActivity {

    private static final int REQUEST_CALL_PHONE = 0;
    private static int localDif=0;
    private static String message =" ";
    private Button callButton;





    private static String TAG = "OtherActivity";
    private String favContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        Log.d(TAG, "inside third activity");
        extractingData();
        callButton = (Button)findViewById(R.id.buttonDial);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAFriend();
            }
        });
    }


    private void extractingData() {
        Intent intent = getIntent();
        Bundle bundle =  intent.getExtras();


        if(bundle!=null)
            localDif= bundle.getInt(SecondActivity.dif);
        message = bundle.getString(SecondActivity.TeamName);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        favContact = sharedPrefs.getString("example_text_favorite","9146093982");

    }

    public void arenaNearMe(View view) {
        // Search for soccer arena nearby
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=Soccer arena  near me");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        //mapIntent.setPackage("https://www.google.com/maps/@?api=1&map_action=map");
        startActivity(mapIntent);


    }

    public void shareScoreSMS(View view) {
        Uri uri = Uri.parse("smsto:"+favContact);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", message);
        startActivity(it);

    }


    public void callAFriend() {
        Log.d(TAG, "inside of callAFriend method");
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+ favContact));

        if (intent.resolveActivity(getPackageManager()) != null) {

            if (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                ActivityCompat.requestPermissions(this, new String[]{
                        android.Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE );

            }
            else {
                startActivity(intent);
            }

        }
        else{
            Log.d(TAG, "cannot call Friend");

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "inside onRequestPermissionsResult");
        switch (requestCode){
            case REQUEST_CALL_PHONE:{
                if((grantResults.length > 0) &&
                        (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    callAFriend();
                }
            }
        }
    }


}
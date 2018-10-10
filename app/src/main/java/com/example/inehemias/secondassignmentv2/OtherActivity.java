package com.example.inehemias.secondassignmentv2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
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
    private static String teamName=" ";
    private String num="address";
    private String  phoneNumber = "123456789";
    private TextView getNumber;




    private static String TAG = "OtherActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        Log.d(TAG, "inside third activity");
        extractingData();
        getNumber = (TextView) findViewById(R.id.editTextWeb);
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

    }

    public void arenaNearMe(View view) {
        // Search for soccer arena nearby
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=Cricket arena  near me");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        //mapIntent.setPackage("https://www.google.com/maps/@?api=1&map_action=map");
        startActivity(mapIntent);


    }

    public void shareScoreSMS(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) // At least KitKat
        {
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this); // Need to change the build to API 19

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            //sendIntent.setData(Uri.parse("smsto:" + phoneNumber));
            sendIntent.putExtra(Intent.EXTRA_TEXT, message);


            if (defaultSmsPackageName != null)// Can be null in case that there is no default, then the user would be able to choose
            // any app that support this intent.
            {
                sendIntent.setPackage(defaultSmsPackageName);
            }
            startActivity(sendIntent);

        }
        else // For early versions
        {
            Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
           smsIntent.setData(Uri.parse("smsto: " + phoneNumber));
            smsIntent.putExtra(teamName,"Good news: "+"message");

            startActivity(smsIntent);
        }

    }


    public void callAFriend() {
        Log.d(TAG, "inside of callAFriend method");
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+ phoneNumber));

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

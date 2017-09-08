package com.example.hulksmash.dhadkan;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;

import com.example.hulksmash.dhadkan.controller.SessionManager;
import com.example.hulksmash.dhadkan.patientActivities.PatientListActivity;

import java.util.HashMap;

public class ControllerActivity extends Activity {
    SessionManager session;
    private final int SPLASH_DISPLAY_LENGTH = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                session = new SessionManager(ControllerActivity.this);
                session.checkLogin();
                finish();
                HashMap<String, String> user = session.getUserDetails();
                Log.d("DATA", user.toString());

                if (user.get("type").equals("doctor")) {
                    Intent i = new Intent(ControllerActivity.this, PatientListActivity.class);
                    startActivity(i);
                    finish();
                } else if (user.get("type").equals("patient")) {
                    Intent i = new Intent(ControllerActivity.this, Entry.class);
                    startActivity(i);
                    finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}


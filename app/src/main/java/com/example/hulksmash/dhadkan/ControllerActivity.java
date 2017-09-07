package com.example.hulksmash.dhadkan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hulksmash.dhadkan.controller.SessionManager;

import java.util.HashMap;

public class ControllerActivity extends AppCompatActivity {
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        if(user.get("type") == "doctor") {
            Intent i = new Intent(this, PatientListActivity.class);
            startActivity(i);
        }

        else {
            Intent i = new Intent(this, Entry.class);
            startActivity(i);
        }


    }
}

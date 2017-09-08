package com.example.hulksmash.dhadkan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hulksmash.dhadkan.patientActivities.RegisterActivity;

public class ChooseActivity extends AppCompatActivity implements View.OnClickListener{

    Button doctor, patient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        doctor = (Button) findViewById(R.id.doctor);
        patient = (Button) findViewById(R.id.patient);

        doctor.setOnClickListener(this);
        patient.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.doctor) {
            Intent i = new Intent(this, DocRegisterActivity.class);
            startActivity(i);
        }

        else if (view.getId() == R.id.patient) {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        }
        finish();
    }
}

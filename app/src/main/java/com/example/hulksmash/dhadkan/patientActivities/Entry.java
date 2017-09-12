package com.example.hulksmash.dhadkan.patientActivities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hulksmash.dhadkan.ControllerActivity;
import com.example.hulksmash.dhadkan.R;
import com.example.hulksmash.dhadkan.controller.AppController;
import com.example.hulksmash.dhadkan.controller.SessionManager;
import com.example.hulksmash.dhadkan.doctorActivities.PatientDetailListActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Entry extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    EditText date, time , weight, heart_rate, systolic, diastolic;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Button save;
    SessionManager session;
    String currentDateandTime;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.patient_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout(this);
                return true;
            case R.id.action_change_doctor:
                change_doctor();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void change_doctor() {

    }

    private void logout(Context _c) {
        session = new SessionManager(_c);
        session.logoutUser();
        Intent i = new Intent(Entry.this, ControllerActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        getSupportActionBar().setTitle("Enter your Health Records");

        currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());

        Log.d("TAG", currentDateandTime.toString());

        date = (EditText) findViewById(R.id.editText);
        date.setText(currentDateandTime.split(" ")[0]);
        date.setOnClickListener(this);
        time = (EditText) findViewById(R.id.editText2);
        time.setOnClickListener(this);
        time.setText(currentDateandTime.split(" ")[1]);
        datePickerDialog = new DatePickerDialog(
                this, Entry.this, 1950, 0, 0);
        timePickerDialog = new TimePickerDialog(this, Entry.this, 0, 0, true);

        weight = (EditText) findViewById(R.id.editText3);
        heart_rate = (EditText) findViewById(R.id.editText4);
        systolic = (EditText) findViewById(R.id.editText5);
        diastolic = (EditText) findViewById(R.id.editText6);

        save = (Button) findViewById(R.id.register);
        save.setOnClickListener(this);

        session = new SessionManager(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.editText) {
            datePickerDialog.show();
        } else if(view.getId() == R.id.editText2) {
            timePickerDialog.show();
        } else if(view.getId() == R.id.register) {
            String str_weight = "" + weight.getText();
            String str_heart_rate = "" + heart_rate.getText();
            String str_systolic = "" + systolic.getText();
            String str_diastolic = "" + diastolic.getText();
            if (str_weight.length() == 0) {
                Toast.makeText(Entry.this, "enter your weight", Toast.LENGTH_LONG).show();
                return;
            }

            if (str_heart_rate.length() == 0) {
                Toast.makeText(Entry.this, "enter your heart_rate", Toast.LENGTH_LONG).show();
                return;
            }

            if (str_diastolic.length() == 0) {
                Toast.makeText(Entry.this, "enter your diastolic bp", Toast.LENGTH_LONG).show();
                return;
            }

            if (str_systolic.length() == 0) {
                Toast.makeText(Entry.this, "enter your systolic bp", Toast.LENGTH_LONG).show();
                return;
            }
            String url = AppController.get_base_url() + "dhadkan/api/data";
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    url, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("TAG", response.toString());
                            Toast.makeText(Entry.this, "Data updated!", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(Entry.this, Entry.class);
                            startActivity(i);
                            finish();
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("TAG", "Error Message: " + error.getMessage());
                }
            }) {

                @Override
                public byte[] getBody() {
                    JSONObject params = new JSONObject();

                    SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    try {
                        HashMap<String, String> user = session.getUserDetails();
                        params.put("weight", weight.getText());
                        params.put("heart_rate", heart_rate.getText());
                        params.put("systolic", systolic.getText());
                        params.put("diastolic", diastolic.getText());
                        params.put("patient", Integer.parseInt(user.get("id")));
                        String d = "" + date.getText();
                        String t = "" + time.getText() + ":00";
                        params.put("time_stamp", d + " " + t);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return params.toString().getBytes();

                }
            };
            AppController.getInstance().addToRequestQueue(jsonObjReq);
        }

    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        i1 = i1 +1;
        date.setText(i + "-" + i1 + "-" + i2);
        Toast.makeText(this, "" + i + i1 + i2, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        String i_str = "" + i;
        String i1_str = "" + i1;
        if (i < 10) {
            i_str = "0" + i;
        }
        if (i1 < 10){
            i1_str = "0" + i1;
        }
        time.setText(i_str + ":" + i1_str + ":00" );
    }
}

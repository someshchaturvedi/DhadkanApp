package com.example.hulksmash.dhadkan;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hulksmash.dhadkan.controller.AppController;

import org.json.JSONException;
import org.json.JSONObject;

public class Entry extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    EditText date, time , weight, heart_rate, systolic, diastolic;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        date = (EditText) findViewById(R.id.editText);
        date.setOnClickListener(this);
        time = (EditText) findViewById(R.id.editText2);
        time.setOnClickListener(this);
        datePickerDialog = new DatePickerDialog(
                this, Entry.this, 1950, 0, 0);
        timePickerDialog = new TimePickerDialog(this, Entry.this, 0, 0, true);

        weight = (EditText) findViewById(R.id.editText3);
        heart_rate = (EditText) findViewById(R.id.editText4);
        systolic = (EditText) findViewById(R.id.editText5);
        diastolic = (EditText) findViewById(R.id.editText6);

        save = (Button) findViewById(R.id.register);
        save.setOnClickListener(this);

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
            String url = "https://04edccda.ngrok.io/dhadkan/api/data";
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    url, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("TAG", response.toString());
                            Toast.makeText(Entry.this, "Data updated!", Toast.LENGTH_SHORT).show();
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
                        params.put("weight", weight.getText());
                        params.put("heart_rate", heart_rate.getText());
                        params.put("systolic", systolic.getText());
                        params.put("diastolic", diastolic.getText());
                        params.put("patient", pref.getInt("P_ID", 0));

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

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

    }
}

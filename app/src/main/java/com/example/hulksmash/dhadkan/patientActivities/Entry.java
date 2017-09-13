package com.example.hulksmash.dhadkan.patientActivities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hulksmash.dhadkan.ChooseActivity;
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
import java.util.Map;

import static android.R.attr.password;

public class Entry extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    EditText date, time , weight, heart_rate, systolic, diastolic, doc_number, doc_name;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Button save, done;
    static  SessionManager session;
    String currentDateandTime;
    Dialog choose_doc;
    int doc_id;

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



        choose_doc = new Dialog(this);
        choose_doc.requestWindowFeature(Window.FEATURE_NO_TITLE);
        choose_doc.setContentView(R.layout.choose_doc_dialog);
        choose_doc.setCancelable(true);
        done = choose_doc.findViewById(R.id.login);

        doc_number = choose_doc.findViewById(R.id.editText8);
        doc_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 10) {
                    String url = AppController.get_base_url() + "dhadkan/api/doctor?mobile=" + editable.toString();
                    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                            url, null,
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("TAG", response.toString());

                                    try {
                                        doc_name.setText(response.get("name") + "");
                                        doc_id = (int) response.get("pk");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        doc_name.setText("");
                                        Toast.makeText(Entry.this, "No doctor with this mobile number is registered", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("TAG", "Error Message: " + error.getMessage());
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Authorization", "Token " + session.getUserDetails().get("Token"));
                            Log.d("TAG", "Token " + session.getUserDetails().get("Token"));
//                params.put("Authorization", "Token f00a64734d608991730ccba944776c316c38c544");
                            return params;
                        }

                    };
                    AppController.getInstance().addToRequestQueue(jsonObjReq);
                }
            }
        });
        doc_name = choose_doc.findViewById(R.id.editText9);
        choose_doc.show();

        done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.login) {


                    String url = AppController.get_base_url() + "dhadkan/api/patient/" + session.getUserDetails().get("id");
                    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                            url, null,
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("TAG", response.toString());
                                    choose_doc.dismiss();
                                    Toast.makeText(Entry.this, "Doctor changed", Toast.LENGTH_LONG).show();

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
                            try {
                                params.put("d_id", doc_id);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            return params.toString().getBytes();

                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Authorization", "Token " + session.getUserDetails().get("Token"));
                            Log.d("TAG", "Token " + session.getUserDetails().get("Token"));
//                params.put("Authorization", "Token f00a64734d608991730ccba944776c316c38c544");
                            return params;
                        }
                    };
                    AppController.getInstance().addToRequestQueue(jsonObjReq);
                }

            }
        });
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
                Toast.makeText(Entry.this, "Enter your weight", Toast.LENGTH_LONG).show();
                return;
            }

            if (str_heart_rate.length() == 0) {
                Toast.makeText(Entry.this, "Enter your heart_rate", Toast.LENGTH_LONG).show();
                return;
            }

            if (str_diastolic.length() == 0) {
                Toast.makeText(Entry.this, "Enter your diastolic bp", Toast.LENGTH_LONG).show();
                return;
            }

            if (str_systolic.length() == 0) {
                Toast.makeText(Entry.this, "Enter your systolic bp", Toast.LENGTH_LONG).show();
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

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "Token " + session.getUserDetails().get("Token"));
                    Log.d("TAG", "Token " + session.getUserDetails().get("Token"));
//                params.put("Authorization", "Token f00a64734d608991730ccba944776c316c38c544");
                    return params;
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

package com.example.hulksmash.dhadkan.patientActivities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hulksmash.dhadkan.ControllerActivity;
import com.example.hulksmash.dhadkan.R;
import com.example.hulksmash.dhadkan.controller.AppController;
import com.example.hulksmash.dhadkan.controller.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.Request.*;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, TextWatcher {
    Button register;
    EditText date_of_birth, time_reminder, name, address, email, mobile, password, doctor_number, doctor_name;
    Spinner pre_mobile, pre_doctor_mobile, reminder;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    RadioGroup sexRadioGroup;
    int doc_id;
    String token, type;
    int u_id, id;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

//        Toast.makeText(RegisterActivity.this, pref.getString("Token", "") + pref.getInt("P_ID", 0) + pref.getInt("U_ID", 0), Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_register);
        name = (EditText) findViewById(R.id.editText);
        address = (EditText) findViewById(R.id.editText2);
        email = (EditText) findViewById(R.id.editText4);
        mobile = (EditText) findViewById(R.id.editText5);
        password = (EditText) findViewById(R.id.editText6);

        doctor_number = (EditText) findViewById(R.id.editText7);
        doctor_number.addTextChangedListener(this);
        doctor_name = (EditText) findViewById(R.id.editText10);

        pre_mobile = (Spinner) findViewById(R.id.spinner);
        pre_doctor_mobile = (Spinner) findViewById(R.id.spinner2);
        reminder = (Spinner) findViewById(R.id.spinner3);

        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);

        date_of_birth = (EditText) findViewById(R.id.editText3);
        date_of_birth.setOnClickListener(this);

        time_reminder = (EditText) findViewById(R.id.editText11);
        time_reminder.setOnClickListener(this);

        datePickerDialog = new DatePickerDialog(
                this, RegisterActivity.this, 1950, 0, 0);
        timePickerDialog = new TimePickerDialog(this, RegisterActivity.this, 0, 0, true);

        sexRadioGroup = (RadioGroup) findViewById(R.id.radioSex);
        session = new SessionManager(this);


    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.register) {
            String str_mobile = "" + mobile.getText();
            String str_password = "" + password.getText();
            String str_name = "" + name.getText();
            String str_address = "" + address.getText();
            String str_dob = "" + date_of_birth.getText();
            String str_doctor_mobile = "" + doctor_number.getText();

            if (str_name.length() == 0) {
                Toast.makeText(RegisterActivity.this, "enter your name", Toast.LENGTH_LONG).show();
                return;
            }

            if (str_address.length() == 0) {
                Toast.makeText(RegisterActivity.this, "enter your address", Toast.LENGTH_LONG).show();
                return;
            }

            if (str_mobile.length() == 0) {
                Toast.makeText(RegisterActivity.this, "enter your mobile number", Toast.LENGTH_LONG).show();
                return;
            }

            if (str_password.length() == 0) {
                Toast.makeText(RegisterActivity.this, "enter your password", Toast.LENGTH_LONG).show();
                return;
            }

            if (str_dob.length() == 0) {
                Toast.makeText(RegisterActivity.this, "enter your date of birth", Toast.LENGTH_LONG).show();
                return;
            }

            if (str_doctor_mobile.length() == 0) {
                Toast.makeText(RegisterActivity.this, "enter your doctor's number", Toast.LENGTH_LONG).show();
                return;
            }


            String url = AppController.get_base_url() + "dhadkan/api/onboard/patient";
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
                    url, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("TAG", response.toString());
//                            SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor edit = pref.edit();
                            try {
                                int U_ID = Integer.parseInt(response.get("U_ID").toString());
                                String token = "" + response.get("Token");
                                int ID = Integer.parseInt(response.get("ID").toString());
                                session.createLoginSession(token, U_ID, "patient", ID);
                                Intent i = new Intent(RegisterActivity.this, ControllerActivity.class);
                                startActivity(i);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
//                            edit.commit();
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
                    int selectedId = sexRadioGroup.getCheckedRadioButtonId();
                    int gender;
                    if (selectedId == R.id.radioMale) {
                        gender = 1;
                    }
                    else{
                        gender = 0;
                    }

                    SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    try {
                        params.put("name", "" + name.getText());
                        params.put("address", "" + address.getText());
                        params.put("password", "" + password.getText());
                        params.put("mobile", mobile.getText());
                        params.put("email", "" + email.getText());
                        params.put("doctor", doc_id);
//                        params.put("date_of_birth", );

                        params.put("gender", gender);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return params.toString().getBytes();

                }
            };
            AppController.getInstance().addToRequestQueue(jsonObjReq);

        } else if (view.getId() == R.id.editText3) {
            datePickerDialog.show();
        } else if (view.getId() == R.id.editText11) {
            timePickerDialog.show();
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        date_of_birth.setText(i + "/" + i1 + "/" + i2);
        Toast.makeText(this, "" + i + i1 + i2, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        time_reminder.setText(i + ":" + i1);
        Toast.makeText(this, "" + i + i1, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(final Editable editable) {
        if (editable.toString().length() == 10) {
            String url = AppController.get_base_url() + "dhadkan/api/doctor?mobile=" + editable.toString();
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
                    url, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("TAG", response.toString());

                            try {
                                doctor_name.setText(response.get("name") + "");
                                doc_id = (int) response.get("pk");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                doctor_name.setText("");
                                Toast.makeText(RegisterActivity.this, "No doctor with this mobile number is registered", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("TAG", "Error Message: " + error.getMessage());
                }
            }) {

            };
            AppController.getInstance().addToRequestQueue(jsonObjReq);
        }
    }
}

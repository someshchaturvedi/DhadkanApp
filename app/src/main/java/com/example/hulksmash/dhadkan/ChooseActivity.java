package com.example.hulksmash.dhadkan;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hulksmash.dhadkan.controller.AppController;
import com.example.hulksmash.dhadkan.doctorActivities.DocRegisterActivity;
import com.example.hulksmash.dhadkan.patientActivities.Entry;
import com.example.hulksmash.dhadkan.patientActivities.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class ChooseActivity extends AppCompatActivity implements View.OnClickListener {

    Button doctor, patient, sign_in;
    Dialog sign_in_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        getSupportActionBar().hide();
        doctor = (Button) findViewById(R.id.doctor);
        patient = (Button) findViewById(R.id.patient);
        sign_in = (Button) findViewById(R.id.sign_in);

        doctor.setOnClickListener(this);
        patient.setOnClickListener(this);
        sign_in.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.doctor) {
            Intent i = new Intent(this, DocRegisterActivity.class);
            startActivity(i);
        } else if (view.getId() == R.id.patient) {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        } else if (view.getId() == R.id.sign_in) {
            callLoginDialog();
        }

    }

    private void callLoginDialog() {
        final EditText username, password;
        Button login;


        sign_in_dialog = new Dialog(this);
        sign_in_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sign_in_dialog.setContentView(R.layout.activity_sign_in);
        sign_in_dialog.setCancelable(true);
        login = sign_in_dialog.findViewById(R.id.login);

        username = sign_in_dialog.findViewById(R.id.editText8);
        password = sign_in_dialog.findViewById(R.id.editText9);
        sign_in_dialog.show();

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                final SharedPreferences.Editor edit = pref.edit();

                if (v.getId() == R.id.sign_in) {
                    String str_username = "" + username.getText();
                    String str_password = "" + password.getText();

                    if (str_username.length() == 0) {
                        Toast.makeText(ChooseActivity.this, "enter your mobile number", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (str_password.length() == 0) {
                        Toast.makeText(ChooseActivity.this, "enter your password", Toast.LENGTH_LONG).show();
                        return;
                    }
                    String url1 = AppController.get_base_url() + "dhadkan/api/login";
                    JsonObjectRequest jsonObjReq1 = new JsonObjectRequest(Request.Method.POST,
                            url1, null,
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("TAG", response.toString());

                                    try {
                                        edit.putString("Token", "" + response.get("token"));
                                        Log.d("SP", "" + response.get("token"));
                                        finish();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    edit.commit();
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
                                String str_mobile = "" + username.getText();
                                String str_password = "" + password.getText();
                                params.put("username", str_mobile);
                                params.put("password", str_password);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            return params.toString().getBytes();

                        }
                    };
                    AppController.getInstance().addToRequestQueue(jsonObjReq1);
                    if (pref.getString("Token", "") != "") {
                        Toast.makeText(ChooseActivity.this, pref.getString("Token", ""), Toast.LENGTH_LONG).show();
                    }
                    startActivity(new Intent(ChooseActivity.this, Entry.class));
                }

            }
        });
    }
}

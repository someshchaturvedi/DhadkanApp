package com.example.hulksmash.dhadkan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hulksmash.dhadkan.controller.AppController;
import com.example.hulksmash.dhadkan.patientActivities.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    Button new_user, sign_in ;
    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        new_user = (Button) findViewById(R.id.new_user);
        sign_in = (Button) findViewById(R.id.sign_in);
        new_user.setOnClickListener(this);
        sign_in.setOnClickListener(this);
        username = (EditText) findViewById(R.id.editText8);
        password = (EditText) findViewById(R.id.editText9);

    }

    @Override
    public void onClick(View view) {
        final SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        final SharedPreferences.Editor edit = pref.edit();
        if (view.getId() == R.id.new_user) {
            startActivity(new Intent(this, RegisterActivity.class));
        }
        else if(view.getId() == R.id.sign_in) {
            String str_username = "" + username.getText();
            String str_password = "" + password.getText();

            if (str_username.length() == 0) {
                Toast.makeText(SignInActivity.this, "enter your mobile number", Toast.LENGTH_LONG).show();
                return;
            }

            if (str_password.length() == 0) {
                Toast.makeText(SignInActivity.this, "enter your password", Toast.LENGTH_LONG).show();
                return;
            }
            String url1 = "https://04edccda.ngrok.io/dhadkan/api/login";
            JsonObjectRequest jsonObjReq1 = new JsonObjectRequest(Request.Method.POST,
                    url1, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("TAG", response.toString());

                            try {
                                edit.putString("Token", "" + response.get("token"));
                                Log.d("SP", "" + response.get("token"));
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
                Toast.makeText(SignInActivity.this,pref.getString("Token", "") , Toast.LENGTH_LONG).show();
            }
            startActivity(new Intent(this, Entry.class));
        }
    }
}

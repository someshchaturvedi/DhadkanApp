package com.example.hulksmash.dhadkan;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hulksmash.dhadkan.controller.AppController;
import com.example.hulksmash.dhadkan.controller.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.R.attr.password;
import static android.R.id.edit;

public class DocRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    Button register;
    EditText name, mobile, hospital, email, password;
    Spinner pre_mobile;
    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_register);

        register = (Button) findViewById(R.id.register);
        name = (EditText) findViewById(R.id.editText13);
        mobile = (EditText) findViewById(R.id.editText14);
        email = (EditText) findViewById(R.id.editText15);
        hospital = (EditText) findViewById(R.id.editText16);
        pre_mobile = (Spinner) findViewById(R.id.spinner);
        password = (EditText) findViewById(R.id.editText12);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.register) {
            String str_mobile = "" + mobile.getText();
            String str_password = "" + password.getText();
            String str_name = "" + name.getText();
            String str_email = "" + email.getText();
            String str_hospital = "" + hospital.getText();

            if (str_name.length() == 0) {
                Toast.makeText(DocRegisterActivity.this, "enter your name", Toast.LENGTH_LONG).show();
                return;
            }

            if (str_email.length() == 0) {
                Toast.makeText(DocRegisterActivity.this, "enter your email address", Toast.LENGTH_LONG).show();
                return;
            }

            if (str_mobile.length() == 0) {
                Toast.makeText(DocRegisterActivity.this, "enter your mobile number", Toast.LENGTH_LONG).show();
                return;
            }

            if (str_password.length() == 0) {
                Toast.makeText(DocRegisterActivity.this, "enter your password", Toast.LENGTH_LONG).show();
                return;
            }

            if (str_hospital.length() == 0) {
                Toast.makeText(DocRegisterActivity.this, "enter your hospital", Toast.LENGTH_LONG).show();
                return;
            }


            String url = AppController.get_base_url() + "dhadkan/api/onboard/doc";
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    url, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("DATA", response.toString());
//                            SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor edit = pref.edit();
                            try {


                                int U_ID = (Integer) response.get("ID");
                                String token = "" + response.get("Token");
                                int ID = (int) response.get("ID");

                                session.createLoginSession(token, U_ID, "doctor", ID);
//
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
                    try {
                        params.put("name", "" + name.getText());
                        params.put("password", "" + password.getText());
                        params.put("mobile", mobile.getText());
                        params.put("email", "" + email.getText());
                        params.put("hospital", "" + hospital.getText());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return params.toString().getBytes();

                }
            };
            AppController.getInstance().addToRequestQueue(jsonObjReq);


//            String url1 = AppController.get_base_url() + "dhadkan/api/login";
//            JsonObjectRequest jsonObjReq1 = new JsonObjectRequest(Request.Method.POST,
//                    url1, null,
//                    new Response.Listener<JSONObject>() {
//
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            Log.d("TAG", response.toString());
////                            SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
////                            SharedPreferences.Editor edit = pref.edit();
//                            try {
//                                token = "" + response.get("token");
////                                edit.putString("Token", "" + response.get("token"));
//                                Log.d("SP", "" + response.get("token"));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
////                            edit.commit();
//                        }
//                    }, new Response.ErrorListener() {
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.d("TAG", "Error Message: " + error.getMessage());
//                }
//            }) {
//
//                @Override
//                public byte[] getBody() {
//                    JSONObject params = new JSONObject();
//                    try {
//                        String str_mobile = "" + mobile.getText();
//                        String str_password = "" + password.getText();
//                        params.put("username", str_mobile);
//                        params.put("password", str_password);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    return params.toString().getBytes();
//
//                }
//            };
//            AppController.getInstance().addToRequestQueue(jsonObjReq1);
//
//
//            String url2 = AppController.get_base_url() + "dhadkan/api/doctor";
//            JsonObjectRequest jsonObjReq2 = new JsonObjectRequest(Request.Method.POST,
//                    url2, null,
//                    new Response.Listener<JSONObject>() {
//
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            Log.d("TAG", response.toString());
//                            SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor edit = pref.edit();
//                            try {
//                                edit.putInt("D_ID", (Integer) response.get("pk"));
//                                Log.d("SP", "" + response.get("pk"));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            edit.putBoolean("registered", true);
//                            edit.commit();
//                        }
//                    }, new Response.ErrorListener() {
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.d("TAG", "Error Message: " + error.getMessage());
//                }
//            }) {
//
//                @Override
//                public byte[] getBody() {
//                    JSONObject params = new JSONObject();
//                    SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//                    try {
//                        params.put("name", "" + name.getText());
//                        params.put("password", "" + password.getText());
//                        params.put("mobile", mobile.getText());
//                        params.put("email", "" + email.getText());
//                        params.put("hospital", "" + hospital.getText());
//                        params.put("user", pref.getString("U_ID", ""));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    return params.toString().getBytes();
//
//                }
//
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<String, String>();
//                    SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//                    String token = pref.getString("Token", "");
//                    headers.put("Authorization", "Token " + token);
//                    Toast.makeText(DocRegisterActivity.this, token, Toast.LENGTH_LONG).show();
//                    return headers;
//                }
//            };
//            AppController.getInstance().addToRequestQueue(jsonObjReq2);
        }

    }
}

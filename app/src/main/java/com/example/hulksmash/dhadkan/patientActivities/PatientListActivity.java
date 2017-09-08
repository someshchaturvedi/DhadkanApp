package com.example.hulksmash.dhadkan.patientActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.hulksmash.dhadkan.R;
import com.example.hulksmash.dhadkan.controller.CustomAdapter;

import java.util.ArrayList;
import java.util.List;

public class PatientListActivity extends AppCompatActivity {
    RecyclerView patient_list_view;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        patient_list_view = (RecyclerView) findViewById(R.id.patient_list);
        adapter = new CustomAdapter(this, getData());
        patient_list_view.setAdapter(adapter);
        patient_list_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        patient_list_view.setItemAnimator(new DefaultItemAnimator());


    }

    public static List<PatientRow> getData(){
        List<PatientRow> data = new ArrayList<PatientRow>();

//        String url = AppController.get_base_url() + "patient";
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
//                url, null,
//                new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("TAG", response.toString());
////                            SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
////                            SharedPreferences.Editor edit = pref.edit();
//                        try {
//                            u_id = (int) response.get("id");
////                                edit.putInt("U_ID", (Integer) response.get("id"));
//                            Log.d("SP", "" + response.get("id"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
////                            edit.commit();
//                    }
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("TAG", "Error Message: " + error.getMessage());
//            }
//        }) {
//
//            @Override
//            public byte[] getBody() {
//                JSONObject params = new JSONObject();
//                try {
//                    String str_mobile = "" + mobile.getText();
//                    String str_password = "" + password.getText();
//                    params.put("username", str_mobile);
//                    params.put("password", str_password);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                return params.toString().getBytes();
//
//            }
//        };
//        AppController.getInstance().addToRequestQueue(jsonObjReq);

        String [] names = {"Somesh", "Animesh", "abc" ,"fuck", "lol", "getlost", "Wooo hooo", "Somesh", "Animesh", "abc" ,"fuck", "lol", "getlost", "Wooo hooo"};
        int [] ages = {1,2,3,4,5,6,7, 1,2,3,4,5,6,7};
        String [] genders = {"Somesh", "Animesh", "abc" ,"fuck", "lol", "getlost", "Wooo hooo","Somesh", "Animesh", "abc" ,"fuck", "lol", "getlost", "Wooo hooo" };

        for (int i=0; i< names.length; i++) {
            data.add(new PatientRow(names[i], ages[i], genders[i]));
        }
        return data;
    }
}

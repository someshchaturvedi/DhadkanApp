package com.example.hulksmash.dhadkan.doctorActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hulksmash.dhadkan.R;
import com.example.hulksmash.dhadkan.controller.AppController;
import com.example.hulksmash.dhadkan.controller.CustomAdapter;
import com.example.hulksmash.dhadkan.controller.PatientDetailCustomAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientDetailListActivity extends AppCompatActivity {
    RecyclerView patient_detail_list_view;
    PatientDetailCustomAdapter adapter;
    static String p_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail_list);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            p_id = extras.getString("P_ID");
            Log.d("TAG", p_id);
            //The key argument here must match that used in the other activity
        }
        patient_detail_list_view = (RecyclerView) findViewById(R.id.patient_detail_list);
        getData(new CallBack() {
            @Override
            public void onSuccess(List<PatientDetailRow> patient_detail_list) {
                Log.d("TAG", patient_detail_list.toString());
                adapter = new PatientDetailCustomAdapter(PatientDetailListActivity.this, patient_detail_list);
                patient_detail_list_view.setAdapter(adapter);
                patient_detail_list_view.setLayoutManager(new LinearLayoutManager(PatientDetailListActivity.this, LinearLayoutManager.VERTICAL, false));
                patient_detail_list_view.setItemAnimator(new DefaultItemAnimator());

            }

            @Override
            public void onFail(String msg) {

            }
        });


    }

    public void getData(final CallBack onCallback) {

        String url = AppController.get_base_url() + "dhadkan/api/patient/" + p_id;
        List<PatientDetailRow> data = new ArrayList<PatientDetailRow>();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
//
                        try {
                            List<PatientDetailRow> data = new ArrayList<PatientDetailRow>();
                            JSONArray patient_details = response.getJSONArray("data");
                            for(int i=0; i <patient_details.length(); i++) {
                                JSONObject po = (JSONObject) patient_details.get(i);
                                PatientDetailRow pr = new PatientDetailRow(
                                        po.getString("time_stamp").split("T")[0],
                                        po.getString("time_stamp").split("T")[1],
                                        "" + po.getInt("weight"),
                                        "" + po.getInt("heart_rate"),
                                        "" + po.getInt("systolic"),
                                        "" + po.getInt("diastolic")
                                );
                                data.add(pr);
                            }
                            Log.d("TAG", data.toString());
                            onCallback.onSuccess(data);
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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
//                params.put("Authorization", "Token" + session.getUserDetails().get("Token"));
                params.put("Authorization", "Token 64e1081675f6d215754cdfd188f7b43fcda6820a");
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }

    public interface CallBack {
        void onSuccess(List<PatientDetailRow> patient_detail_list);

        void onFail(String msg);
    }

}

package com.example.hulksmash.dhadkan.doctorActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.hulksmash.dhadkan.R;
import com.example.hulksmash.dhadkan.controller.CustomAdapter;
import com.example.hulksmash.dhadkan.controller.PatientDetailCustomAdapter;

import java.util.ArrayList;
import java.util.List;

public class PatientDetailListActivity extends AppCompatActivity {
    RecyclerView patient_detail_list_view;
    PatientDetailCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail_list);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("P_ID");
            Log.d("TAG" ,value);
            //The key argument here must match that used in the other activity
        }
        patient_detail_list_view = (RecyclerView) findViewById(R.id.patient_detail_list);
        adapter = new PatientDetailCustomAdapter(this, getData());
        patient_detail_list_view.setAdapter(adapter);
        patient_detail_list_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        patient_detail_list_view.setItemAnimator(new DefaultItemAnimator());


    }

    public static List<PatientDetailRow> getData(){
        List<PatientDetailRow> data = new ArrayList<PatientDetailRow>();

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

        String [] dates = {"17", "18", "19" ,"20", "21", "17", "18", "19" ,"20", "21", "17", "18", "19" ,"20", "21"};
        String [] times = {"17", "18", "19" ,"20", "21", "17", "18", "19" ,"20", "21", "17", "18", "19" ,"20", "21"};
        String [] weights = {"17", "18", "19" ,"20", "21", "17", "18", "19" ,"20", "21", "17", "18", "19" ,"20", "21"};
        String [] heart_rates = {"17", "18", "19" ,"20", "21", "17", "18", "19" ,"20", "21", "17", "18", "19" ,"20", "21"};
        String [] systolics = {"17", "18", "19" ,"20", "21", "17", "18", "19" ,"20", "21", "17", "18", "19" ,"20", "21"};
        String [] diastolics = {"17", "18", "19" ,"20", "21", "17", "18", "19" ,"20", "21", "17", "18", "19" ,"20", "21"};

        for (int i=0; i< dates.length; i++) {
            data.add(new PatientDetailRow(dates[i], times[i], weights[i], heart_rates[i], systolics[i], diastolics[i]));
        }
        return data;
    }

}

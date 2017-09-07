package com.example.hulksmash.dhadkan.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hulksmash.dhadkan.PatientRow;
import com.example.hulksmash.dhadkan.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by hulksmash on 6/9/17.
 */

public class CustomAdapter extends RecyclerView.Adapter <CustomAdapter.MyViewHolder>{

    List<PatientRow> data = Collections.emptyList();
    private LayoutInflater inflater;


    public CustomAdapter(Context context, List<PatientRow> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  inflater.inflate(R.layout.patient_row, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PatientRow current = data.get(position);
        holder.name.setText(current.name);
        holder.gender.setText(current.gender);
        holder.age.setText("" + current.age);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, age, gender;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.TextView17);
            gender = itemView.findViewById(R.id.TextView19);
            age = itemView.findViewById(R.id.TextView18);

        }
    }
}

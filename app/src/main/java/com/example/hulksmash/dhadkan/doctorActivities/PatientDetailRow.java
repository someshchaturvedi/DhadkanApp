package com.example.hulksmash.dhadkan.doctorActivities;

/**
 * Created by hulksmash on 10/9/17.
 */



public class PatientDetailRow {
    public String date_date ,date_month, date_year, time_hour, time_min, time_period, weight, heart_rate, systolic, diastolic;


    PatientDetailRow(String date, String time, String weight, String heart_rate, String systolic, String diastolic) {
        this.date_date = get_date_date(date);
        this.date_month = get_date_month(date);
        this.date_year = get_date_year(date);
        this.time_hour = get_time_hour(time);
        this.time_min = get_time_min(time);
        this.time_period = get_time_period(time);
        this.weight = weight;
        this.heart_rate = heart_rate;
        this.systolic = systolic;
        this.diastolic = diastolic;
    }

    private String get_time_period(String time) {
        return "AM";
    }

    private String get_time_min(String time) {
        return time;
    }

    private String get_time_hour(String time) {
        return time;
    }

    private String get_date_year(String date) {
        return "20" + date;
    }

    private String get_date_month(String date) {
        return "Aug";
    }

    private String get_date_date(String date) {
        return date;
    }
}

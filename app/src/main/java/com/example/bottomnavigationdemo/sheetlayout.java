package com.example.bottomnavigationdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.DatePicker;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import android.content.DialogInterface;
import android.app.DatePickerDialog;


public class sheetlayout extends Fragment {
    private DatePicker datePicker;

    private TextView textView3;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.bottomsheetlayout, container, false);


        return rootView;
    }






}
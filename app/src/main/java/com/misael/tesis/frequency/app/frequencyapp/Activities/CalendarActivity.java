package com.misael.tesis.frequency.app.frequencyapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.misael.tesis.frequency.app.frequencyapp.R;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        getSupportActionBar().setTitle("Calendario");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

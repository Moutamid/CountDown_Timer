package com.moutamid.countdown_timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.dhims.timerview.TimerTextView;

import java.util.Timer;

public class Timer_Acticity extends AppCompatActivity {

    TextView hh , mm , ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_acticity);

        hh = findViewById(R.id.hour_Tv);
        mm = findViewById(R.id.minute_Tv);
        ss = findViewById(R.id.second_Tv);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            hh.setText(bundle.getString("hh"));
            mm.setText(bundle.getString("mm"));
            ss.setText(bundle.getString("ss"));
        }

        int hour = Integer.valueOf(hh.getText().toString().trim());
        int min = Integer.valueOf(mm.getText().toString().trim());
        int sec = Integer.valueOf(ss.getText().toString().trim());

        int h = hour*60*60;
        int m = min*60;
        int s = sec;
        int total = h+m+s;

        long futureTimestamp = System.currentTimeMillis() + (total * 1000);
        TimerTextView timerText = (TimerTextView) findViewById(R.id.timerText);
        timerText.setEndTime(futureTimestamp);
    }
}
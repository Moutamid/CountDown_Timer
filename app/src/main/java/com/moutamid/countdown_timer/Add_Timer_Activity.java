package com.moutamid.countdown_timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;

public class Add_Timer_Activity extends AppCompatActivity {

    public static SQLiteHelper mSQLiteHelper;
    Button create_btn;
    EditText hourET , minuteET , secondET ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timer);

        create_btn = findViewById(R.id.create_btn);
        hourET = findViewById(R.id.hour_Et);
        minuteET = findViewById(R.id.minute_Et);
        secondET = findViewById(R.id.second_Et);

        mSQLiteHelper = new SQLiteHelper(this, "RECORDDB.sqlite", null, 1);

        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Add_Timer_Activity.this , MainActivity.class);
                startActivity(intent);
                try {
                    mSQLiteHelper.insertData(
                            hourET.getText().toString().trim(),
                            minuteET.getText().toString().trim(),
                            secondET.getText().toString().trim()
                    );
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
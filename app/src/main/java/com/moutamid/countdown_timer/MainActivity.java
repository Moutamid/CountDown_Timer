package com.moutamid.countdown_timer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dhims.timerview.TimerTextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab_btn;
    ListView mListView;
    LinearLayout layout_notimer;
    ArrayList<Model> mList;
    RecordListAdapter mAdapter = null;
    public static SQLiteHelper mSQLiteHelper;
    String hour;
    String minute;
    String seconds;

    TextView privacy_text;
    Button thumbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.listView);
        mList = new ArrayList<>();
        mAdapter = new RecordListAdapter(this, R.layout.row, mList);
        mListView.setAdapter(mAdapter);
        layout_notimer = findViewById(R.id.layout_notimer);
        mListView.setVisibility(View.VISIBLE);

        thumbs = findViewById(R.id.btn_thumbs);
        privacy_text = findViewById(R.id.privacy_policy);

        mSQLiteHelper = new SQLiteHelper(this, "RECORDDB.sqlite", null, 1);

        mSQLiteHelper.queryData("CREATE TABLE IF NOT EXISTS RECORD(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, age VARCHAR, phone VARCHAR, image BLOB)");

        Cursor cursor = mSQLiteHelper.getData("SELECT * FROM RECORD");
        mList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            hour = cursor.getString(1);
            minute = cursor.getString(2);
            seconds = cursor.getString(3);

            //add to list
            mList.add(new Model(id, hour, minute , seconds));
        }
        mAdapter.notifyDataSetChanged();
        if (mList.size()==0){
            mListView.setVisibility(View.GONE);
            layout_notimer.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No record found...", Toast.LENGTH_SHORT).show();
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor c = MainActivity.mSQLiteHelper.getData("SELECT id FROM RECORD");
                ArrayList<Integer> arrID = new ArrayList<Integer>();
                while (c.moveToNext()){
                    arrID.add(c.getInt(0));
                }
                showDialogDelete(arrID.get(i));
            }
        });
        
        fab_btn = findViewById(R.id.fab_btn);
        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , Add_Timer_Activity.class);
                startActivity(intent);
            }
        });

        privacy_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/document/d/e/2PACX-1vTbqiquu8QtUTKveg0KnCRMYQ7xP4iermicsJXm5k5Ocilq6J7_91I2hkMkgzG0jBYu_AdDsmM6z2lw/pub"));
                startActivity(browserIntent);
            }
        });

        thumbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , DonateActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showDialogDelete(final int idRecord) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(Html.fromHtml("<font color='#FFFFFF'>Select Action</font>"));
        builder.setPositiveButton(Html.fromHtml("<font color='#FFFFFF'>Delete</font>"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                try {
                    MainActivity.mSQLiteHelper.deleteData(idRecord);
                    Toast.makeText(MainActivity.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updateRecordList();
            }
        });
        builder.setNegativeButton(Html.fromHtml("<font color='#FFFFFF'>Start Timer</font>"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Intent intent = new Intent(MainActivity.this , Timer_Acticity.class);
                intent.putExtra("hh", hour);
                intent.putExtra("mm", minute);
                intent.putExtra("ss", seconds);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();

    }

    private void updateRecordList() {
        //get all data from sqlite
        Cursor cursor = mSQLiteHelper.getData("SELECT * FROM RECORD");
        mList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String hour = cursor.getString(1);
            String minute = cursor.getString(2);
            String second = cursor.getString(3);

            mList.add(new Model(id,hour, minute, second));
        }
        mAdapter.notifyDataSetChanged();
        if (mList.size()==0){
            mListView.setVisibility(View.GONE);
            layout_notimer.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No record found...", Toast.LENGTH_SHORT).show();
        }
    }
}
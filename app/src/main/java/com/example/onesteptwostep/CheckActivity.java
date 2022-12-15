package com.example.onesteptwostep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckActivity extends AppCompatActivity {
    int checknum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);


        Date date = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy년 MM월 dd일");
        String todaydate = sd.format(date);

        TextView datetext = findViewById(R.id.datetext);
        datetext.setText(todaydate);


        EditText et = findViewById(R.id.et);



        ImageButton backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button savebtn = findViewById(R.id.savebtn);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("checknum",checknum);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    public void onCheckBoxClicked(View v){
        EditText et = findViewById(R.id.et);
        checknum=0;
        if (((CheckBox)v).isChecked()) {
            checknum++;
            if(v.getId() == R.id.cb6) et.setFocusableInTouchMode(true);
        }
        else checknum--;

    }

}

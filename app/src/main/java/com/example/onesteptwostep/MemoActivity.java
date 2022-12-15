package com.example.onesteptwostep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MemoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        ImageButton backbtn = findViewById(R.id.backbtn);
        Button savebtn = findViewById(R.id.savebtn);

        //뒤로 가기 버튼을 눌렀을 때 - MainActivity로 전환
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("save",true);
                setResult(RESULT_OK,intent);
                finish();
            }
        });


    }
}

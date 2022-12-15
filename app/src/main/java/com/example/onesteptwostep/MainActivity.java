package com.example.onesteptwostep;
//커스텀 캘린더 구글링해서 나온 코드들 참고
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

//메인 액티비티(달력)
public class MainActivity extends AppCompatActivity {
    private String[] TempCal = new String[42];
    private boolean[] savememo = new boolean[42];
    private int[] checknum = new int[42];
    GregorianCalendar calendar;


    //+버튼 눌렀을 때 뜨는 팝업메뉴
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();



        Button plusbtn = findViewById(R.id.plusbtn);
        plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(MainActivity.this, view);
                popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId() == R.id.pluscheck){
                            Intent intent = new Intent(MainActivity.this, CheckActivity.class);
                            resultLauncher.launch(intent);
                        }
                        else{
                            Intent intent1 = new Intent(MainActivity.this, MemoActivity.class);
                            resultLauncher.launch(intent1);

                        }
                        return false;
                    }
                });
                popup.show();


            }
        });
    }
    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        boolean result1 = intent.getBooleanExtra("save",true);
                        int checknum = intent.getIntExtra("checknum",0);
//                        if(result1){
//
//                        }
                    }
                }
            });

    //calendar 인터페이스 구현
    protected void init() {
        calendar = new GregorianCalendar();
        Calsetting(calendar);
        RecyclerViewCreate();
//
    }

    public void onChangeMonthClicked(View view) {
        TextView yearmon = findViewById(R.id.yermon);
        if (view.getId() == R.id.prevbtn) {
            if (calendar.get(Calendar.MONTH) == 0){
                calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
                calendar.set(Calendar.MONTH,11);}
            else
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        } else {
            if (calendar.get(Calendar.MONTH) == 11) {
                calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
                calendar.set(Calendar.MONTH,0);}
            else
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        }
        Calsetting(calendar);
        yearmon.setText(calendar.get(Calendar.YEAR) + "." + (calendar.get(Calendar.MONTH)+1));


    }

    protected void RecyclerViewCreate() {
        RecyclerView calendarView = findViewById(R.id.calendarRecyclerView);
        Recycler_Adapter calendarAdapter = new Recycler_Adapter(getApplicationContext(), TempCal, savememo, checknum);

        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarView.setLayoutManager(layoutManager);
        calendarView.setAdapter(calendarAdapter);
    }

    protected void Calsetting(GregorianCalendar calendar) {
        GregorianCalendar cal = new GregorianCalendar(calendar.get(calendar.YEAR), calendar.get(calendar.MONTH), 1, 0, 0, 0);
        int week = cal.get(Calendar.DAY_OF_WEEK) -1 ;

       int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH) - 1;

       for (int i = 0; i < TempCal.length; i++) {
            if (i >=week && i<=(week+max)) {
                TempCal[i] = Integer.toString(i - week + 1);
           } else { // 이번달 일수
               TempCal[i]="";
           }
       }



        RecyclerViewCreate();
        }

}
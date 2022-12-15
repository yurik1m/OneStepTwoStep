package com.example.onesteptwostep;
//커스텀 캘린더 구글링해서 나온 코드들 참고
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

//메인 액티비티(달력)
public class MainActivity extends AppCompatActivity {
    private String[] TempCal = new String[42];
    private boolean savememo[] = new boolean[42];
    private int checknum[] = new int[42];
    private boolean daily;
    GregorianCalendar calendar;

    //알림


    public void sendAlerm(){

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if(daily){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int hour=20;
                int minute=0;
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),  AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }

    protected void initArray(){
        for(int i=0; i< savememo.length; i++){
            savememo[i]=false;
            checknum[i]=-1;
        }
    }


    //+버튼 눌렀을 때 뜨는 팝업메뉴
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initArray();

        Switch notibtn = findViewById(R.id.notibtn);
        notibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean on = ((Switch)view).isChecked();
                if(on) {
                    daily = true;
                    Toast.makeText(getApplicationContext(),"알림이 켜졌습니다.",Toast.LENGTH_LONG).show();
                }
                else{
                    daily=false;
                    Toast.makeText(getApplicationContext(),"알림이 해제되었습니다.",Toast.LENGTH_LONG).show();
                }
            }
        });


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
                        boolean result1 = intent.getBooleanExtra("save",false);
                        int num = intent.getIntExtra("checknum",0);
                        Date date = new Date();
                        SimpleDateFormat sd = new SimpleDateFormat("dd");
                        String todaydate = sd.format(date);
                        int day=0;
                        for(int i=0; i<TempCal.length; i++){
                            if(todaydate.equals(TempCal[i])) day=i;
                        }
                        if(result1){
                            savememo[day] = true;
                        }
                        checknum[day] = num;
                        RecyclerViewCreate();
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
        initArray();


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
           } else {
               TempCal[i]="";
           }
       }
        RecyclerViewCreate();
        }

}
package com.faresa.githubsearchuser.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.faresa.githubsearchuser.R;
import com.faresa.githubsearchuser.notifikasi.MyDaily;

import java.util.Calendar;
import java.util.Objects;

public class ActivitySetting extends AppCompatActivity {
    SharedPreferences sharedPref;
    Switch daily;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        daily = findViewById(R.id.daily);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        int dailyy = sharedPref.getInt("daily_notification",0);
        if (dailyy  ==1) {
            daily.setChecked(true);
        }else {
            daily.setChecked(false);
        }
        switchHandling();
    }

    private void switchHandling() {
        daily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    setDaily(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("daily_notification",1);
                    editor.commit();

                }else {
                    dailyOff(ActivitySetting.this);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("daily_notification",0);
                    editor.commit();

                }
            }
        });

    }
    public void setDaily(Context context){
        Intent intent = new Intent(context, MyDaily.class);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,9);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,101,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Log.d("daily","daily");
        if (alarmManager !=null){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

        }
    }

    public void dailyOff(Context context){
        Log.d("daily","daily");

        Intent intent = new Intent(context, MyDaily.class);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,101,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Objects.requireNonNull(alarmManager).cancel(pendingIntent);
    }
}

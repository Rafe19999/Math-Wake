package com.mathwake.app;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {
    private int hour = 7, minute = 0;
    private TextView time, status;
    private LinearLayout root;
    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        hour = AlarmScheduler.prefs(this).getInt("hour",7);
        minute = AlarmScheduler.prefs(this).getInt("minute",0);
        root = new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(42,70,42,30); root.setGravity(Gravity.CENTER_HORIZONTAL); root.setBackgroundColor(Color.rgb(12,17,33)); setContentView(root);
        label("MATHWAKE", 34, 0xFF76E0C4); label("Solve maths. Wake up.",16,Color.WHITE);
        time = label("",48,Color.WHITE); time.setPadding(0,65,0,38); displayTime();
        Button select = button("CHOOSE ALARM TIME"); select.setOnClickListener(v -> new TimePickerDialog(this,(view,h,m)-> {hour=h;minute=m;displayTime();},hour,minute,false).show());
        Button set = button("SET ALARM"); set.setOnClickListener(v -> setAlarm());
        Button cancel = button("CANCEL NEXT ALARM"); cancel.setOnClickListener(v -> {AlarmScheduler.cancel(this); refresh();});
        Button preview = button("TRY MATH CHALLENGE"); preview.setOnClickListener(v -> startActivity(new Intent(this, ChallengeActivity.class).putExtra("demo",true)));
        status = label("",16,Color.WHITE); status.setPadding(0,35,0,0); refresh();
        label("3 correct Class 10 maths answers to stop the alarm.\nOne-time alarm · no snooze · emergency backup recommended.",13,0xFFABB6D0).setPadding(0,45,0,0);
        if (Build.VERSION.SDK_INT >= 33 && checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS},1);
    }
    private TextView label(String s,int size,int color) {TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(color);t.setGravity(Gravity.CENTER);root.addView(t);return t;}
    private Button button(String s) {Button b = new Button(this);b.setText(s); b.setAllCaps(false); LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1,-2);p.setMargins(0,9,0,9);root.addView(b,p);return b;}
    private void displayTime(){time.setText(String.format(java.util.Locale.US,"%02d:%02d",hour,minute));}
    private void refresh(){if(status==null)return;boolean enabled=AlarmScheduler.prefs(this).getBoolean("enabled",false);long next=AlarmScheduler.prefs(this).getLong("next",0);status.setText(enabled ? "Alarm set: " + DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.SHORT).format(new Date(next)) : "No alarm scheduled");}
    private void setAlarm(){try { AlarmScheduler.schedule(this,hour,minute); refresh(); Toast.makeText(this,"Alarm is set!",Toast.LENGTH_SHORT).show(); }
      catch(SecurityException e){Toast.makeText(this,"Allow exact alarms in Android settings",Toast.LENGTH_LONG).show();if(Build.VERSION.SDK_INT>=31)startActivity(new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM));}
    }
    @Override protected void onResume(){super.onResume();refresh();}
}

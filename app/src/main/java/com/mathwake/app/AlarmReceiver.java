package com.mathwake.app;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
public class AlarmReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context context,Intent intent){
        AlarmScheduler.prefs(context).edit().putBoolean("enabled",false).remove("next").apply();
        Intent service = new Intent(context, AlarmService.class);
        if(Build.VERSION.SDK_INT>=26) context.startForegroundService(service); else context.startService(service);
    }
}

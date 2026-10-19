package com.mathwake.app;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
public class BootReceiver extends BroadcastReceiver {
 @Override public void onReceive(Context context,Intent intent){
    if(AlarmScheduler.prefs(context).getBoolean("enabled",false)){
        int h=AlarmScheduler.prefs(context).getInt("hour",7), m=AlarmScheduler.prefs(context).getInt("minute",0);
        try { AlarmScheduler.schedule(context,h,m); } catch(SecurityException ignored) {}
    }
 }
}

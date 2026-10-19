package com.mathwake.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.Calendar;

final class AlarmScheduler {
    static SharedPreferences prefs(Context context) { return context.getSharedPreferences("alarm", Context.MODE_PRIVATE); }
    static PendingIntent alarmIntent(Context context) {
        Intent i = new Intent(context, AlarmReceiver.class);
        return PendingIntent.getBroadcast(context, 1001, i, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }
    static long nextTime(int hour, int minute) {
        Calendar now = Calendar.getInstance(); Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour); c.set(Calendar.MINUTE, minute); c.set(Calendar.SECOND, 0); c.set(Calendar.MILLISECOND, 0);
        if (!c.after(now)) c.add(Calendar.DATE, 1);
        return c.getTimeInMillis();
    }
    static void schedule(Context context, int hour, int minute) {
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long when = nextTime(hour, minute);
        prefs(context).edit().putBoolean("enabled", true).putInt("hour", hour).putInt("minute", minute).putLong("next", when).apply();
        Intent show = new Intent(context, ChallengeActivity.class);
        PendingIntent showPending = PendingIntent.getActivity(context, 1002, show, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        mgr.setAlarmClock(new AlarmManager.AlarmClockInfo(when, showPending), alarmIntent(context));
    }
    static void cancel(Context context) {
        ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).cancel(alarmIntent(context));
        prefs(context).edit().putBoolean("enabled", false).remove("next").apply();
    }
}

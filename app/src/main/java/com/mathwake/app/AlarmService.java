package com.mathwake.app;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.content.pm.ServiceInfo;
import android.os.Build;
public class AlarmService extends Service {
 private MediaPlayer player;
 static final String CHANNEL="mathwake_alarm";
 @Override public void onCreate(){super.onCreate();
     NotificationManager mgr=getSystemService(NotificationManager.class);
     NotificationChannel ch=new NotificationChannel(CHANNEL,"MathWake alarms",NotificationManager.IMPORTANCE_HIGH);
     ch.setDescription("Alarm alerts"); ch.setSound(null,null);mgr.createNotificationChannel(ch);
     Intent challenge=new Intent(this,ChallengeActivity.class);
     challenge.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
     PendingIntent pi=PendingIntent.getActivity(this,200,challenge,PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);
     Notification n=new Notification.Builder(this,CHANNEL).setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
       .setContentTitle("MathWake alarm ringing").setContentText("Solve 3 problems to dismiss")
       .setCategory(Notification.CATEGORY_ALARM).setPriority(Notification.PRIORITY_MAX).setOngoing(true)
       .setContentIntent(pi).setFullScreenIntent(pi,true).build();
     if(Build.VERSION.SDK_INT>=29) startForeground(200,n,ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK);
     else startForeground(200,n);
     try { Uri uri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
         if(uri==null)uri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
         player=new MediaPlayer();player.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build());
         player.setDataSource(this,uri);player.setLooping(true);player.prepare();player.start();
     } catch(Exception e){android.util.Log.e("MathWake","Alarm sound unavailable",e);}
 }
 @Override public int onStartCommand(Intent intent,int flags,int startId){
     if(intent!=null && "STOP_AFTER_MATH".equals(intent.getAction())) {stopSelf();return START_NOT_STICKY;}
     return START_STICKY;
 }
 @Override public void onDestroy(){if(player!=null){try{player.stop();}catch(Exception ignored){}player.release();player=null;}super.onDestroy();}
 @Override public IBinder onBind(Intent intent){return null;}
}

package com.iitism2k16.webnode.myalarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by imkiller on 19/03/2018.
 */

public class RingtonePlayingService extends Service {


        MediaPlayer media_songs;
        int startId;
        boolean isRunning;


        @Nullable

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Log.i("LocalService", "Received start id " + startId + ": " + intent);

            //fetch extra
            String state=intent.getExtras().getString("extra");


            assert state!=null;
            switch (state) {
                case "alarm on":
                    startId = 1;
                    break;
                case "alarm off":
                    startId = 0;
                    break;
                default:
                    startId = 0;
                    break;
            }
            NotificationManager notify_manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//no music playing and button=alarm on
            if(!this.isRunning &&  startId==1){
                media_songs = MediaPlayer.create(this,R.raw.ringtone);
                media_songs.start();
                this.isRunning=true;
                this.startId=0;

                //setUp Notification using notification manager

                //srtup intent
                Intent intent_main_activity=new Intent(this.getApplicationContext(),MainActivity.class);

                //setPendingIntent
                PendingIntent pending_intent_main_activity=PendingIntent.getActivity(this,0,
                        intent_main_activity,0);

                Notification notification_popup=new Notification.Builder(this)
                        .setContentTitle("Wake Up")
                        .setContentText("Click For turnOff")
                        .setContentIntent(pending_intent_main_activity)
                        .setAutoCancel(false)
                        .addAction(R.drawable.clock,"Close Alarm?",
                                pending_intent_main_activity)
                        .setStyle(new Notification.BigTextStyle()
                                .bigText("s"))
                        .setSmallIcon(R.mipmap.notifyalarm)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                        .build();
                        notify_manager.notify(0,notification_popup);
            }
//music playing and button=alarm off
            else if(this.isRunning && startId==0){
                notify_manager.cancelAll();
            media_songs.stop();
            media_songs.reset();
            this.isRunning=false;
            this.startId=0;
            }
//button=random no music button alarm off(do nothing)
            else if(!this.isRunning && startId==0){
                this.isRunning=false;
                this.startId=0;
            }
//music playing and button=alarm on (do nothing)
            else if(this.isRunning && startId==1){
                this.isRunning=true;
                this.startId=1;
            }
//
            else {
            Log.e("Somehow u are here ","Ok");
            }
            return START_NOT_STICKY;
        }
        @Override
        public void onDestroy() {
            Log.e("On Destroy Called ","Bye");
            super.onDestroy();
            this.isRunning=false;

        }
}
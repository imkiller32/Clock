package com.iitism2k16.webnode.myalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by imkiller on 19/03/2018.
 */

public class Alarm_Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("We Are in the Receiver"," Yeah ! ");

        //extra string for intent
        String get_your_string=intent.getExtras().getString("extra");

        Log.e("We is the key?",get_your_string);
        //create intent to ringTone service
        Intent service_intent=new Intent(context,RingtonePlayingService.class);
        service_intent.putExtra("extra",get_your_string);
        //start ringtone Service
        context.startService(service_intent);

    }
}

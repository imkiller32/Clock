package com.iitism2k16.webnode.myalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //to make our alarm manager
    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    TextView Update_text;
    Context context;
    PendingIntent pending_intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context=this;

        final Intent my_intent=new Intent(this.context,Alarm_Receiver.class);
        //initialize Alarm manager
        alarm_manager=(AlarmManager) getSystemService(ALARM_SERVICE);

        //initialize time picker
        alarm_timepicker=(TimePicker)findViewById(R.id.timePicker);

        //initialize text Update box
        Update_text=(TextView)findViewById(R.id.Update_text);

        final Calendar calendar=Calendar.getInstance();

        //initialize Button
        Button alarm_on=(Button)findViewById(R.id.alarm_on);

        //create an intent for To the AlarmReceiver Class


        //onClick for Start
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                calendar.set(Calendar.HOUR_OF_DAY,alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE,alarm_timepicker.getMinute());

                int hour=alarm_timepicker.getHour();
                int minute=alarm_timepicker.getMinute();
                String hour_string=String.valueOf(hour);
                String minute_string=String.valueOf(minute);

                if(hour>12)
                {
                    hour_string=String.valueOf(hour-12);
                }
                if(minute<10)
                {
                    //10:7-->10:07
                    minute_string="0"+String.valueOf(minute);
                }
                Update_text.setText("Alarm Set to "+hour_string+":"+minute_string);

                //put extra string to intent to tell that alarm on is pressed
                my_intent.putExtra("extra","alarm on");

                //create the pending intent
                pending_intent=PendingIntent.getBroadcast(MainActivity.this,
                        0,my_intent,PendingIntent.FLAG_UPDATE_CURRENT);
                //set the alarm manager
                alarm_manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pending_intent);

            }
        });
        Button alarm_off=(Button)findViewById(R.id.alarm_off);
        //onClick for Stop
        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update_text.setText("Alarm Off");
                //cancel the later alarms set
                alarm_manager.cancel(pending_intent);

                //extra string
                my_intent.putExtra("extra","alarm off");

                //Stop The Current Playing Alarm
                sendBroadcast(my_intent);
            }
        });

    }


}

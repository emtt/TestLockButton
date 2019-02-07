package emt.c20.testlockbutton.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.Toast;

public class ScreenReceiver extends BroadcastReceiver {

    public static boolean wasScreenOn = true;
    //Vibrator vibe;
    public Long a, seconds_screenoff, OLD_TIME, diffrence, seconds_screenon2, seconds_screenon, actual_diff;
    private boolean OFF_SCREEN, sent_msg, ON_SCREEN;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        //vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);


        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            a = System.currentTimeMillis();
            seconds_screenoff = a;
            OLD_TIME = seconds_screenoff;
            OFF_SCREEN = true;
            wasScreenOn = false;
            new CountDownTimer(5000, 200) {

                public void onTick(long millisUntilFinished) {


                    if (ON_SCREEN) {
                        if (seconds_screenon != 0 && seconds_screenoff != 0) {

                            actual_diff = cal_diff(seconds_screenon, seconds_screenoff);
                            if (actual_diff <= 4000) {
                                sent_msg = true;
                                if (sent_msg) {

                                    Toast.makeText(context, "POWER BUTTON CLICKED 2 TIMES", Toast.LENGTH_LONG).show();
                                    seconds_screenon = 0L;
                                    seconds_screenoff = 0L;
                                    sent_msg = false;

                                }
                            } else {
                                seconds_screenon = 0L;
                                seconds_screenoff = 0L;

                            }
                        }
                    }
                }

                public void onFinish() {
                    seconds_screenoff = 0L;
                }
            }.start();
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            a = System.currentTimeMillis();
            seconds_screenon = a;
            OLD_TIME = seconds_screenoff;
            wasScreenOn = true;
            new CountDownTimer(5000, 200) {

                public void onTick(long millisUntilFinished) {
                    if (OFF_SCREEN) {
                        if (seconds_screenon != 0 && seconds_screenoff != 0) {
                            actual_diff = cal_diff(seconds_screenon, seconds_screenoff);
                            if (actual_diff <= 4000) {
                                sent_msg = true;
                                if (sent_msg) {

                                    Toast.makeText(context, "POWER BUTTON CLICKED 2 TIMES", Toast.LENGTH_LONG).show();
                                    seconds_screenon = 0L;
                                    seconds_screenoff = 0L;
                                    sent_msg = false;


                                }
                            } else {
                                seconds_screenon = 0L;
                                seconds_screenoff = 0L;

                            }
                        }
                    }

                }

                public void onFinish() {

                    seconds_screenon = 0L;
                }
            }.start();


        }



/*
        Log.e("LOB","onReceive");
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            // do whatever you need to do here
            wasScreenOn = false;
            Log.e("LOB","wasScreenOn"+wasScreenOn);



        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            // and do whatever you need to do here
            wasScreenOn = true;

        }else if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){
            Log.e("LOB","userpresent");
            Log.e("LOB","wasScreenOn"+wasScreenOn);
            String url = "http://www.stackoverflow.com";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setData(Uri.parse(url));
            context.startActivity(i);
        }*/
    }

    private long cal_diff(long seconds_screenon2, long seconds_screenoff2) {
        if (seconds_screenon2 >= seconds_screenoff2) {
            diffrence = (seconds_screenon2) - (seconds_screenoff2);
            seconds_screenon2 = 0;
            seconds_screenoff2 = 0;
        } else {
            diffrence = (seconds_screenoff2) - (seconds_screenon2);
            seconds_screenon2 = 0;
            seconds_screenoff2 = 0;
        }

        return diffrence;
    }

}

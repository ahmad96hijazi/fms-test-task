package com.fms.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = "Action: " + intent.getAction();
        String log = "Action: " + intent.getAction() + "\n" +
                "URI: " + intent.toUri(Intent.URI_INTENT_SCHEME) + "\n";
        Log.d(TAG, log);
        Toast.makeText(context, action, Toast.LENGTH_LONG).show();
        Log2S.getInstance().send(TAG, context, intent.getAction());

        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.d(TAG, "ACTION_BOOT_COMPLETED");
            Intent serviceIntent = new Intent(context, MyService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent);
            } else {
                context.startService(serviceIntent);
            }
        }
    }
}
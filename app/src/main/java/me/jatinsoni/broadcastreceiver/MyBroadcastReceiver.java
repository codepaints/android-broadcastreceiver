package me.jatinsoni.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Broadcast Receiver Triggered!", Toast.LENGTH_SHORT).show();
        Log.d("BroadcastReceiver", "Broadcast Receiver Triggered!");
    }
}

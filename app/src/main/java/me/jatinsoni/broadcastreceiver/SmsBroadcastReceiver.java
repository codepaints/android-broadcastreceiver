package me.jatinsoni.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    public static final String SMS_KEY = "pdus";
    public static MessageListener messageListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Broadcast Receiver Triggered!", Toast.LENGTH_SHORT).show();
        Log.d("BroadcastReceiver", "Broadcast Receiver Triggered!");

        Bundle data = intent.getExtras();
        String body = "";

        if (data != null) {

            Object[] smsData = (Object[]) data.get(SMS_KEY);

            for (int i = 0; i < smsData.length; i++) {

                SmsMessage sms = SmsMessage.createFromPdu((byte[]) smsData[i]);

                body += sms.getMessageBody();
                String address = sms.getOriginatingAddress();
                messageListener.messageReceived(address, body);

            }

        }

    }

    public static void bindListener(MessageListener listener) {
        messageListener = listener;
    }
}

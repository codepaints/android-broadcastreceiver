package me.jatinsoni.broadcastreceiver;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MessageListener {

    private int SMS_PERMISSION_CODE = 1;
    String[] PERMISSIONS = {
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS
    };

    TextView smsFrom, smsBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        smsFrom = findViewById(R.id.sms_from);
        smsBody = findViewById(R.id.sms_body);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) + ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You have granted this permission", Toast.LENGTH_SHORT).show();
        } else {
            requestReceiveSmsPermission();
        }

        MyBroadcastReceiver.bindListener(this);

    }

    private void requestReceiveSmsPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission Required")
                    .setMessage("Allow to receive and read SMS")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, SMS_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, SMS_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void messageReceived(String address, String body) {
        smsFrom.setText(address);
        smsBody.setText(body);
        Toast.makeText(this, "From: " + address + "\n" + body, Toast.LENGTH_SHORT).show();
    }
}
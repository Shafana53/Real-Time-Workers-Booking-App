package com.example.labourbooking;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MessageSender {
    private static final int SMS_PERMISSION_REQUEST_CODE = 1;

    private String phoneNumbers;
    private String message;
    private Context context;

    public MessageSender(Context context,String phoneNumbers, String message) {
        this.context = context;
        this.phoneNumbers = phoneNumbers;
        this.message = message;
    }

    public void sendMessage() {
        // Check if the SMS permission is already granted
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, handle accordingly
            // You can display a message to the user or take any other appropriate action
        } else {
            // Permission is granted, send the message
            sendMessage(phoneNumbers);
            Toast.makeText(context, phoneNumbers+"You are booked", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendMessage(String phno) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phno, null, message, null, null);
    }
}
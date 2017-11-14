package com.mooo.hairyone.td5tester;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;

public  class Util {

    public static void setImageButtonState(ImageButton imageButton, boolean enabled) {
        imageButton.setEnabled(enabled);
        if (enabled) {
            imageButton.getBackground().setColorFilter(null);
        } else {
            imageButton.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        }
    }

    public static String byte_array_to_hex(byte[] data, int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(String.format("%02X ", data[i] & 0xFF));
        }
        return sb.toString();
    }

    public static String integer_to_binary(int value) {
        return String.format("%8s", Integer.toBinaryString(value & 0xFF)).replace(' ', '0');
    }

}
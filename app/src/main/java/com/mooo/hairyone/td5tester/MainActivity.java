package com.mooo.hairyone.td5tester;

import android.support.v7.app.AppCompatActivity;

//import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ftdi.j2xx.D2xxManager;
import com.ftdi.j2xx.FT_Device;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    D2xxManager ftdid2xx = null;
    FT_Device ftDevice = null;
    int device_count;

    TextView LoggingTextView;
    Button ConnectButton;
    Button ClearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectButton = (Button) findViewById(R.id.connectButton);
        ClearButton = (Button) findViewById(R.id.clearButton);
        LoggingTextView = (TextView) findViewById((R.id.loggingTextView));

        LoggingTextView.setMovementMethod(new ScrollingMovementMethod());

        ConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect();
            }
        });

        ClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log_clear();
            }
        });

    }

    public void log_clear() {
        LoggingTextView.setText("");
    }

    public void log_append(String text) {
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        Date now = new Date();
        LoggingTextView.append(String.format("%s : %s\n", sdf.format(now), text));
    }

    public void connect() {
        // Toast.makeText(getApplicationContext(), "Connecting ...", Toast.LENGTH_SHORT).show();
        // log_clear();
        try {
            ftdid2xx = D2xxManager.getInstance(getApplicationContext());
            device_count = ftdid2xx.createDeviceInfoList(getApplicationContext());

            log_append("");
            log_append(String.format("connecting", device_count));
            log_append(String.format("device_count=%d", device_count));

            if (device_count > 0) {
                D2xxManager.FtDeviceInfoListNode[] deviceList = new D2xxManager.FtDeviceInfoListNode[device_count];
                ftdid2xx.getDeviceInfoList(device_count, deviceList);

                log_append(String.format("serial_number=%s", deviceList[0].serialNumber == null ? "null" : deviceList[0].serialNumber));
                log_append(String.format("description=%s", deviceList[0].description == null ? "null" : deviceList[0].description));
                log_append(String.format("location=%d", deviceList[0].location));
                log_append(String.format("id=%s", deviceList[0].id));
                log_append(String.format("library_version=%d", D2xxManager.getLibraryVersion()));
                log_append(String.format("type=%d", deviceList[0].type));
                log_append(String.format("type_name=%s", get_device_type(deviceList[0].type)));

            }

        } catch (D2xxManager.D2xxException e) {
            log_append(String.format("error=%s", e.getMessage()));
            e.printStackTrace();
        }

    }

    public String get_device_type(int device_type) {
        String result = "unknown device";
        switch (device_type) {
            case D2xxManager.FT_DEVICE_232B: result = "FT232B"; break;
            case D2xxManager.FT_DEVICE_8U232AM: result = "FT8U232AM"; break;
            case D2xxManager.FT_DEVICE_UNKNOWN: result = "Unknown"; break;
            case D2xxManager.FT_DEVICE_2232: result = "FT2232"; break;
            case D2xxManager.FT_DEVICE_232R: result = "FT232R"; break;
            case D2xxManager.FT_DEVICE_2232H: result = "FT2232H"; break;
            case D2xxManager.FT_DEVICE_4232H: result = "FT4232H"; break;
            case D2xxManager.FT_DEVICE_232H: result = "FT232H"; break;
            case D2xxManager.FT_DEVICE_X_SERIES: result = "FTDI X_SERIES"; break;
            default: result = "FT232B"; break;
        }
        return result;
    }

}

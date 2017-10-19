package com.mooo.hairyone.td5tester;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectButton = (Button) findViewById(R.id.connectButton);
        LoggingTextView = (TextView) findViewById((R.id.loggingTextView));

        ConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect();
            }
        });

    }

    public void log_clear() {
        LoggingTextView.setText("");
    }

    public void log_append(String text) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        LoggingTextView.append(String.format("%s : %s\n", sdf.format(now), text));
    }

    public void connect() {
        Toast.makeText(getApplicationContext(), "Connecting ...", Toast.LENGTH_SHORT).show();
        log_clear();
        try {
            ftdid2xx = D2xxManager.getInstance(getApplicationContext());
            device_count = ftdid2xx.createDeviceInfoList(getApplicationContext());

            log_append(String.format("device_count=%d", device_count));

            if (device_count > 0) {
                D2xxManager.FtDeviceInfoListNode[] deviceList = new D2xxManager.FtDeviceInfoListNode[device_count];
                ftdid2xx.getDeviceInfoList(device_count, deviceList);

                log_append(String.format("serial_number=%s", deviceList[0].serialNumber == null ? "null" : deviceList[0].serialNumber));
                log_append(String.format("description=%s", deviceList[0].description == null ? "null" : deviceList[0].description));
            }

        } catch (D2xxManager.D2xxException e) {
            log_append(String.format("error=%s", e.getMessage()));
            e.printStackTrace();
        }

    }

}

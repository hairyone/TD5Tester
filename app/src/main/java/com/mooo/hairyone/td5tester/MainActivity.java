package com.mooo.hairyone.td5tester;

import android.content.Context;
import android.graphics.Color;
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

    Context application_context;


    D2xxManager d2xx_manager = null;
    D2xxManager.FtDeviceInfoListNode[] device_list = null;
    FT_Device ft_device = null;

    int device_count;

    TextView LoggingTextView;
    Button ConnectButton;
    Button ClearButton;
    boolean connected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        application_context = getApplicationContext();

        ConnectButton = (Button) findViewById(R.id.connectButton);
        ClearButton = (Button) findViewById(R.id.clearButton);
        LoggingTextView = (TextView) findViewById((R.id.loggingTextView));
        LoggingTextView.setMovementMethod(new ScrollingMovementMethod());
        LoggingTextView.setBackgroundColor(Color.parseColor("#FFFFA5"));

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
        // LoggingTextView.append(String.format("%s : %s\n", sdf.format(now), text));
        LoggingTextView.append(String.format("%s\n", text));
    }

    public void connect() {
        log_append("");
        log_append(String.format("connecting"));

        try {
            d2xx_manager = D2xxManager.getInstance(application_context);
            device_count = d2xx_manager.createDeviceInfoList(application_context);
            device_list = new D2xxManager.FtDeviceInfoListNode[device_count];
            d2xx_manager.getDeviceInfoList(device_count, device_list);

            log_append(String.format("library_version=%d", D2xxManager.getLibraryVersion()));
            log_append(String.format("device_count=%d", device_count));

            for (D2xxManager.FtDeviceInfoListNode ft_device_info_list_node : device_list) {
                log_device_info(ft_device_info_list_node);
            }

            fast_init();

        } catch (D2xxManager.D2xxException e) {
            log_append(String.format("error=%s", e.getMessage()));
            e.printStackTrace();
        }

    }

    public void log_device_info(D2xxManager.FtDeviceInfoListNode ft_device_info_list_node) {
        log_append(String.format("serial_number=%s", ft_device_info_list_node.serialNumber == null ? "null" : ft_device_info_list_node.serialNumber));
        log_append(String.format("description=%s", ft_device_info_list_node.description == null ? "null" : ft_device_info_list_node.description));
        log_append(String.format("location=%d", ft_device_info_list_node.location));
        log_append(String.format("id=%s", ft_device_info_list_node.id));
        log_append(String.format("type=%d", ft_device_info_list_node.type));
        log_append(String.format("type_name=%s", get_device_type(ft_device_info_list_node.type)));
    }

    public void fast_init() {
            if (device_count > 0) {
                log_append("fast_init");
                ft_device = d2xx_manager.openByIndex(application_context, 0);
                ft_device.setBaudRate(10400);
                ft_device.setDataCharacteristics(D2xxManager.FT_DATA_BITS_8, D2xxManager.FT_STOP_BITS_1, D2xxManager.FT_PARITY_NONE);

                //Mask = 0x00  // all input
                //Mask = 0xFF  // all output
                //Mask = 0x0F  // upper nibble input,lower nibble output

                //#define PIN_TX  0x01  /* Orange wire on FTDI cable */
                //#define PIX_RX  0x02  /* Yellow */
                //#define PIN_RTS 0x04  /* Green */
                //#define PIN_CTS 0x08  /* Brown */
                //#define PIN_DTR 0x10
                //#define PIN_DSR 0x20
                //#define PIN_DCD 0x40
                //#define PIN_RI  0x80

                ft_device.setBitMode((byte) 0x01, D2xxManager.FT_BITMODE_ASYNC_BITBANG);

                byte[] HI = new byte [] {(byte)0x01};
                byte[] LO = new byte [] {(byte)0x00};

                long endTime = System.currentTimeMillis();

                try {
                    ft_device.write(HI, 1);
                    Thread.sleep(50);
                    ft_device.write(LO, 1);
                    Thread.sleep(25);
                    ft_device.write(HI, 1);
                    Thread.sleep(25);
                } catch (Exception ex) {
                    log_append(ex.getMessage());
                }

                ft_device.setBitMode((byte) 0xFF, D2xxManager.FT_BITMODE_RESET);
                ft_device.close();
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

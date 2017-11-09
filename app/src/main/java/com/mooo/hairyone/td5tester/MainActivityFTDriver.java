package com.mooo.hairyone.td5tester;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

import jp.ksksue.driver.serial.FTDriver;

public class MainActivityFTDriver extends AppCompatActivity {

    private FTDriver ft_driver;
    private static final String ACTION_USB_PERMISSION = "com.mooo.hairyone.td5tester.USB_PERMISSION";

    TextView tvInfo;
    Button btConnect;
    Button btDisconnect;
    Button btFastInit;
    Button btClear;

    byte[] response = new byte[TD5_Constants.BUFFER_SIZE];

    boolean connected = false;
    TD5_Requests td5_requests = null;

    private static final int LOG_MSG = 1;
    private static final int SET_CONNECTION_STATE = 2;

    private final Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case (LOG_MSG):
                    log_append((String) msg.obj);
                    break;
                case (SET_CONNECTION_STATE):
                    connected = (boolean) msg.obj;
                    break;
            }
        }
    };


    public void log_msg(String msg) {
        myHandler.sendMessage(Message.obtain(myHandler, LOG_MSG, msg));
    }

    public void set_connection_state(boolean connected) {
        myHandler.sendMessage(Message.obtain(myHandler, LOG_MSG, connected));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btConnect = (Button) findViewById(R.id.btConnect);
        btClear = (Button) findViewById(R.id.btClear);
        tvInfo = (TextView) findViewById(R.id.tvInfo);
        tvInfo.setMovementMethod(new ScrollingMovementMethod());
        tvInfo.setBackgroundColor(Color.parseColor("#FFFFA5"));

        ft_driver = new FTDriver((UsbManager)getSystemService(Context.USB_SERVICE));

        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        registerReceiver(mUsbReceiver, filter);

        PendingIntent permissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        ft_driver.setPermissionIntent(permissionIntent);

        btConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        openDevice();
                        fast_init();
                    }
                });
                thread.start();
            }
        });

        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log_clear();
            }
        });

        td5_requests = new TD5_Requests();
    }

    void log_clear() {
        tvInfo.setText("");
    }

    void log_append(String text) {
        tvInfo.append(String.format("%s\n", text));
        Log.w("TD5Tester", text);
    }

    public void openDevice() {
        if (!ft_driver.isConnected()) {
            ft_driver.begin(10400);
            log_msg(String.format("ft_driver=%s", ft_driver.getDevice().getProductName()));
        }
    }

    public void fast_init() {
        byte[] HI = new byte[]{(byte) 0x01};
        byte[] LO = new byte[]{(byte) 0x00};

        try {
            if (ft_driver != null && ft_driver.isConnected()) {
                ft_driver.setSerialPropertyDataBit(FTDriver.FTDI_SET_DATA_BITS_8, FTDriver.CH_A);
                ft_driver.setSerialPropertyStopBits(FTDriver.FTDI_SET_DATA_STOP_BITS_1, FTDriver.CH_A);
                ft_driver.setSerialPropertyParity(FTDriver.FTDI_SET_DATA_PARITY_EVEN, FTDriver.CH_A);

                ft_driver.setBitmode(true, 0x01, FTDriver.FTDI_MPSSE_BITMODE_BITBANG);
                ft_driver.write(HI); Thread.sleep(500);
                ft_driver.write(LO); Thread.sleep(25);
                ft_driver.write(HI); Thread.sleep(25);
                ft_driver.setBitmode(false, 0x00, FTDriver.FTDI_MPSSE_BITMODE_RESET);

                if (get_pid(TD5_Pids.Pid.INIT_FRAME) && get_pid(TD5_Pids.Pid.START_DIAGNOSTICS) && get_pid(TD5_Pids.Pid.REQUEST_SEED)) {
                    int seed = (short) (response[3] << 8 | response[4]);
                    int key = generate_key(seed);
                    td5_requests.request.get(TD5_Pids.Pid.KEY_RETURN).request[3] = (byte) (key >> 8);
                    td5_requests.request.get(TD5_Pids.Pid.KEY_RETURN).request[4] = (byte) (key & 0xFF);
                    connected = get_pid(TD5_Pids.Pid.KEY_RETURN);
                }
            } else {
                log_msg("not connected");
            }
        } catch (Exception ex) {
            log_msg(ex.toString());
        }

    }

    int generate_key(int seedin) {
        // we have to use an int because java doesn't do unsigned values so we use the lower 16 bits of an int
        int seed = seedin;
        int count = ((seed >> 0xC & 0x8) + (seed >> 0x5 & 0x4) + (seed >> 0x3 & 0x2) + (seed & 0x1)) + 1; // count == byte (0 .. 255)
        // Log.d(String.format("\ncount=%d", count));
        for (int idx = 0; idx < count; idx++) {
            int tap = ((seed >> 1) + (seed >> 2) + (seed >> 8) + (seed >> 9)) & 1; // tap byte (0 .. 1)
            int tmp = (seed >> 1 & 0xFFFF) | ( tap << 0xF); // short (0 .. 65535)
            if ( (seed >> 0x3 & 1) == 1 && (seed >> 0xD & 1) == 1) {
                seed = tmp & ~1;
            } else {
                seed = tmp | 1;
            }
        }
        return seed;
    }

    boolean get_pid(TD5_Pids.Pid pid) {
        boolean result = false;
        send(pid);
        int len = readResponse(pid);
        if (len > 1) {
            byte cs1 = response[len - 1];
            byte cs2 = checksum(response, len - 1);
            if (cs1 == cs2) {
                if (response[1] != 0x7F) {
                    result = true;
                }
            }
        }
        return result;
    }

    void send(TD5_Pids.Pid pid) {
        int len     = td5_requests.request.get(pid).request.length;
        byte[] data = td5_requests.request.get(pid).request;
        String name = td5_requests.request.get(pid).name;

        data[len - 1] = checksum(data, len - 1);
        log_msg(name);
        log_data(data, len, true);
        ft_driver.write(data);
    }

    byte checksum(byte[] data, int len) {
        byte crc = 0;
        for (int i = 0; i < len; i++) {
            crc = (byte) (crc + data[i]);
        }
        return crc;
    }

    static String byte_array_to_hex(byte[] data, int len){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++){
            sb.append(String.format("%02X ", data[i] & 0xFF));
        }
        return sb.toString();
    }

    int readResponse(TD5_Pids.Pid pid) {
        // REQUESTING INIT FRAME
        // >> 81 13 F7 81 0C
        // << FF FF 03 C1 57 8F AA
        // REQUESTING START DIAGNOSTICS
        // >> 02 10 A0 B2
        // << FF FF 01 50 51
        // REQUESTING REQUEST SEED
        // >> 02 27 01 2A
        // << FF FF FF FF 04 67 01 52 25 E3
        // REQUESTING KEY RETURN
        // >> 04 27 02 14 89 CA
        // << FF FF FF FF 02 67 02 6B


        // FTDriver specifies 0 (infinite) as the UsbDeviceConnection.bulkTransfer() timeout
        // unless the device is CDC in which case 100ms is used

        int bytes_read = ft_driver.read(response);
        log_msg(String.format("bytes_read=%d", bytes_read));
        log_data(response, bytes_read, false);
        return bytes_read;
    }

    void log_data(byte[] data, int len, boolean is_tx) {
        log_msg(String.format("%s %s", is_tx ? ">>" : "<<", byte_array_to_hex(data, len)));
    }

    public void onDestroy() {
        super.onDestroy();
        ft_driver.end();
    }

    BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                ft_driver.usbAttached(intent);
                log_msg("usb connected");
            } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                ft_driver.usbDetached(intent);
                log_msg("usb disconnected");
            }
        }
    };

}

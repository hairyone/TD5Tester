package com.mooo.hairyone.td5tester;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

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

    private static final String ACTION_USB_PERMISSION = "com.mooo.hairyone.td5tester.USB_PERMISSION";
    PendingIntent mPermissionIntent;
    UsbDevice mUsbDevice = null;
    UsbInterface mUsbInterface = null;
    UsbEndpoint mUsbEndpointIn = null;
    UsbEndpoint mUsbEndpointOut = null;
    UsbDeviceConnection mUsbDeviceConnection = null;

    private final BroadcastReceiver mUsbDeviceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                mUsbDevice = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                log_msg(String.format("usb_attached=%s", mUsbDevice.getDeviceName()));
                // usb_open();
            } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                UsbDevice usbDevice = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                log_msg(String.format("usb_detached=%s", usbDevice.getDeviceName()));
                if (usbDevice != null && usbDevice == mUsbDevice) {
                    usb_close();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btConnect = (Button) findViewById(R.id.btConnect);
        btDisconnect= (Button) findViewById(R.id.btDisconnect);
        btFastInit = (Button) findViewById(R.id.btFastInit);
        btClear = (Button) findViewById(R.id.btClear);
        tvInfo = (TextView) findViewById(R.id.tvInfo);
        tvInfo.setMovementMethod(new ScrollingMovementMethod());
        tvInfo.setBackgroundColor(Color.parseColor("#FFFFA5"));

        td5_requests = new TD5_Requests();

        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        registerReceiver(mUsbDeviceReceiver, filter);

        registerReceiver(mUsbDeviceReceiver, new IntentFilter(UsbManager.ACTION_USB_DEVICE_ATTACHED));
        registerReceiver(mUsbDeviceReceiver, new IntentFilter(UsbManager.ACTION_USB_DEVICE_DETACHED));

        btConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        usb_open();
                    }
                });
                thread.start();
            }
        });

        btFastInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
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
        btDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usb_close();
            }
        });

    }

    private void usb_open(){
        mUsbInterface = null;
        mUsbEndpointIn = null;
        mUsbEndpointOut = null;

        if (mUsbDevice == null){
            UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
            HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
            Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
            while (deviceIterator.hasNext()) {
                UsbDevice usbDevice = deviceIterator.next();
                if (usbDevice.getVendorId() == TD5_Constants.FTDI_VENDOR_ID && usbDevice.getProductId() == TD5_Constants.FTDI_PRODUCT_ID) {
                    mUsbDevice = usbDevice;
                }
            }
        }

        if (mUsbDevice == null){
            log_msg("FTDI device not found");
        } else {
            log_msg(String.format("mUsbDevice=%s", mUsbDevice.toString()));
            for (int i=0; i < mUsbDevice.getInterfaceCount(); i++){
                UsbInterface usbInterface = mUsbDevice.getInterface(i);
                UsbEndpoint usbEndpointOut = null;
                UsbEndpoint usbEndpointIn = null;

                int endpointCount = usbInterface.getEndpointCount();
                if (endpointCount >= 2) {
                    for(int j=0; j < endpointCount; j++){
                        if (usbInterface.getEndpoint(j).getType() == UsbConstants.USB_ENDPOINT_XFER_BULK && usbInterface.getEndpoint(j).getDirection() == UsbConstants.USB_DIR_OUT) {
                            usbEndpointOut = usbInterface.getEndpoint(j);
                        }
                        if (usbInterface.getEndpoint(j).getType() == UsbConstants.USB_ENDPOINT_XFER_BULK && usbInterface.getEndpoint(j).getDirection() == UsbConstants.USB_DIR_IN) {
                            usbEndpointIn = usbInterface.getEndpoint(j);
                        }
                    }
                    if (usbEndpointOut != null && usbEndpointIn != null){
                        mUsbInterface = usbInterface;
                        mUsbEndpointOut = usbEndpointOut;
                        mUsbEndpointIn = usbEndpointIn;
                    }
                }
            }

            if( mUsbInterface == null){
                log_msg("no suitable interface found");
            } else {
                log_msg(String.format("mUsbInterface=%s", mUsbInterface.toString()));
            }
        }

        if (mUsbInterface != null){
            open_uart();
        }
    }

    private boolean open_uart(){
        boolean success = false;

        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        Boolean has_permission = manager.hasPermission(mUsbDevice);

        if (!has_permission) {
            manager.requestPermission(mUsbDevice, mPermissionIntent);
            has_permission = manager.hasPermission(mUsbDevice);
        }

        log_msg(String.format("has_permission=%b", has_permission));

        if (has_permission) {
            mUsbDeviceConnection = manager.openDevice(mUsbDevice);
            if (mUsbDeviceConnection != null) {
                mUsbDeviceConnection.claimInterface(mUsbInterface, true);
                control_transfer(FTDI.SIO_RESET,         FTDI.SIO_RESET_SIO,         FTDI.CH_A);
                control_transfer(FTDI.SIO_RESET,         FTDI.SIO_RESET_PURGE_RX,    FTDI.CH_A);
                control_transfer(FTDI.SIO_RESET,         FTDI.SIO_RESET_PURGE_TX,    FTDI.CH_A);
                control_transfer(FTDI.SIO_SET_FLOW_CTRL, FTDI.SIO_DISABLE_FLOW_CTRL, FTDI.CH_A);
                control_transfer(FTDI.SIO_SET_DATA,      FTDI.LINE_8N1,              FTDI.CH_A);
                control_transfer(FTDI.SIO_SET_BAUDRATE,  FTDI.BAUDRATE_10400);
                success = true;
            }
        }

        return success;
    }

    public void log_msg(String msg) {
        myHandler.sendMessage(Message.obtain(myHandler, LOG_MSG, msg));
    }

    public void set_connection_state(boolean connected) {
        myHandler.sendMessage(Message.obtain(myHandler, LOG_MSG, connected));
    }

    public void usb_close() {
        if (mUsbDeviceConnection != null) {
            if (mUsbInterface != null) {
                mUsbDeviceConnection.releaseInterface(mUsbInterface);
                mUsbInterface = null;
            }
            mUsbDeviceConnection.close();
            mUsbDeviceConnection = null;
        } else {
            log_msg("not connected");
        }
        mUsbDevice = null;
        mUsbInterface = null;
        mUsbEndpointIn = null;
        mUsbEndpointOut = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        usb_close();
    }

    void log_clear() {
        tvInfo.setText("");
    }

    void log_append(String text) {
        tvInfo.append(String.format("%s\n", text));
        Log.w("TD5Tester", text);
    }

    public int control_transfer(int request, int value) {
        return control_transfer(request, value, 0);
    }
    public int control_transfer(int request, int value, int index) {
        int rc = mUsbDeviceConnection.controlTransfer(FTDI.REQ_OUT, request, value, index, null, 0x00, FTDI.WRITE_TIMEOUT);
        if (rc < 0) {
            Log.e(TAG, String.format("request=%d, value=%d, index=%d, rc=%d", request, value, index, rc));
        }
        return rc;
    }

    public int bulk_transfer(byte[] data, int len) {
        int rc = mUsbDeviceConnection.bulkTransfer(mUsbEndpointOut, data, len, FTDI.WRITE_TIMEOUT);
        if (rc < 0) {
            Log.e(TAG, String.format("data=%s, len=%d, rc=%d", data, len, rc));
        }
        return rc;
    }

    public void fast_init() {
        byte[] HI = new byte[]{(byte) 0x01};
        byte[] LO = new byte[]{(byte) 0x00};

        if (mUsbDeviceConnection != null) {
            try {
                log_msg("FAST_INIT");
                control_transfer(FTDI.SIO_SET_BITMODE, FTDI.BITBANG_ON);
                mUsbDeviceConnection.bulkTransfer(mUsbEndpointOut, HI, 1, 500); Thread.sleep(500);
                mUsbDeviceConnection.bulkTransfer(mUsbEndpointOut, LO, 1, 25); Thread.sleep(25);
                mUsbDeviceConnection.bulkTransfer(mUsbEndpointOut, HI, 1, 25); Thread.sleep(25);
                control_transfer(FTDI.SIO_SET_BITMODE, FTDI.BITBANG_OFF);

                control_transfer(FTDI.SIO_RESET, FTDI.SIO_RESET_PURGE_RX, FTDI.CH_A);
                control_transfer(FTDI.SIO_RESET, FTDI.SIO_RESET_PURGE_TX, FTDI.CH_A);

                if (get_pid(TD5_Pids.Pid.INIT_FRAME) && get_pid(TD5_Pids.Pid.START_DIAGNOSTICS) && get_pid(TD5_Pids.Pid.REQUEST_SEED)) {
                    int seed = (short) (response[3] << 8 | response[4]);
                    int key = generate_key(seed);
                    td5_requests.request.get(TD5_Pids.Pid.KEY_RETURN).request[3] = (byte) (key >> 8);
                    td5_requests.request.get(TD5_Pids.Pid.KEY_RETURN).request[4] = (byte) (key & 0xFF);
                    connected = get_pid(TD5_Pids.Pid.KEY_RETURN);
                }
            } catch (Exception ex) {
                log_msg(ex.toString());
            }
        } else {
            log_msg("not connected");
        }

    }

    int generate_key(int seedin) {
        // we have to use an int because java doesn't do unsigned values so we use the lower 16 bits of an int
        int seed = seedin;
        int count = ((seed >> 0xC & 0x8) + (seed >> 0x5 & 0x4) + (seed >> 0x3 & 0x2) + (seed & 0x1)) + 1; // count == byte (0 .. 255)
        for (int idx = 0; idx < count; idx++) {
            int tap = ((seed >> 1) + (seed >> 2) + (seed >> 8) + (seed >> 9)) & 1; // tap byte (0 .. 1)
            int tmp = (seed >> 1 & 0xFFFF) | (tap << 0xF); // short (0 .. 65535)
            if ((seed >> 0x3 & 1) == 1 && (seed >> 0xD & 1) == 1) {
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
        int len = td5_requests.request.get(pid).request.length;
        byte[] data = td5_requests.request.get(pid).request;
        String name = td5_requests.request.get(pid).name;

        data[len - 1] = checksum(data, len - 1);
        log_msg(name);
        log_data(data, len, true);
        int rc = mUsbDeviceConnection.bulkTransfer(mUsbEndpointOut, data, len, TD5_Constants.FTDI_WRITE_TIMEOUT);
    }

    byte checksum(byte[] data, int len) {
        byte crc = 0;
        for (int i = 0; i < len; i++) {
            crc = (byte) (crc + data[i]);
        }
        return crc;
    }

    static String byte_array_to_hex(byte[] data, int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(String.format("%02X ", data[i] & 0xFF));
        }
        return sb.toString();
    }

    private String integer_to_binary(int value) {
        return String.format("%8s", Integer.toBinaryString(value & 0xFF)).replace(' ', '0');
    }

    int readResponse(TD5_Pids.Pid pid) {
        byte[] buf = new byte[TD5_Constants.BUFFER_SIZE];
        // We are lucky that all the request and response messages are less than the 64 byte
        // max packet size of the FT232R. So we don't have to chunk the data for sending and
        // reading.

        // NTOTE: http://www.ftdichip.com/Support/Knowledgebase/index.html?an232b_04smalldataend.htm
        // When transferring data from an FTDI USB-Serial or USB-FIFO IC device to the PC, the device
        // will send the data given one of the following conditions:
        //
        // 1.	The buffer is full (64 bytes made up of 2 status bytes and 62 user bytes).
        //
        // 2.	One of the RS232 status lines has changed (USB-Serial chips only). A change of level
        // (high or low) on CTS# / DSR# / DCD# or RI# will cause it to pass back the current buffer
        // even though it may be empty or have less than 64 bytes in it.
        //
        // 3.	An event character had been enabled and was detected in the incoming data stream.
        //
        // 4.	A timer integral to the chip has timed out. There is a timer (latency timer) in the
        // FT232R, FT245R, FT2232C, FT232BM and FT245BM chips that measures the time since data was
        // last sent to the PC. The default value of the timer is set to 16 milliseconds. Every time
        // data is sent back to the PC the timer is reset. If it times-out then the chip will send
        // back the 2 status bytes and any data that is held in the buffer.

        int bytes_read = mUsbDeviceConnection.bulkTransfer(mUsbEndpointIn, buf, TD5_Constants.BUFFER_SIZE, TD5_Constants.FTDI_READ_TIMEOUT);
        // log_msg(String.format("bytes_read=%d", bytes_read));
        log_data(buf, bytes_read, false);

        // first two bytes are the modem status
        //if ((buf[1] & FTDI.ERROR_BIT_1) > 0) {
            // String msg = String.format("FTDI error: %02X:%02X", buf[0], buf[1]);
            String msg = String.format("modem_status=%s:%s", integer_to_binary(buf[0]), integer_to_binary(buf[1]));
        log_msg(msg);
        //}

        if (buf.length > 2) {
            response = Arrays.copyOfRange(buf, 2, bytes_read);
        }

        return bytes_read - 2;
    }

    void log_data(byte[] data, int len, boolean is_tx) {
        log_msg(String.format("%s %s", is_tx ? ">>" : "<<", byte_array_to_hex(data, len)));
    }

}
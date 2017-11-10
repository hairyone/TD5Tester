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
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    TextView tvInfo;
    Button btConnect;
    Button btDisconnect;
    Button btFastInit;
    Button btClear;

    byte[] mReadBuffer = new byte[Consts.RESPONSE_BUFFER_SIZE];
    boolean mHaveUsbPermission = false;
    boolean mFastInitCompleted = false;
    Requests requests = null;

    PendingIntent mPermissionIntent;
    UsbDevice mUsbDevice = null;
    UsbInterface mUsbInterface = null;
    UsbEndpoint mUsbEndpointIn = null;
    UsbEndpoint mUsbEndpointOut = null;
    UsbDeviceConnection mUsbDeviceConnection = null;

    // Allow other threads to update the UI
    private final Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case (Consts.UI_HANDLER_LOG_MSG):
                    log_append((String) msg.obj);
                    break;
                case (Consts.UI_HANDLER_SET_CONNECT_BUTTON_STATE):
                    btConnect.setEnabled((boolean) msg.obj);
                    break;
                case (Consts.UI_HANDLER_SET_DISCONNECT_BUTTON_STATE):
                    btDisconnect.setEnabled((boolean) msg.obj);
                    break;
                case (Consts.UI_HANDLER_SET_FASTINIT_BUTTON_STATE):
                    btFastInit.setEnabled((boolean) msg.obj);
                    break;
            }
        }
    };

    private final BroadcastReceiver mUsbDeviceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Consts.ACTION_USB_PERMISSION.equals(action)) {
                if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                    UsbDevice usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    log_msg(String.format("permission granted for %s", usbDevice.getProductName()));
                    mHaveUsbPermission = true;
                }
            }
            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                mUsbDevice = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                log_msg(String.format("usb_attached=%s", mUsbDevice.getProductName()));
                // usb_open();
            }
            if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                UsbDevice usbDevice = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                log_msg(String.format("usb_detached=%s", usbDevice.getProductName()));
                if (usbDevice != null && usbDevice.getDeviceName().equals(mUsbDevice.getDeviceName())) {
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

        btDisconnect.setEnabled(false);
        btFastInit.setEnabled(false);

        requests = new Requests();

        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(Consts.ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(Consts.ACTION_USB_PERMISSION);
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
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        usb_close();
                    }
                });
                thread.start();
            }
        });

    }

    private boolean usb_open(){
        myHandler.sendMessage(Message.obtain(myHandler, Consts.UI_HANDLER_SET_CONNECT_BUTTON_STATE, false));

        boolean result = false;

        mFastInitCompleted = false;
        mUsbDevice = null;
        mUsbInterface = null;
        mUsbEndpointIn = null;
        mUsbEndpointOut = null;
        mUsbDeviceConnection = null;

        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        while (deviceIterator.hasNext()) {
            UsbDevice usbDevice = deviceIterator.next();
            if (usbDevice.getVendorId() == FTDI.VENDOR_ID && usbDevice.getProductId() == FTDI.PRODUCT_ID) {
                mUsbDevice = usbDevice;
            }
        }

        if (mUsbDevice == null) {
            log_msg("FTDI device not found");
            myHandler.sendMessage(Message.obtain(myHandler, Consts.UI_HANDLER_SET_CONNECT_BUTTON_STATE, true));
            return result;
        }

        log_msg(String.format("connected to %s", mUsbDevice.getProductName()));

        log_msg(String.format("requesting permission for %s", mUsbDevice.getProductName()));
        manager.requestPermission(mUsbDevice, mPermissionIntent);

        // FIXME: The rest of this code should be fired in another thread when permission is granted
        int cnt = 0;
        while (!mHaveUsbPermission && cnt <= 5) {
            log_msg(String.format("waiting for permission for %s", mUsbDevice.getProductName()));
            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
                log_msg(ex.toString());
                break;
            }
            cnt++;
        }
        if (!mHaveUsbPermission) {
            myHandler.sendMessage(Message.obtain(myHandler, Consts.UI_HANDLER_SET_CONNECT_BUTTON_STATE, true));
            return result;
        }

        for (int i=0; i < mUsbDevice.getInterfaceCount(); i++) {
            UsbInterface usbInterface = mUsbDevice.getInterface(i);
            UsbEndpoint usbEndpointOut = null;
            UsbEndpoint usbEndpointIn = null;

            int endpointCount = usbInterface.getEndpointCount();
            if (endpointCount >= 2) {
                for (int j = 0; j < endpointCount; j++) {
                    if (usbInterface.getEndpoint(j).getType() == UsbConstants.USB_ENDPOINT_XFER_BULK && usbInterface.getEndpoint(j).getDirection() == UsbConstants.USB_DIR_OUT) {
                        usbEndpointOut = usbInterface.getEndpoint(j);
                    }
                    if (usbInterface.getEndpoint(j).getType() == UsbConstants.USB_ENDPOINT_XFER_BULK && usbInterface.getEndpoint(j).getDirection() == UsbConstants.USB_DIR_IN) {
                        usbEndpointIn = usbInterface.getEndpoint(j);
                    }
                }
                if (usbEndpointOut != null && usbEndpointIn != null) {
                    mUsbInterface = usbInterface;
                    mUsbEndpointOut = usbEndpointOut;
                    mUsbEndpointIn = usbEndpointIn;
                }
            }
        }

        if( mUsbInterface == null) {
            log_msg("no suitable interface found");
            return result;
        }

        log_msg(String.format("mUsbInterface=%s", mUsbInterface.toString()));

        mUsbDeviceConnection = manager.openDevice(mUsbDevice);
        if (mUsbDeviceConnection != null) {
            mUsbDeviceConnection.claimInterface(mUsbInterface, true);
            control_transfer(FTDI.SIO_RESET,         FTDI.SIO_RESET_SIO,         FTDI.CH_A);
            control_transfer(FTDI.SIO_RESET,         FTDI.SIO_RESET_PURGE_RX,    FTDI.CH_A);
            control_transfer(FTDI.SIO_RESET,         FTDI.SIO_RESET_PURGE_TX,    FTDI.CH_A);
            control_transfer(FTDI.SIO_SET_FLOW_CTRL, FTDI.SIO_DISABLE_FLOW_CTRL, FTDI.CH_A);
            control_transfer(FTDI.SIO_SET_DATA,      FTDI.LINE_8N1,              FTDI.CH_A);
            control_transfer(FTDI.SIO_SET_BAUDRATE,  FTDI.BAUDRATE_10400);
            result = true;
        }
        myHandler.sendMessage(Message.obtain(myHandler, Consts.UI_HANDLER_SET_CONNECT_BUTTON_STATE, false));
        myHandler.sendMessage(Message.obtain(myHandler, Consts.UI_HANDLER_SET_DISCONNECT_BUTTON_STATE, true));
        myHandler.sendMessage(Message.obtain(myHandler, Consts.UI_HANDLER_SET_FASTINIT_BUTTON_STATE, true));

        return result;

    }

    public void log_msg(String msg) {
        myHandler.sendMessage(Message.obtain(myHandler, Consts.UI_HANDLER_LOG_MSG, msg));
    }

    public void usb_close() {
        myHandler.sendMessage(Message.obtain(myHandler, Consts.UI_HANDLER_SET_CONNECT_BUTTON_STATE, false));
        myHandler.sendMessage(Message.obtain(myHandler, Consts.UI_HANDLER_SET_DISCONNECT_BUTTON_STATE, false));
        myHandler.sendMessage(Message.obtain(myHandler, Consts.UI_HANDLER_SET_FASTINIT_BUTTON_STATE, false));
        if (connected()) {
            if (mUsbInterface != null) {
                mUsbDeviceConnection.releaseInterface(mUsbInterface);
                mUsbInterface = null;
            }
            mUsbDeviceConnection.close();
            mUsbDeviceConnection = null;
        }
        mFastInitCompleted = false;
        mHaveUsbPermission = false;
        mUsbDevice = null;
        mUsbEndpointIn = null;
        mUsbEndpointOut = null;
        myHandler.sendMessage(Message.obtain(myHandler, Consts.UI_HANDLER_SET_CONNECT_BUTTON_STATE, true));
        myHandler.sendMessage(Message.obtain(myHandler, Consts.UI_HANDLER_SET_DISCONNECT_BUTTON_STATE, false));
        myHandler.sendMessage(Message.obtain(myHandler, Consts.UI_HANDLER_SET_FASTINIT_BUTTON_STATE, false));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // TODO: Can a thread be used here ?
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

    public int bulk_transfer_read(byte[] data, int len) {
        int rc = mUsbDeviceConnection.bulkTransfer(mUsbEndpointIn, data, len, FTDI.READ_TIMEOUT);
        if (rc < 0) {
            Log.e(TAG, String.format("data=%s, len=%d, rc=%d", data, len, rc));
        }
        return rc;
    }

    public int bulk_transfer_write(byte[] data, int len) {
        int rc = mUsbDeviceConnection.bulkTransfer(mUsbEndpointOut, data, len, FTDI.WRITE_TIMEOUT);
        if (rc < 0) {
            Log.e(TAG, String.format("data=%s, len=%d, rc=%d", data, len, rc));
        }
        return rc;
    }

    public boolean connected() {
        boolean result = true;
        if (mUsbDeviceConnection == null) {
            log_msg("not connected to device");
            result = false;
        }
        return result;
    }

    public void fast_init() {
        myHandler.sendMessage(Message.obtain(myHandler, Consts.UI_HANDLER_SET_FASTINIT_BUTTON_STATE, false));
        byte[] HI = new byte[]{(byte) 0x01};
        byte[] LO = new byte[]{(byte) 0x00};

        try {
            log_msg("FAST_INIT");
            control_transfer(FTDI.SIO_SET_BITMODE, FTDI.BITBANG_ON);
            bulk_transfer_write(HI, 1); Thread.sleep(500);
            bulk_transfer_write(LO, 1); Thread.sleep(25);
            bulk_transfer_write(HI, 1); Thread.sleep(25);
            control_transfer(FTDI.SIO_SET_BITMODE, FTDI.BITBANG_OFF);

            control_transfer(FTDI.SIO_RESET, FTDI.SIO_RESET_PURGE_RX, FTDI.CH_A);
            control_transfer(FTDI.SIO_RESET, FTDI.SIO_RESET_PURGE_TX, FTDI.CH_A);
            control_transfer(FTDI.SIO_SET_LATENCY_TIMER, FTDI.LATENCY_MAX);

            if (get_pid(Requests.RequestPidEnum.INIT_FRAME) && get_pid(Requests.RequestPidEnum.START_DIAGNOSTICS) && get_pid(Requests.RequestPidEnum.REQUEST_SEED)) {
                int seed = (short) (mReadBuffer[3] << 8 | mReadBuffer[4]);
                int key = generate_key(seed);
                requests.request.get(Requests.RequestPidEnum.KEY_RETURN).request[3] = (byte) (key >> 8);
                requests.request.get(Requests.RequestPidEnum.KEY_RETURN).request[4] = (byte) (key & 0xFF);
                mFastInitCompleted = get_pid(Requests.RequestPidEnum.KEY_RETURN);
            }
        } catch (Exception ex) {
            log_msg(ex.toString());
        }
        myHandler.sendMessage(Message.obtain(myHandler, Consts.UI_HANDLER_SET_FASTINIT_BUTTON_STATE, true));
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

    boolean get_pid(Requests.RequestPidEnum pid) {
        boolean result = false;
        send(pid);
        int len = readResponse(pid);
        if (len > 1) {
            byte cs1 = mReadBuffer[len - 1];
            byte cs2 = checksum(mReadBuffer, len - 1);
            if (cs1 == cs2) {
                if (mReadBuffer[1] != 0x7F) {
                    result = true;
                }
            }
        }
        return result;
    }

    void send(Requests.RequestPidEnum pid) {
        int len = requests.request.get(pid).request.length;
        byte[] data = requests.request.get(pid).request;
        String name = requests.request.get(pid).name;

        data[len - 1] = checksum(data, len - 1);
        log_msg(name);
        log_data(data, len, true);
        bulk_transfer_write(data, len);
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

    int readResponse(Requests.RequestPidEnum pid) {
        // Create a temporary buffer to hold the mReadBuffer, note that:
        //  1.  The original request is echoed in the mReadBuffer.
        //  2.  In every packet of 64 bytes returned by the FTDI chip there are 2 modem status bytes
        //      at the start of each packet.

        int request_len = requests.request.get(pid).request.length;
        int response_len = requests.request.get(pid).response_len;
        int expected_response_len = request_len + response_len;
        int max_packet_size = mUsbEndpointIn.getMaxPacketSize();
        int modem_status_len = 2;
        final int modem_status_bytes_len = (
                (int) Math.ceil(
                        ((double) expected_response_len) / ((double) (max_packet_size - modem_status_len)))
        ) * modem_status_len;

        byte[] buf = new byte[expected_response_len + modem_status_bytes_len];

        int bytes_read = bulk_transfer_read(buf, expected_response_len + modem_status_bytes_len);
        log_data(buf, bytes_read, false);

        if (Consts.LOG_EVERY_MODEM_STATUS || (buf[1] & FTDI.ERROR_BIT_1) > 0) {
            String msg = String.format("modem_status=%s:%s", integer_to_binary(buf[0]), integer_to_binary(buf[1]));
            log_msg(msg);
        }

        if (buf.length < 2) {
            log_msg("malformed mReadBuffer");
            return 0;
        }

        // Remove the modem status bytes from each packet in the buf
        int data_len = filter_modem_status_bytes(buf, buf, Math.min(buf.length, bytes_read), max_packet_size, modem_status_len);
        if (data_len > 0) {
            // Remove the request bytes from the start
            System.arraycopy(buf, request_len, mReadBuffer, 0, data_len - request_len);
            data_len -= response_len;
        }

        // The cleaned up mReadBuffer
        log_data(mReadBuffer, data_len, false);
        return data_len;
    }

    void log_data(byte[] data, int len, boolean is_tx) {
        log_msg(String.format("%s %s", is_tx ? ">>" : "<<", byte_array_to_hex(data, len)));
    }

    private int filter_modem_status_bytes(byte[] src, byte[] dest, int src_len, int max_packet_len, int modem_status_len) {
        int src_offset = 0;
        int dst_offset = 0;
        src_offset = src_offset + modem_status_len;
        while (src_offset < src_len) {
            int len = ((src_len - src_offset) >= (max_packet_len - modem_status_len)) ?
                    (max_packet_len - modem_status_len) :
                    (src_len - src_offset);
            System.arraycopy(src, src_offset, dest, dst_offset, len);
            src_offset = src_offset + len + modem_status_len;
            dst_offset = dst_offset + len;
        }
        return dst_offset;
    }



}
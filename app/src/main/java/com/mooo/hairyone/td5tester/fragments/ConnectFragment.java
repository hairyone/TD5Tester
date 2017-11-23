package com.mooo.hairyone.td5tester.fragments;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mooo.hairyone.td5tester.Consts;
import com.mooo.hairyone.td5tester.FTDI;
import com.mooo.hairyone.td5tester.R;
import com.mooo.hairyone.td5tester.Requests;
import com.mooo.hairyone.td5tester.Util;
import com.mooo.hairyone.td5tester.events.AuthorisedEvent;
import com.mooo.hairyone.td5tester.events.ConnectedEvent;
import com.mooo.hairyone.td5tester.events.DashboardEvent;
import com.mooo.hairyone.td5tester.events.LogClearEvent;
import com.mooo.hairyone.td5tester.events.MessageEvent;
import com.mooo.hairyone.td5tester.events.NotConnectedEvent;
import com.mooo.hairyone.td5tester.events.BusyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class ConnectFragment extends Fragment {

    private static final String TAG = ConnectFragment.class.getSimpleName();
    private static final String STATE_TVINFO = "TVINFO";
    private static final String STATE_DASHBOARD_RUNNING = "DASHBOARD_RUNNING";

    TextView tvInfo;
    ImageButton btConnect;
    ImageButton btDisconnect;
    ImageButton btFastInit;
    ImageButton btDashboard;
    ImageButton btClear;

    byte[] mReadBuffer = new byte[Consts.RESPONSE_BUFFER_SIZE];
    boolean mHaveUsbPermission = false;
    boolean mFastInitCompleted = false;
    private volatile boolean mDashboardRunning = false;
    Requests mRequests = new Requests();
    UsbDevice mUsbDevice = null;
    UsbInterface mUsbInterface = null;
    UsbEndpoint mUsbEndpointIn = null;
    UsbEndpoint mUsbEndpointOut = null;
    UsbDeviceConnection mUsbDeviceConnection = null;

    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
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

    public ConnectFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        Log.i(TAG, "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
        getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter(Consts.ACTION_USB_PERMISSION));
        getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter(UsbManager.ACTION_USB_DEVICE_ATTACHED));
        getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter(UsbManager.ACTION_USB_DEVICE_DETACHED));
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause()");
        getActivity().unregisterReceiver(mBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop()");
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        tvInfo.append(event.message + "\n");
        Log.w(TAG, event.message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogClearEvent(LogClearEvent event) {
        tvInfo.setText("");
        //byte seed_hi = (byte) 0xcb;
        //byte seed_lo = (byte) 0xb6;
        //short seed = Util.bytes2short(seed_hi, seed_lo);
        //log_msg(String.format("seed=%X, seed_hi=%X, seed_lo=%X", seed, seed_hi, seed_lo));
        //log_msg(String.format("seed=%04X, key=%04X", seed, generate_key(seed)));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotConnectedEvent(NotConnectedEvent event) {
        Util.setImageButtonState(btConnect, true);
        Util.setImageButtonState(btDisconnect, false);
        Util.setImageButtonState(btFastInit, false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBusyEvent(BusyEvent event) {
        Util.setImageButtonState(btConnect, false);
        Util.setImageButtonState(btDisconnect, false);
        Util.setImageButtonState(btFastInit, false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectedEvent(ConnectedEvent event) {
        Util.setImageButtonState(btConnect, false);
        Util.setImageButtonState(btDisconnect, true);
        Util.setImageButtonState(btFastInit, true);
        // Util.setImageButtonState(btClear, true);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState()");
        savedInstanceState.putCharSequence(STATE_TVINFO, tvInfo.getText());
        savedInstanceState.putBoolean(STATE_DASHBOARD_RUNNING, mDashboardRunning);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");
        View view =  inflater.inflate(R.layout.connect_fragment, container, false);

        btConnect = (ImageButton) view.findViewById(R.id.btConnect);
        btDisconnect= (ImageButton) view.findViewById(R.id.btDisconnect);
        btFastInit = (ImageButton) view.findViewById(R.id.btFastInit);
        btDashboard = (ImageButton) view.findViewById(R.id.btDashboard);
        btClear = (ImageButton) view.findViewById(R.id.btClear);
        tvInfo = (TextView) view.findViewById(R.id.tvInfo);
        tvInfo.setMovementMethod(new ScrollingMovementMethod());

        if (savedInstanceState != null) {
            tvInfo.setText(savedInstanceState.getCharSequence(STATE_TVINFO));
            mDashboardRunning = savedInstanceState.getBoolean(STATE_DASHBOARD_RUNNING);
        }

        // set the initial button state before the EventBus is registered
        Util.setImageButtonState(btConnect, true);
        Util.setImageButtonState(btDisconnect, false);
        Util.setImageButtonState(btFastInit, false);
        Util.setImageButtonState(btClear, true);

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

        btDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (mDashboardRunning) {
                            mDashboardRunning = false;
                        } else {
                            mDashboardRunning = true;
                            dashboard();
                        }
                    }
                });
                thread.start();
            }
        });


        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new LogClearEvent());
            }
        });

        return view;
    }

    private boolean usb_open(){
        EventBus.getDefault().post(new BusyEvent());
        log_msg("connecting");

        boolean result = false;
        mFastInitCompleted = false;
        mUsbDevice = null;
        mUsbInterface = null;
        mUsbEndpointIn = null;
        mUsbEndpointOut = null;
        mUsbDeviceConnection = null;

        UsbManager manager = (UsbManager) getActivity().getSystemService(Context.USB_SERVICE);
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
            EventBus.getDefault().post(new NotConnectedEvent());
            return result;
        }

        log_msg(String.format("connected to %s", mUsbDevice.getProductName()));

        log_msg(String.format("requesting permission for %s", mUsbDevice.getProductName()));
        manager.requestPermission(mUsbDevice, PendingIntent.getBroadcast(getContext(), 0, new Intent(Consts.ACTION_USB_PERMISSION), 0));

        // FIXME: The rest of this code should be fired in another thread when permission is granted
        int cnt = 0;
        while (!mHaveUsbPermission && cnt <= Consts.PERMISSION_WAIT_SECS) {
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
            EventBus.getDefault().post(new NotConnectedEvent());
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
            EventBus.getDefault().post(new NotConnectedEvent());
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

        EventBus.getDefault().post(new ConnectedEvent());
        return result;
    }

    private void usb_close() {
        EventBus.getDefault().post(new BusyEvent());
        log_msg("disconnecting");

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

        EventBus.getDefault().post(new NotConnectedEvent());
    }

    private int control_transfer(int request, int value) {
        return control_transfer(request, value, 0);
    }

    private int control_transfer(int request, int value, int index) {
        int rc = mUsbDeviceConnection.controlTransfer(FTDI.REQ_OUT, request, value, index, null, 0x00, FTDI.WRITE_TIMEOUT);
        if (rc < 0) {
            Log.e(TAG, String.format("control_transfer failed: request=%d, value=%d, index=%d, rc=%d", request, value, index, rc));
        }
        return rc;
    }

    private int bulk_transfer_read(byte[] data, int len) {
        int rc = mUsbDeviceConnection.bulkTransfer(mUsbEndpointIn, data, len, FTDI.READ_TIMEOUT);
        if (rc < 0) {
            Log.e(TAG, String.format("bulk_transfer_read failed: data=%s, len=%d, rc=%d", data, len, rc));
        }
        return rc;
    }

    private int bulk_transfer_write(byte[] data, int len) {
        int rc = mUsbDeviceConnection.bulkTransfer(mUsbEndpointOut, data, len, FTDI.WRITE_TIMEOUT);
        if (rc < 0) {
            Log.e(TAG, String.format("bulk_transfer_write failed: data=%s, len=%d, rc=%d", data, len, rc));
        }
        return rc;
    }

    private boolean connected() {
        boolean result = true;
        if (mUsbDeviceConnection == null) {
            log_msg("not connected to device");
            result = false;
        }
        return result;
    }

    private void fast_init() {
        EventBus.getDefault().post(new BusyEvent());

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

            // So the FTDI chip will return the current contents of the RX buffer
            // if the latency timer has expired.
            // control_transfer(FTDI.SIO_SET_LATENCY_TIMER, FTDI.LATENCY_MAX);

            if (get_pid(Requests.RequestPidEnum.INIT_FRAME) && get_pid(Requests.RequestPidEnum.START_DIAGNOSTICS) && get_pid(Requests.RequestPidEnum.REQUEST_SEED)) {
                // https://stackoverflow.com/questions/736815/2-bytes-to-short-java
                short seed = Util.bytes2short(mReadBuffer[3], mReadBuffer[4]);
                log_msg(String.format("seed=%X, seed_hi=%X, seed_lo=%X", seed, mReadBuffer[3], mReadBuffer[4]));
                int key = generate_key(seed);
                mRequests.request.get(Requests.RequestPidEnum.KEY_RETURN).request[3] = (byte) ((key & 0xFFFF) >>> 8);
                mRequests.request.get(Requests.RequestPidEnum.KEY_RETURN).request[4] = (byte) (key & 0xFF);
                mFastInitCompleted = get_pid(Requests.RequestPidEnum.KEY_RETURN);
            }
            log_msg(String.format("mFastinitCompleted=%b", mFastInitCompleted));
        } catch (Exception ex) {
            log_msg(ex.toString());
        }
        EventBus.getDefault().post(new ConnectedEvent());
    }

    /*
    uint16_t generateKey(uint16_t seed) {
        uint16_t tmp = 0;
        byte tap = 0;
        byte count = ((seed >> 0xC & 0x8) | (seed >> 0x5 & 0x4) | (seed >> 0x3 & 0x2) | (seed & 0x1)) + 1;
        for (byte idx = 0; idx < count; idx++) {
            tap = ((seed >> 1 ) ^ (seed >> 2 ) ^ (seed >> 8 ) ^ (seed >> 9 )) & 1;
            tmp = ((seed >> 1) | (tap << 0xF));
            if ((seed >> 0x3 & 1) && (seed >> 0xD & 1)) {
                seed = tmp & ~1;
            } else {
                seed = tmp | 1;
            }
        }
        return seed;
    }
    */

    private int generate_key(int seedin) {
        int seed = seedin & 0xFFFF;
        log_msg(String.format("seed=%04X, seed_hi=%02X, seed_lo=%02X", seed & 0xFFFF, (seed & 0xFFFF) >>> 8, seed & 0xFF));
        int count = (( (seed >>> 0xC & 0x8) | (seed >>> 0x5 & 0x4) | (seed >>> 0x3 & 0x2) | (seed & 0x1) ) + 1) & 0xFF;
        log_msg(String.format("count=%d", count));
        for (int idx = 0; idx < count; idx++) {
            int tap = (( (seed >>> 1) ^ (seed >>> 2) ^ (seed >>> 8) ^ (seed >>> 9) ) & 1) & 0xFF;
            int tmp = (seed >>> 1) | (tap << 0xF);
            if ( (seed >>> 0x3 & 1) == 1 && (seed >>> 0xD & 1) == 1 ) {
                seed = tmp & ~1;
            } else {
                seed = tmp | 1;
            }
            log_msg(String.format("tap=%d, tmp=%05d, a=%d, b=%d, seed=%05d", tap, tmp, seed >>> 0x03 & 1, seed >>> 0x0d & 1, seed));
        }
        log_msg(String.format("key_hi=%02X, key_lo=%02X", (seed & 0xFFFF) >>> 8, seed & 0xFF));
        return seed;
    }

    private boolean get_pid(Requests.RequestPidEnum pid) {
        boolean result = false;
        if (pid != Requests.RequestPidEnum.INIT_FRAME) {
            try {
                Thread.sleep(Consts.SEND_REQUEST_DELAY);
            } catch (Exception ex) {
                log_msg(String.format("get_pid() error=%s", ex.toString()));
            }
        }
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

    private void send(Requests.RequestPidEnum pid) {
        int len = mRequests.request.get(pid).request.length;
        byte[] data = mRequests.request.get(pid).request;
        String name = mRequests.request.get(pid).name;

        data[len - 1] = checksum(data, len - 1);
        log_msg(name);
        log_data(data, len, true);
        bulk_transfer_write(data, len);
    }

    private byte checksum(byte[] data, int len) {
        byte crc = 0;
        for (int i = 0; i < len; i++) {
            crc = (byte) (crc + data[i]);
        }
        return crc;
    }

    private int readResponse(Requests.RequestPidEnum pid) {

        int request_len = mRequests.request.get(pid).request.length;
        int response_len = mRequests.request.get(pid).response_len;
        int max_packet_size = mUsbEndpointIn.getMaxPacketSize();
        int modem_status_len = 2;

        final int modem_status_bytes_len = (
            (int) Math.ceil(
                ((double) request_len + response_len) / ((double) (max_packet_size - modem_status_len)))
        ) * modem_status_len;

        int expected_len = request_len + response_len + modem_status_bytes_len;
        byte[] buf = new byte[expected_len];

        long start_time = System.currentTimeMillis();
        int offset = 0;
        while (true) {
            // Keep reading until we get the expected number of bytes or we timeout
            int bytes_read = bulk_transfer_read(buf, expected_len);
            if (Consts.LOG_EVERY_READ) {
                log_data(buf, bytes_read, false);
            }

            if (Consts.LOG_EVERY_MODEM_STATUS || (buf[1] & FTDI.ERROR_MASK) > 0) {
                String msg = String.format("modem_status=%s, line_status=%s", Util.integer_to_binary(buf[0]), Util.integer_to_binary(buf[1]));
                log_msg(msg);
            }

            // There should be at least the two status bytes from the FTDI chip
            if (buf.length < 2) {
                log_msg("malformed mReadBuffer");
                return 0;
            }

            // Remove the modem status bytes from the start of each packet in the buffer
            int data_len = filter_modem_status_bytes(buf, buf, Math.min(buf.length, bytes_read), max_packet_size, modem_status_len);

            if (data_len > 0) {
                System.arraycopy(buf, 0, mReadBuffer, offset, data_len);
                offset += data_len;
            }

            if (pid == Requests.RequestPidEnum.KEY_RETURN && offset == 10 && mReadBuffer[7] == 0x67) {
                // The only instance where a negative response is longer than a positive response
                // >> 04 27 02 F7 B9 DD | 02 67 02 6B       Positive response
                // >> 04 27 02 F7 B9 DD | 03 7F 27 35 DE    Negative response
                break;
            }

            if (offset >= request_len + response_len || System.currentTimeMillis() - start_time > Consts.READ_RESPONSE_TIMEOUT ) {
                if (System.currentTimeMillis() - start_time > FTDI.READ_TIMEOUT) {
                    log_msg("timeout in read_response()");
                }
                break;
            }
        }

        // Remove the echoed request
        if (offset >= request_len) {
            System.arraycopy(mReadBuffer, request_len, mReadBuffer, 0, offset - request_len);
            log_data(mReadBuffer, offset - request_len, false);
            return offset - request_len;
        } else {
            // not enough bytes for a plausible response
            return 0;
        }
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

    public void log_msg(String msg) {
        EventBus.getDefault().post(new MessageEvent(msg));
    }

    private void log_data(byte[] data, int len, boolean is_tx) {
        log_msg(String.format("%s %s", is_tx ? ">>" : "<<", Util.byte_array_to_hex(data, len)));
    }

    private void dashboard() {
        log_msg("dashboard starting");
        try {
            while (mDashboardRunning) {
                if (mFastInitCompleted) {
                    if (get_pid(Requests.RequestPidEnum.ENGINE_RPM)) {
                        double rpm = Util.bytes2short(mReadBuffer[3], mReadBuffer[4]);
                        log_msg(String.format("rpm=%f", rpm));
                        EventBus.getDefault().post(new DashboardEvent(DashboardEvent.DATA_TYPE.RPM, rpm));
                    }
                    if (get_pid(Requests.RequestPidEnum.BATTERY_VOLTAGE)) {
                        double voltage = Util.bytes2short(mReadBuffer[3], mReadBuffer[4]) / 1000.0;
                        log_msg(String.format("voltage=%f", voltage));
                        EventBus.getDefault().post(new DashboardEvent(DashboardEvent.DATA_TYPE.BATTERY_VOLTAGE, voltage));
                    }
                    if (get_pid(Requests.RequestPidEnum.VEHICLE_SPEED)) {
                        double speed = mReadBuffer[3];
                        log_msg(String.format("speed=%f", speed));
                        EventBus.getDefault().post(new DashboardEvent(DashboardEvent.DATA_TYPE.VEHICLE_SPEED, speed));
                    }
                } else {
                    // Not connected so just wiggle the gauges
                    EventBus.getDefault().post(new DashboardEvent(DashboardEvent.DATA_TYPE.RPM, ThreadLocalRandom.current().nextInt(500, 3500 + 1)));
                    EventBus.getDefault().post(new DashboardEvent(DashboardEvent.DATA_TYPE.BATTERY_VOLTAGE, ThreadLocalRandom.current().nextInt(8, 14 + 1)));
                    EventBus.getDefault().post(new DashboardEvent(DashboardEvent.DATA_TYPE.VEHICLE_SPEED, ThreadLocalRandom.current().nextInt(30, 55 + 1)));
                }
                Thread.sleep(500);
            }
        } catch (Exception ex) {
            log_msg(ex.toString());
        }
        mDashboardRunning = false;
        log_msg("dashboard stopped");
    }

}
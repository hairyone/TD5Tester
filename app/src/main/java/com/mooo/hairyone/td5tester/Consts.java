package com.mooo.hairyone.td5tester;

public class Consts {

    // Log all modem status bytes or just when an error is detected ?
    public static final boolean LOG_EVERY_MODEM_STATUS  = true;

    // Get permission to use the USB system
    public static final String ACTION_USB_PERMISSION = "com.mooo.hairyone.td5tester.USB_PERMISSION";
    public static final int PERMISSION_WAIT_SECS = 5;

    // Message types that can be sent to the UI thread from other threads
    public static final int UI_HANDLER_LOG_MSG                      = 1;
    public static final int UI_HANDLER_SET_CONNECT_BUTTON_STATE     = 2;
    public static final int UI_HANDLER_SET_DISCONNECT_BUTTON_STATE  = 3;
    public static final int UI_HANDLER_SET_FASTINIT_BUTTON_STATE    = 4;

    public static final int DASHBOARD_SET_RPM                       = 1;

    public static final int RESPONSE_BUFFER_SIZE    = 512;
    public static final int READ_RESPONSE_TIMEOUT   = 100;

}

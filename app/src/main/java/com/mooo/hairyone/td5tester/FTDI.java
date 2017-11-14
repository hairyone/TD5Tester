package com.mooo.hairyone.td5tester;

import android.hardware.usb.UsbConstants;

public class FTDI {

    // The only chip I am testing with is the FT232RL

    public static final int VENDOR_ID           = 0x0403;
    public static final int PRODUCT_ID          = 0x6001;

    public static final int WRITE_TIMEOUT       = 5000;
    public static final int READ_TIMEOUT        = 5000;

    public static final int BAUDRATE_10400      = 0x4120;

    public static final int USB_TYPE_STANDARD   = 0x00 << 5;
    public static final int USB_TYPE_CLASS      = 0x00 << 5;
    public static final int USB_TYPE_VENDOR     = 0x00 << 5;
    public static final int USB_TYPE_RESERVED   = 0x00 << 5;

    public static final int USB_RECIP_DEVICE    = 0x00;
    public static final int USB_RECIP_INTERFACE = 0x01;
    public static final int USB_RECIP_ENDPOINT  = 0x02;
    public static final int USB_RECIP_OTHER     = 0x03;

    public static final int USB_ENDPOINT_IN     = 0x80;
    public static final int USB_ENDPOINT_OUT    = 0x00;

    public static final int CH_A                = 1;        // FT232R just has a single interface
    public static final int REQ_OUT             = UsbConstants.USB_TYPE_VENDOR | USB_RECIP_DEVICE | USB_ENDPOINT_OUT;
    public static final int REQ_IN              = UsbConstants.USB_TYPE_VENDOR | USB_RECIP_DEVICE | USB_ENDPOINT_IN;

    // Requests
    public static final int SIO_RESET               = 0;  // Reset the port
    public static final int SIO_SET_MODEM_CTRL      = 1;  // Set the modem control register
    public static final int SIO_SET_FLOW_CTRL       = 2;  // Set flow control register
    public static final int SIO_SET_BAUDRATE        = 3;  // Set baud rate
    public static final int SIO_SET_DATA            = 4;  // Set the data characteristics of the port
    public static final int SIO_POLL_MODEM_STATUS   = 5;  // Get line status
    public static final int SIO_SET_EVENT_CHAR      = 6;  // Change event character
    public static final int SIO_SET_ERROR_CHAR      = 7;  // Change error character
    public static final int SIO_SET_LATENCY_TIMER   = 9;  // Change latency timer
    public static final int SIO_GET_LATENCY_TIMER   = 10; // Get latency timer
    public static final int SIO_SET_BITMODE         = 11; // Change bit mode
    public static final int SIO_READ_PINS           = 12; // Read GPIO pin value

    // Reset commands
    public static final int SIO_RESET_SIO           = 0; // Reset device
    public static final int SIO_RESET_PURGE_RX      = 1; // Drain RX buffer
    public static final int SIO_RESET_PURGE_TX      = 2; // Drain TX buffer

    // Flow control
    public static final int SIO_DISABLE_FLOW_CTRL   = 0x0;
    public static final int SIO_RTS_CTS_HS          = (0x1 << 8);
    public static final int SIO_DTR_DSR_HS          = (0x2 << 8);
    public static final int SIO_XON_XOFF_HS         = (0x4 << 8);
    public static final int SIO_SET_DTR_MASK        = 0x1;
    public static final int SIO_SET_DTR_HIGH        = (SIO_SET_DTR_MASK | (SIO_SET_DTR_MASK << 8));
    public static final int SIO_SET_DTR_LOW         = (0x0 | (SIO_SET_DTR_MASK << 8));
    public static final int SIO_SET_RTS_MASK        = 0x2;
    public static final int SIO_SET_RTS_HIGH        = (SIO_SET_RTS_MASK | (SIO_SET_RTS_MASK << 8));
    public static final int SIO_SET_RTS_LOW         = (0x0 | (SIO_SET_RTS_MASK << 8));

    // Parity bits
    public static final int PARITY_NONE     = 0x0000;
    public static final int PARITY_ODD      = 0x0001;
    public static final int PARITY_EVEN     = 0x0002;
    public static final int PARITY_MARK     = 0x0003;
    public static final int PARITY_SPACE    = 0x0004;

    // Number of stop bits
    public static final int STOP_BIT_1      = 0x0000;
    public static final int STOP_BIT_15     = 0x0001;
    public static final int STOP_BIT_2      = 0x0002;

    // Number of bits
    public static final int BITS_7          = 0x0007;
    public static final int BITS_8          = 0x0008;

    // Break type
    public static final int BREAK_OFF       = 0x0000;
    public static final int BREAK_ON        = 0x0001;

    public static final int LINE_8N1        = (((BITS_8 & 0x0F) | PARITY_NONE << 8) | STOP_BIT_1 << 11);

    // Bit bang
    public static final int BITMODE_RESET       = 0x00; // switch off bitbang mode
    public static final int BITMODE_BITBANG     = 0x01; // classical asynchronous bitbang mode
    public static final int BITMODE_MPSSE       = 0x02; // MPSSE mode, available on 2232x chips
    public static final int BITMODE_SYNCBB      = 0x04; // synchronous bitbang mode
    public static final int BITMODE_MCU         = 0x08; // MCU Host Bus Emulation mode,
    public static final int BITMODE_OPTO        = 0x10; // Fast Opto-Isolated Serial Interface Mode
    public static final int BITMODE_CBUS        = 0x20; // Bitbang on CBUS pins of R-type chips
    public static final int BITMODE_SYNCFF      = 0x40; // Single Channel Synchronous FIFO mode
    public static final int BITMODE_MASK        = 0x7F; // Mask for all bitmodes

    // Bit bang shortcuts
    public static final int BITBANG_ON  = (0x01 & 0xFF) | ((BITMODE_BITBANG & BITMODE_MASK) << 8);
    public static final int BITBANG_OFF = (0x00 & 0xFF) | ((BITMODE_RESET & BITMODE_MASK) << 8);

    // Every packet from the FTDI chip has two status bytes at the start
    // Modem status = buf[0] & 0b11110000
    // Line status  = buf[1] & 0b11111111

    // Modem status bits
    public static final int MODEM_CTS   = (1 << 4);  // Clear to send
    public static final int MODEM_DSR   = (1 << 5);  // Data set ready
    public static final int MODEM_RI    = (1 << 6);  // Ring indicator
    public static final int MODEM_RLSD  = (1 << 7);  // Carrier detect

    // Line status bits
    public static final int MODEM_DR    = (1 << 0); // Data ready
    public static final int MODEM_OE    = (1 << 1); // Overrun error
    public static final int MODEM_PE    = (1 << 2); // Parity error
    public static final int MODEM_FE    = (1 << 3); // Framing error
    public static final int MODEM_BI    = (1 << 4); // Break interrupt
    public static final int MODEM_THRE  = (1 << 5); // Transmitter holding register
    public static final int MODEM_TEMT  = (1 << 6); // Transmitter empty
    public static final int MODEM_RCVE  = (1 << 7); // Error in RCVR FIFO

    // The bits of the line status byte that would indicate an error
    public static final int ERROR_MASK  = 0b10001110;

    // Latency
    public static final int LATENCY_MIN = 12;
    public static final int LATENCY_MAX = 255;
}

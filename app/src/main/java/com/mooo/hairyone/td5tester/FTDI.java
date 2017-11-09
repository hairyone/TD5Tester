package com.mooo.hairyone.td5tester;

public class FTDI {
    public static final int VENDOR_ID   = 0x0403;
    public static final int PRODUCT_ID  = 0x6001;

    public static final int WRITE_TIMEOUT   = 5000;
    public static final int READ_TIMEOUT    = 5000;

    public static final int CH_A = 1;

    public static final int REQ_OUT             = 0x0040;   // Control Transfer Out


    // Requests
    public static final int SIO_RESET               = 0;    // Reset the port
    public static final int SIO_SET_MODEM_CTRL      = 1;    // Set the modem control register
    public static final int SIO_SET_FLOW_CTRL       = 2;    // Set flow control register
    public static final int SIO_SET_BAUDRATE        = 3;    // Set baud rate
    public static final int SIO_SET_DATA            = 4;    // Set the data characteristics of the port
    public static final int SIO_POLL_MODEM_STATUS   = 5;    // Get line status
    public static final int SIO_SET_EVENT_CHAR      = 6;    // Change event character
    public static final int SIO_SET_ERROR_CHAR      = 7;    // Change error character
    public static final int SIO_SET_LATENCY_TIMER   = 9;    // Change latency timer
    public static final int SIO_GET_LATENCY_TIMER   = 10;   // Get latency timer
    public static final int SIO_SET_BITMODE         = 11;   // Change bit mode
    public static final int SIO_READ_PINS           = 12;   // Read GPIO pin value

    // Reset commands
    public static final int SIO_RESET_SIO       = 0x0000;    // Reset device
    public static final int SIO_RESET_PURGE_RX  = 0x0001;    // Drain RX buffer
    public static final int SIO_RESET_PURGE_TX  = 0x0002;    // Drain TX buffer

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

    public static final int BAUDRATE_10400  = 0x4120;


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

    // Error bits
    public static final int ERROR_BIT_0 = 0x00;
    public static final int ERROR_BIT_1 = 0x8E;

    // Modem status
    public static final int MODEM_CTS   = (1 << 4);  // Clear to send
    public static final int MODEM_DSR   = (1 << 5);  // Data set ready
    public static final int MODEM_RI    = (1 << 6);  // Ring indicator
    public static final int MODEM_RLSD  = (1 << 7);  // Carrier detect
    public static final int MODEM_DR    = (1 << 8);  // Data ready
    public static final int MODEM_OE    = (1 << 9);  // Overrun error
    public static final int MODEM_PE    = (1 << 10); // Parity error
    public static final int MODEM_FE    = (1 << 11); // Framing error
    public static final int MODEM_BI    = (1 << 12); // Break interrupt
    public static final int MODEM_THRE  = (1 << 13); // Transmitter holding register
    public static final int MODEM_TEMT  = (1 << 14); // Transmitter empty
    public static final int MODEM_RCVE  = (1 << 15); // Error in RCVR FIFO
}

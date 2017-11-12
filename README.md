# TD5Tester

A simple Android app to communicate with the Land Rover TD5 engine ECU.

To use this you will need a VAG COM KKL usb -> OBD2 cable that uses an FTDI chip.

Developed with Android Studio.


## FTDI Notes

1. Why does a bulkTransfer sometimes return before all the requested bytes have been received and the timeout has not expired?

http://www.ftdichip.com/Support/Knowledgebase/index.html?an232b_04smalldataend.htm

> When transferring data from an FTDI USB-Serial or USB-FIFO IC device to the PC, the device will send the data given one of the following conditions:

> 1. The buffer is full (64 bytes made up of 2 status bytes and 62 user bytes).

> 2. One of the RS232 status lines has changed (USB-Serial chips only). A change of level(high or low) on CTS# / DSR# / DCD# or RI# will cause it to pass back the current buffer even though it may be empty or have less than 64 bytes in it.

> 3. An event character had been enabled and was detected in the incoming data stream.

> 4. A timer integral to the chip has timed out. There is a timer (latency timer) in theFT232R, FT245R, FT2232C, FT232BM and FT245BM chips that measures the time since data waslast sent to the PC. The default value of the timer is set to 16 milliseconds. Every time data is sent back to the PC the timer is reset. If it times-out then the chip will send
back the 2 status bytes and any data that is held in the buffer.

2. Why are there spurious bytes in the received data.

The FTDI chip returns data as packets and the first two bytes of each packet are the modem status bytes.  If you do a bulkTransfer larger than the maxPacketSize the modem status bytes are repeated at the start of every packet.

https://github.com/mik3y/usb-serial-for-android/issues/4
http://git.altlinux.org/people/manowar/public/usb-serial-for-android.git


## Example output

This is what the first sucessful fast_init looks like in the logcat

```
11-11 23:39:26.573 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: connected to FT232R USB UART
11-11 23:39:26.576 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: requesting permission for FT232R USB UART
11-11 23:39:26.598 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: waiting for permission for FT232R USB UART
11-11 23:39:27.572 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: waiting for permission for FT232R USB UART
11-11 23:39:28.573 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: waiting for permission for FT232R USB UART
11-11 23:39:29.552 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: permission granted for FT232R USB UART
11-11 23:39:29.582 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: mUsbInterface=UsbInterface[mId=0,mAlternateSetting=0,mName=FT232R USB UART,mClass=255,mSubclass=255,mProtocol=255,mEndpoints=[
                                                                        UsbEndpoint[mAddress=129,mAttributes=2,mMaxPacketSize=64,mInterval=0]
                                                                        UsbEndpoint[mAddress=2,mAttributes=2,mMaxPacketSize=64,mInterval=0]]
11-11 23:39:41.318 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: FAST_INIT
11-11 23:39:41.859 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: INIT_FRAME
11-11 23:39:41.861 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: >> 81 13 F7 81 0C 
11-11 23:39:42.117 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: << 01 60 81 13 F7 81 0C 03 C1 57 8F AA 
11-11 23:39:42.120 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: modem_status=00000001:01100000
11-11 23:39:42.121 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: << 03 C1 57 8F AA 
11-11 23:39:42.124 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: START_DIAGNOSTICS
11-11 23:39:42.125 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: >> 02 10 A0 B2 
11-11 23:39:42.370 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: << 01 60 02 10 A0 B2 01 50 51 
11-11 23:39:42.371 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: modem_status=00000001:01100000
11-11 23:39:42.372 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: << 01 50 51 
11-11 23:39:42.374 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: REQUEST_SEED
11-11 23:39:42.376 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: >> 02 27 01 2A 
11-11 23:39:42.627 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: << 01 60 02 27 01 2A 04 67 01 60 43 0F 
11-11 23:39:42.628 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: modem_status=00000001:01100000
11-11 23:39:42.629 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: << 04 67 01 60 43 0F 
11-11 23:39:42.630 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: tap=1, tmp=45089, a=0, b=1, seed=45089
11-11 23:39:42.631 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: tap=0, tmp=22544, a=0, b=0, seed=22545
11-11 23:39:42.634 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: seed_hi=60, seed_lo=43, key_hi=58, key_lo=11
11-11 23:39:42.638 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: KEY_RETURN
11-11 23:39:42.639 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: >> 04 27 02 58 11 96 
11-11 23:39:42.878 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: << 01 60 04 27 02 58 11 96 02 67 02 6B 
11-11 23:39:42.879 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: modem_status=00000001:01100000
11-11 23:39:42.880 24500-24500/com.mooo.hairyone.td5tester W/TD5Tester: << 02 67 02 6B 
```


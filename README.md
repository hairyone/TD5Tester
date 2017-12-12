# TD5Tester

A simple Android app to communicate with the Land Rover TD5 engine ECU.

To use this you will need a VAG COM KKL usb -> OBD2 cable that uses an FTDI chip.

Developed with Android Studio.

## Screenshots

![CONNECT](/docs/ConnectTab.png)
![DASHBOARD](/docs/DashboardTab.png)
![TEMPERATURE](/docs/TemperatureTab.png)


## Graphs 
These graphs were created using the CSV file that is saved whilst the dashboard feature is running. The graphs were created using Google Sheets.

![RPM Vs SPEED](/docs/RPMvsSPEED.png)
![RPM Vs MAF](/docs/RPMvsMAF.png)
![RPM Vs IQ](/docs/RPMvsIQ.png)
![DEMAND Vs IQ](/docs/DEMANDvsIQ.png)

## Credits

Much of the above would not have been possible without the research done by the people below:

* Java library that deals with a function to strip the modem status bytes: https://github.com/mik3y/usb-serial-for-android/issues/4

* Using a VAG COM KKL cable with Python: http://www.discotd5.com/data-logging/desktop-diagnostics

* How to communicate with the FTDI chip using controlTransfer(), it also contains the only notes I have found showing how the modem status bytes should be interpreted: https://github.com/eblot/pyftdi

* Much information refarding the data and TD5 tuning http://www.discotd5.com

* A Python basec project with information on many of the TD5 PID requests https://github.com/EA2EGA/Ekaitza_Itzali



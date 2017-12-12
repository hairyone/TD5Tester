# TD5Tester

A simple Android app to communicate with the Land Rover TD5 engine ECU.

To use this you will need a VAG COM KKL usb -> OBD2 cable that uses an FTDI chip.

Developed with Android Studio.

## Screenshots

![CONNECT](/docs/ConnectTab.png)
![DASHBOARD](/docs/DashboardTab.png)
![TEMPERATURE](/docs/TemperatureTab.png)

## Viewing the log and CSV files
I use the Log Viewer app from here https://play.google.com/store/apps/details?id=com.apptiva.logviewer&hl=en_GB to view the log files.
The app will write up to 30 log files up to 256kB in size (any larger and the log viewer struggles to load them).

A CSV file is started each time the Dashboard feature is started.

![LOGVIEWER](/docs/LogViewer.png)

## Graphs 
These graphs were created using the CSV file that is saved whilst the dashboard feature is running. The graphs were created using Google Sheets.

![RPM Vs SPEED](/docs/RPMvsSPEED.png)
![RPM Vs MAF](/docs/RPMvsMAF.png)
![RPM Vs IQ](/docs/RPMvsIQ.png)
![DEMAND Vs IQ](/docs/DEMANDvsIQ.png)

## Purchases

* This is the super cheap MAF I purchased to replace my defective one all I can say is it works, the car drives a little better but cheap MAFs generally get bad reviews. https://www.ebay.co.uk/itm/AIR-FLOW-METER-MASS-FOR-LAND-ROVER-DEFENDER-DISCOVERY-II-FREELANDER-5WK9607-NEW/132165517764

* This is the USB VAG COM cable I am using: http://www.ebay.co.uk/itm/VAG-KKL-USB-COM-OBD-2-Diagnostic-Cable-for-AUDI-VW-Skoda-Seat-OBDII-EOBD-FT232RL/322814060072

* My USB OTG cable looks like this: http://www.ebay.co.uk/itm/90-Degree-Right-Angled-USB-C-USB-3-1-Type-C-Male-to-A-Female-OTG-Data-Cable/231822737522. I ought to replace it with one tht allows charging at the same time.

## Credits

Much of the above would not have been possible without the research done by the people below:

* Java library that deals with a function to strip the modem status bytes: https://github.com/mik3y/usb-serial-for-android/issues/4

* Using a VAG COM KKL cable with Python: http://www.discotd5.com/data-logging/desktop-diagnostics

* How to communicate with the FTDI chip using controlTransfer(), it also contains the only notes I have found showing how the modem status bytes should be interpreted: https://github.com/eblot/pyftdi

* Much information refarding the data and TD5 tuning http://www.discotd5.com

* A Python based project with information on many of the TD5 PID requests https://github.com/EA2EGA/Ekaitza_Itzali





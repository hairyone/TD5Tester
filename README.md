# TD5Tester

![THEKIT](/docs/TheKit.jpg)

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

The CSV file contains the raw data values in the units reported by the ECU, the field order is:

|Column              |Units      |Conversion                 |
|--------------------|-----------|---------------------------|
|EngineRpm           |RPM        |raw_value                  |
|BatteryVoltage      |Volts      |raw_value / 1000           |
|VehicleSpeed        |MPH        |raw_value * 0.621371)      |
|CoolantTemperature  |deg C      |(raw_value - 2732) / 10)   |
|ExternalTemperature |deg C      |(raw_value - 2732) / 10)   |
|InletTemperature    |deg C      |(raw_value - 2732) / 10)   |
|FuelTemperature     |deg C      |(raw_value - 2732) / 10)   |
|AcceleratorTrack1   |Volts      |raw_value / 1000           |
|AcceleratorTrack2   |Volts      |raw_value / 1000           |
|AcceleratorTrack3   |%          |raw_value / 100            |
|AcceleratorSupply   |Volts      |raw_value / 1000           |
|AmbientPressure     |kPa        |raw_value / 100            |
|ManifoldAirPressure |kPa        |raw_value / 100            |
|ManifoldAirFlow     |kg/h       |raw_value / 10             |
|Driver Demand       |mg/stroke  |raw_value / 100            |
|MAF Air mass?       |mg/stroke  |raw_value / 10             |
|MAP Air mass?       |mg/stroke  |raw_value / 10             |
|Injection quantity  |mg/stroke  |raw_value / 100            |
|FuelDemand5         |?          |raw_value / 100            |
|Torque limit?       |mg/stroke  |raw_value / 100            |
|Smoke  limit?       |mg/stroke  |raw_value / 100            |
|Idle demand?        |mg/stroke  |raw_value / 100            |

![LOGVIEWER](/docs/LogViewer.png)

## Graphs 
These graphs were created using the CSV file that is saved whilst the dashboard feature is running. The graphs were created using Google Sheets.

![MAPMAF Vs RPM](/docs/MAPMAFvsRPM.png)
![RPM Vs SPEED](/docs/RPMvsSPEED.png)
![RPM Vs MAF](/docs/RPMvsMAF.png)
![RPM Vs IQ](/docs/RPMvsIQ.png)
![DEMAND Vs IQ](/docs/DEMANDvsIQ.png)

## TODO

* Add display of injector config after logged faults
* Add display of MAP_VARIANT info
* Add WGM_DUTY_RATIO and EGR_MODULE to CSV file
* Add another screen to loop and show GET_INPUTS PID data, e.g. array of indicators that illuminate when input active (brake pedal pressed, cruise control, etc.)
* Add a Menu for:
  * Preferences: logging level, csv file on|off, GPS on|off
  * Download engine MAP
* Add a screen of buttons for the various test functions ( test A/C clutch, test MIL lamp, etc.)
* Implement the KEEP_ALIVE PID so connection is kept active whilst no other PIDS are being sent
* Add GPS coords to CSV logging
* Write some instructions and explain the buttons / screens

## Purchases

* This is the super cheap MAF (£20) I purchased to replace my defective one. All I will say is it works, the car drives a little better. Most people would recommend you buy genuine and cheap MAFs generally get bad reviews. If you examine the MAPMAF vs RPM chart above you will see that the plot for the MAF has more scattering of the points. I think I have noticed that the initial idle readings when the engine is cold are higher than when engine is warmed up, this shows up as the two horizontal lines of red dots at low RPM.
https://www.ebay.co.uk/itm/AIR-FLOW-METER-MASS-FOR-LAND-ROVER-DEFENDER-DISCOVERY-II-FREELANDER-5WK9607-NEW/132165517764

* This is the USB VAG COM cable I am using, the listing says is has a genuine FTDI chip (there are many counterfeits), but it works so it is good enough for me.
http://www.ebay.co.uk/itm/VAG-KKL-USB-COM-OBD-2-Diagnostic-Cable-for-AUDI-VW-Skoda-Seat-OBDII-EOBD-FT232RL/322814060072

* My USB OTG cable looks like the one below, I ought to replace it with one that allows charging at the same time.
http://www.ebay.co.uk/itm/90-Degree-Right-Angled-USB-C-USB-3-1-Type-C-Male-to-A-Female-OTG-Data-Cable/231822737522. 

## Credits

Much of the above would not have been possible without the research done by the people below:

* Java library that showed me an easy way to strip the modem status bytes from the FTDI receive buffer response: https://github.com/mik3y/usb-serial-for-android/issues/4

* Using a VAG COM KKL cable with Python: http://www.discotd5.com/data-logging/desktop-diagnostics

* How to communicate with the FTDI chip using controlTransfer(), it also contains the only notes I have found showing how the modem status bytes should be interpreted: https://github.com/eblot/pyftdi

* Much information regarding the data and TD5 tuning http://www.discotd5.com

* A Python based project with information on many of the TD5 PID requests https://github.com/EA2EGA/Ekaitza_Itzali





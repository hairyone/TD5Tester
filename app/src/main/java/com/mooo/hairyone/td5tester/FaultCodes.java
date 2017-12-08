package com.mooo.hairyone.td5tester;

import java.util.EnumMap;

public class FaultCodes {

    static final String fault_code_00_00 = "Unknown";

    static final String fault_code_01_01 = "01-1 egr inlet throttle diagnostics (L)";
    static final String fault_code_01_02 = "01-2 turbocharger wastegate diagnostics (L)";
    static final String fault_code_01_03 = "01-3 egr vacuum diagnostics (L)";
    static final String fault_code_01_04 = "01-4 temperature gauge diagnostics (L)";
    static final String fault_code_01_05 = "01-5 driver demand problem 1 (L)";
    static final String fault_code_01_06 = "01-6 driver demand problem 2 (L)";
    static final String fault_code_01_07 = "01-7 air flow circuit (L)";
    static final String fault_code_01_08 = "01-8 manifold pressure circuit (L)";
    static final String fault_code_02_01 = "02-1 inlet air temp. circuit (L)";
    static final String fault_code_02_02 = "02-2 fuel temp. circuit (L)";
    static final String fault_code_02_03 = "02-3 coolant temp. circuit (L)";
    static final String fault_code_02_04 = "02-4 battery volts (L)";
    static final String fault_code_02_05 = "02-5 reference voltage (L)";
    static final String fault_code_02_06 = "02-6 ambient air temp. circuit (L)";
    static final String fault_code_02_07 = "02-7 driver demand supply problem (L)";
    static final String fault_code_02_08 = "02-8 ambient pressure circuit (L)";
    static final String fault_code_03_01 = "03-1 egr inlet throttle diagnostics (L)";
    static final String fault_code_03_02 = "03-2 turbocharger wastegate diagnostics (L)";
    static final String fault_code_03_03 = "03-3 egr vacuum diagnostics (L)";
    static final String fault_code_03_04 = "03-4 temperature gauge diagnostics (L)";
    static final String fault_code_03_05 = "03-5 driver demand problem 1 (L)";
    static final String fault_code_03_06 = "03-6 driver demand problem 2 (L)";
    static final String fault_code_03_07 = "03-7 air flow circuit (L)";
    static final String fault_code_03_08 = "03-8 manifold pressure circuit (L)";
    static final String fault_code_04_01 = "04-1 inlet air temp. circuit (L)";
    static final String fault_code_04_02 = "04-2 fuel temperature circuit (L)";
    static final String fault_code_04_03 = "04-3 coolant temp. circuit (L)";
    static final String fault_code_04_04 = "04-4 battery volts (L)";
    static final String fault_code_04_05 = "04-5 reference voltage (L)";
    static final String fault_code_04_06 = "04-6 ambient air temperature circuit (L)";
    static final String fault_code_04_07 = "04-7 driver demand supply problem (L)";
    static final String fault_code_04_08 = "04-8 ambient pressure circuit (L)";
    static final String fault_code_05_01 = "05-1 egr inlet throttle diagnostics (C)";
    static final String fault_code_05_02 = "05-2 turbocharger wastegate diagnostics (C)";
    static final String fault_code_05_03 = "05-3 egr vacuum diagnostics (C)";
    static final String fault_code_05_04 = "05-4 temperature gauge diagnostics (C)";
    static final String fault_code_05_05 = "05-5 driver demand problem 1 (C)";
    static final String fault_code_05_06 = "05-6 driver demand problem 2 (C)";
    static final String fault_code_05_07 = "05-7 air flow circuit (C)";
    static final String fault_code_05_08 = "05-8 manifold pressure circuit (C)";
    static final String fault_code_06_01 = "06-1 inlet air temp. circuit (C)";
    static final String fault_code_06_02 = "06-2 fuel temperature circuit (C)";
    static final String fault_code_06_03 = "06-3 coolant temp. circuit (C)";
    static final String fault_code_06_04 = "06-4 battery voltage problem (C)";
    static final String fault_code_06_05 = "06-5 reference voltage (C)";
    static final String fault_code_06_07 = "06-7 driver demand supply problem (C)";
    static final String fault_code_06_08 = "06-8 ambient pressure circuit (C)";
    static final String fault_code_07_01 = "07-1 cruise lamp drive over temp. (L)";
    static final String fault_code_07_02 = "07-2 fuel used output drive over temp. (L)";
    static final String fault_code_07_03 = "07-3 radiator fan drive over temp. (L)";
    static final String fault_code_07_04 = "07-4 active engine mounting over temp. (L)";
    static final String fault_code_07_05 = "07-5 turbocharger wastegate short circuit (L)";
    static final String fault_code_07_06 = "07-6 egr inlet throttle short circuit (L)";
    static final String fault_code_07_07 = "07-7 egr vacuum modulator short circuit (L)";
    static final String fault_code_07_08 = "07-8 temperature gauge short circuit (L)";
    static final String fault_code_08_01 = "08-1 air conditioning fan drive over temp. (L)";
    static final String fault_code_08_02 = "08-2 fuel pump drive over temp. (L)";
    static final String fault_code_08_03 = "08-3 tacho drive over temp. (L)";
    static final String fault_code_08_04 = "08-4 gearbox/abs drive over temp. (L)";
    static final String fault_code_08_05 = "08-5 air conditioning clutch over temp. (L)";
    static final String fault_code_08_06 = "08-6 mil lamp drive over temp. (L)";
    static final String fault_code_08_07 = "08-7 glow plug relay drive over temp. (L)";
    static final String fault_code_08_08 = "08-8 glowplug lamp drive over temperature (L)";
    static final String fault_code_09_01 = "09-1 fuel used output drive open load (L)";
    static final String fault_code_09_02 = "09-2 cruise lamp drive open load (L)";
    static final String fault_code_09_03 = "09-3 radiator fan drive open load (L)";
    static final String fault_code_09_04 = "09-4 active engine mounting open load (L)";
    static final String fault_code_09_05 = "09-5 turbocharger wastegate open load (L)";
    static final String fault_code_09_06 = "09-6 egr inlett throttle open load (L)";
    static final String fault_code_09_07 = "09-7 egr vacuum modulator open load (L)";
    static final String fault_code_09_08 = "09-8 temperature gauge open load (L)";
    static final String fault_code_10_01 = "10-1 air conditioning fan drive open load (L)";
    static final String fault_code_10_02 = "10-2 fuel pump drive open load (L)";
    static final String fault_code_10_03 = "10-3 tachometer open load (L)";
    static final String fault_code_10_04 = "10-4 gearbox/abs drive open load (L)";
    static final String fault_code_10_05 = "10-5 air conditioning clutch open load (L)";
    static final String fault_code_10_06 = "10-6 mil lamp drive open load (L)";
    static final String fault_code_10_07 = "10-7 glow plug lamp drive open load (L)";
    static final String fault_code_10_08 = "10-8 glow plug relay drive open load (L)";
    static final String fault_code_11_01 = "11-1 cruise control lamp drive over temperature (C)";
    static final String fault_code_11_02 = "11-2 fuel used output drive over temperature (C)";
    static final String fault_code_11_03 = "11-3 radiator fan drive over temperature (C)";
    static final String fault_code_11_04 = "11-4 active engine mounting over temperature (C)";
    static final String fault_code_11_05 = "11-5 turbocharger wastegate short circuit (C)";
    static final String fault_code_11_06 = "11-6 egr inlet throttle short circuit (C)";
    static final String fault_code_11_07 = "11-7 egr vacuum modulator short circuit (C)";
    static final String fault_code_11_08 = "11-8 temperature gauge short circuit (C)";
    static final String fault_code_12_01 = "12-1 air conditioning fan drive open load (C)";
    static final String fault_code_12_02 = "12-2 fuel pump drive open load (C)";
    static final String fault_code_12_03 = "12-3 tachometer open load (C)";
    static final String fault_code_12_04 = "12-4 gearbox/abs drive open load (C)";
    static final String fault_code_12_05 = "12-5 air conditioning clutch open load (C)";
    static final String fault_code_12_06 = "12-6 mil lamp drive open load (C)";
    static final String fault_code_12_07 = "12-7 glow plug relay drive open load (C)";
    static final String fault_code_12_08 = "12-8 glowplug relay drive open load (C)";
    static final String fault_code_13_01 = "13-1 cruise control lamp drive over temp. (C)";
    static final String fault_code_13_02 = "13-2 fuel used output drive over temp. (C)";
    static final String fault_code_13_03 = "13-3 radiator fan drive over temp. (C)";
    static final String fault_code_13_04 = "13-4 active engine mounting over temp. (C)";
    static final String fault_code_13_05 = "13-5 turbocharger wastegate short circuit (C)";
    static final String fault_code_13_06 = "13-6 egr inlet throttle short circuit (C)";
    static final String fault_code_13_07 = "13-7 egr vacuum modulator short circuit (C)";
    static final String fault_code_13_08 = "13-8 temperature gauge short circuit (C)";
    static final String fault_code_14_01 = "14-1 air conditioning fan drive open load (C)";
    static final String fault_code_14_02 = "14-2 fuel pump drive open load (C)";
    static final String fault_code_14_03 = "14-3 tachometer open load (C)";
    static final String fault_code_14_04 = "14-4 gearbox/abs drive open load (C)";
    static final String fault_code_14_05 = "14-5 air conditioning clutch open load (C)";
    static final String fault_code_14_06 = "14-6 mil lamp drive open load (C)";
    static final String fault_code_14_07 = "14-7 glow plug relay drive open load (C)";
    static final String fault_code_14_08 = "14-8 glowplug relay drive open load (C)";
    static final String fault_code_15_02 = "15-2 high speed crank (L)";
    static final String fault_code_16_01 = "16-1 pump relay open circuit";
    static final String fault_code_16_02 = "16-2 high speed crank (L)";
    static final String fault_code_17_02 = "17-2 high speed crank (C)";
    static final String fault_code_19_02 = "19-2 can rx/tx error (L)";
    static final String fault_code_19_03 = "19-3 can tx/rx error (L)";
    static final String fault_code_19_06 = "19-6 noisy crank signal has been detected (L)";
    static final String fault_code_19_08 = "19-8 can has had reset failure (L)";
    static final String fault_code_20_01 = "20-1 turbocharger under boosting (L)";
    static final String fault_code_20_02 = "20-2 turbocharger over boosting (L)";
    static final String fault_code_20_04 = "20-4 egr valve stuck open (L)";
    static final String fault_code_20_05 = "20-5 egr valve stuck closed (L)";
    static final String fault_code_21_04 = "21-4 driver demand 1 out of range (L)";
    static final String fault_code_21_05 = "21-5 driver demand 2 out of range (L)";
    static final String fault_code_21_06 = "21-6 problem detected with driver demand (L)";
    static final String fault_code_21_07 = "21-7 inconsistencies found with driver demand (L)";
    static final String fault_code_21_08 = "21-8 injector trim data corrupted (L)";
    static final String fault_code_22_01 = "22-1 road speed missing (L)";
    static final String fault_code_22_03 = "22-3 vehicle accel. outside bounds of cruise control (L)";
    static final String fault_code_22_07 = "22-7 cruise control resume stuck closed (L)";
    static final String fault_code_22_08 = "22-8 cruise control set stuck closed (L)";
    static final String fault_code_23_01 = "23-1 excessive can bus off (C)";
    static final String fault_code_23_02 = "23-2 can rx/tx error (C)";
    static final String fault_code_23_03 = "23-3 can tx/rx error (C)";
    static final String fault_code_23_04 = "23-4 unable to detect remote can mode (C)";
    static final String fault_code_23_05 = "23-5 under boost has occurred on this trip (C)";
    static final String fault_code_23_06 = "23-6 noisy crack signal has been detected (C)";
    static final String fault_code_24_01 = "24-1 turbocharger under boosting (C)";
    static final String fault_code_24_02 = "24-2 turbocharger over boosting (C)";
    static final String fault_code_24_03 = "24-3 over boost has occurred this trip (C)";
    static final String fault_code_24_04 = "24-4 egr valve stuck open (C)";
    static final String fault_code_24_05 = "24-5 egr valve stuck closed (C)";
    static final String fault_code_24_07 = "24-7 problem detected with auto gear box (C)";
    static final String fault_code_25_04 = "25-4 driver demand 1 out of range (L)";
    static final String fault_code_25_05 = "25-5 driver demand 2 out of range (L)";
    static final String fault_code_25_06 = "25-6 problem detected with drive demand (C)";
    static final String fault_code_25_07 = "25-7 inconsistencies found with driver demand (C)";
    static final String fault_code_25_08 = "25-8 injector trim data corrupted (C)";
    static final String fault_code_26_01 = "26-1 road speed missing (C)";
    static final String fault_code_26_02 = "26-2 cruise control system problem (C)";
    static final String fault_code_26_03 = "26-3 vehicle accel. outside bounds for cruise control (C)";
    static final String fault_code_26_07 = "26-7 cruise control resume stuck closed (C)";
    static final String fault_code_26_08 = "26-8 cruise control set stuck closed (C)";
    static final String fault_code_27_01 = "27-1 inj. 1 peak charge long (L)";
    static final String fault_code_27_02 = "27-2 inj. 2 peak charge long (L)";
    static final String fault_code_27_03 = "27-3 inj. 3 peak charge long (L)";
    static final String fault_code_27_04 = "27-4 inj. 4 peak charge long (L)";
    static final String fault_code_27_05 = "27-5 inj. 5 peak charge long (L)";
    static final String fault_code_27_06 = "27-6 inj. 6 peak charge long (L)";
    static final String fault_code_27_07 = "27-7 topside switch failed post injection (L)";
    static final String fault_code_28_01 = "28-1 inj. 1 peak charge short (L)";
    static final String fault_code_28_02 = "28-2 inj. 2 peak charge short (L)";
    static final String fault_code_28_03 = "28-3 inj. 3 peak charge short (L)";
    static final String fault_code_28_04 = "28-4 inj. 4 peak charge short (L)";
    static final String fault_code_28_05 = "28-5 inj. 5 peak charge short (L)";
    static final String fault_code_28_06 = "28-6 inj. 6 peak charge short (L)";
    static final String fault_code_28_07 = "28-7 topside switch failed pre injection (L)";
    static final String fault_code_29_01 = "29-1 inj. 1 peak charge long (C)";
    static final String fault_code_29_02 = "29-2 inj. 2 peak charge long (C)";
    static final String fault_code_29_03 = "29-3 inj. 3 peak charge long (C)";
    static final String fault_code_29_04 = "29-4 inj. 4 peak charge long (C)";
    static final String fault_code_29_05 = "29-5 inj. 5 peak charge long (C)";
    static final String fault_code_29_06 = "29-6 inj. 6 peak charge long (C)";
    static final String fault_code_29_07 = "29-7 topside switch failed post injection (C)";
    static final String fault_code_30_01 = "30-1 inj. 1 peak charge short (C)";
    static final String fault_code_30_02 = "30-2 inj. 2 peak charge short (C)";
    static final String fault_code_30_03 = "30-3 inj. 3 peak charge short (C)";
    static final String fault_code_30_04 = "30-4 inj. 4 peak charge short (C)";
    static final String fault_code_30_05 = "30-5 inj. 5 peak charge short (C)";
    static final String fault_code_30_06 = "30-6 inj. 6 peak charge short (C)";
    static final String fault_code_30_07 = "30-7 topside switch failed pre injection (C)";
    static final String fault_code_31_01 = "31-1 inj. 1 open circuit (L)";
    static final String fault_code_31_02 = "31-2 inj. 2 open circuit (L)";
    static final String fault_code_31_03 = "31-3 inj. 3 open circuit (L)";
    static final String fault_code_31_04 = "31-4 inj. 4 open circuit (L)";
    static final String fault_code_31_05 = "31-5 inj. 5 open circuit (L)";
    static final String fault_code_31_06 = "31-6 inj. 6 open circuit (L)";
    static final String fault_code_32_01 = "32-1 inj. 1 short circuit (L)";
    static final String fault_code_32_02 = "32-2 inj. 2 short circuit (L)";
    static final String fault_code_32_03 = "32-3 inj. 3 short circuit (L)";
    static final String fault_code_32_04 = "32-4 inj. 4 short circuit (L)";
    static final String fault_code_32_05 = "32-5 inj. 5 short circuit (L)";
    static final String fault_code_32_06 = "32-6 inj. 6 short circuit (L)";
    static final String fault_code_33_01 = "33-1 inj. 1 open circuit (C)";
    static final String fault_code_33_02 = "33-2 inj. 2 open circuit (C)";
    static final String fault_code_33_03 = "33-3 inj. 3 open circuit (C)";
    static final String fault_code_33_04 = "33-4 inj. 4 open circuit (C)";
    static final String fault_code_33_05 = "33-5 inj. 5 open circuit (C)";
    static final String fault_code_33_06 = "33-6 inj. 6 open circuit (C)";
    static final String fault_code_34_01 = "34-1 inj. 1 short circuit (C)";
    static final String fault_code_34_02 = "34-2 inj. 2 short circuit (C)";
    static final String fault_code_34_03 = "34-3 inj. 3 short circuit (C)";
    static final String fault_code_34_04 = "34-4 inj. 4 short circuit (C)";
    static final String fault_code_34_05 = "34-5 inj. 5 short circuit (C)";
    static final String fault_code_34_06 = "34-6 inj. 6 short circuit (C)";
    static final String fault_code_35_01 = "35-1 inj. 1 partial short circuit (L)";
    static final String fault_code_35_02 = "35-2 inj. 2 partial short circuit (L)";
    static final String fault_code_35_03 = "35-3 inj. 3 partial short circuit (L)";
    static final String fault_code_35_04 = "35-4 inj. 4 partial short circuit (L)";
    static final String fault_code_35_05 = "35-5 inj. 5 partial short circuit (L)";
    static final String fault_code_35_06 = "35-6 inj. 6 partial short circuit (L)";

    public static final String[] faultCodeList = {
        fault_code_01_01, fault_code_01_02, fault_code_01_03, fault_code_01_04, fault_code_01_05, fault_code_01_06, fault_code_01_07, fault_code_01_08,
        fault_code_02_01, fault_code_02_02, fault_code_02_03, fault_code_02_04, fault_code_02_05, fault_code_02_06, fault_code_02_07, fault_code_02_08,
        fault_code_03_01, fault_code_03_02, fault_code_03_03, fault_code_03_04, fault_code_03_05, fault_code_03_06, fault_code_03_07, fault_code_03_08,
        fault_code_04_01, fault_code_04_02, fault_code_04_03, fault_code_04_04, fault_code_04_05, fault_code_04_06, fault_code_04_07, fault_code_04_08,
        fault_code_05_01, fault_code_05_02, fault_code_05_03, fault_code_05_04, fault_code_05_05, fault_code_05_06, fault_code_05_07, fault_code_05_08,
        fault_code_06_01, fault_code_06_02, fault_code_06_03, fault_code_06_04, fault_code_06_05, fault_code_00_00, fault_code_06_07, fault_code_06_08,
        fault_code_07_01, fault_code_07_02, fault_code_07_03, fault_code_07_04, fault_code_07_05, fault_code_07_06, fault_code_07_07, fault_code_07_08,
        fault_code_08_01, fault_code_08_02, fault_code_08_03, fault_code_08_04, fault_code_08_05, fault_code_08_06, fault_code_08_07, fault_code_08_08,
        fault_code_09_01, fault_code_09_02, fault_code_09_03, fault_code_09_04, fault_code_09_05, fault_code_09_06, fault_code_09_07, fault_code_09_08,
        fault_code_10_01, fault_code_10_02, fault_code_10_03, fault_code_10_04, fault_code_10_05, fault_code_10_06, fault_code_10_07, fault_code_10_08,
        fault_code_11_01, fault_code_11_02, fault_code_11_03, fault_code_11_04, fault_code_11_05, fault_code_11_06, fault_code_11_07, fault_code_11_08,
        fault_code_12_01, fault_code_12_02, fault_code_12_03, fault_code_12_04, fault_code_12_05, fault_code_12_06, fault_code_12_07, fault_code_12_08,
        fault_code_13_01, fault_code_13_02, fault_code_13_03, fault_code_13_04, fault_code_13_05, fault_code_13_06, fault_code_13_07, fault_code_13_08,
        fault_code_14_01, fault_code_14_02, fault_code_14_03, fault_code_14_04, fault_code_14_05, fault_code_14_06, fault_code_14_07, fault_code_14_08,
        fault_code_00_00, fault_code_15_02, fault_code_00_00, fault_code_00_00, fault_code_00_00, fault_code_00_00, fault_code_00_00, fault_code_00_00,
        fault_code_16_01, fault_code_16_02, fault_code_00_00, fault_code_00_00, fault_code_00_00, fault_code_00_00, fault_code_00_00, fault_code_00_00,
        fault_code_00_00, fault_code_17_02, fault_code_00_00, fault_code_00_00, fault_code_00_00, fault_code_00_00, fault_code_00_00, fault_code_00_00,
        fault_code_00_00, fault_code_00_00, fault_code_00_00, fault_code_00_00, fault_code_00_00, fault_code_00_00, fault_code_00_00, fault_code_00_00,
        fault_code_00_00, fault_code_19_02, fault_code_19_03, fault_code_00_00, fault_code_00_00, fault_code_19_06, fault_code_00_00, fault_code_19_08,
        fault_code_20_01, fault_code_20_02, fault_code_00_00, fault_code_20_04, fault_code_20_05, fault_code_00_00, fault_code_00_00, fault_code_00_00,
        fault_code_00_00, fault_code_00_00, fault_code_00_00, fault_code_21_04, fault_code_21_05, fault_code_21_06, fault_code_21_07, fault_code_21_08,
        fault_code_22_01, fault_code_00_00, fault_code_22_03, fault_code_00_00, fault_code_00_00, fault_code_00_00, fault_code_22_07, fault_code_22_08,
        fault_code_23_01, fault_code_23_02, fault_code_23_03, fault_code_23_04, fault_code_23_05, fault_code_23_06, fault_code_00_00, fault_code_00_00,
        fault_code_24_01, fault_code_24_02, fault_code_24_03, fault_code_24_04, fault_code_24_05, fault_code_00_00, fault_code_24_07, fault_code_00_00,
        fault_code_00_00, fault_code_00_00, fault_code_00_00, fault_code_25_04, fault_code_25_05, fault_code_25_06, fault_code_25_07, fault_code_25_08,
        fault_code_26_01, fault_code_26_02, fault_code_26_03, fault_code_00_00, fault_code_00_00, fault_code_00_00, fault_code_26_07, fault_code_26_08,
        fault_code_27_01, fault_code_27_02, fault_code_27_03, fault_code_27_04, fault_code_27_05, fault_code_27_06, fault_code_27_07, fault_code_00_00,
        fault_code_28_01, fault_code_28_02, fault_code_28_03, fault_code_28_04, fault_code_28_05, fault_code_28_06, fault_code_28_07, fault_code_00_00,
        fault_code_29_01, fault_code_29_02, fault_code_29_03, fault_code_29_04, fault_code_29_05, fault_code_29_06, fault_code_29_07, fault_code_00_00,
        fault_code_30_01, fault_code_30_02, fault_code_30_03, fault_code_30_04, fault_code_30_05, fault_code_30_06, fault_code_30_07, fault_code_00_00,
        fault_code_31_01, fault_code_31_02, fault_code_31_03, fault_code_31_04, fault_code_31_05, fault_code_31_06, fault_code_00_00, fault_code_00_00,
        fault_code_32_01, fault_code_32_02, fault_code_32_03, fault_code_32_04, fault_code_32_05, fault_code_32_06, fault_code_00_00, fault_code_00_00,
        fault_code_33_01, fault_code_33_02, fault_code_33_03, fault_code_33_04, fault_code_33_05, fault_code_33_06, fault_code_00_00, fault_code_00_00,
        fault_code_34_01, fault_code_34_02, fault_code_34_03, fault_code_34_04, fault_code_34_05, fault_code_34_06, fault_code_00_00, fault_code_00_00,
        fault_code_35_01, fault_code_35_02, fault_code_35_03, fault_code_35_04, fault_code_35_05, fault_code_35_06, fault_code_00_00, fault_code_00_00
    };
}

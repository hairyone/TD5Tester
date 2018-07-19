package com.mooo.hairyone.td5tester;

import java.util.EnumMap;

public class Requests {

    public enum RequestPidEnum {
        INIT_FRAME, START_DIAGNOSTICS, REQUEST_SEED, KEY_RETURN,
        ENGINE_RPM, BATTERY_VOLTAGE, VEHICLE_SPEED, START_FUELLING,
        TEMPERATURES, MAP_MAF, AMBIENT_PRESSURE, THROTTLE_POSITION,
        POWER_BALANCE, RPM_ERROR, EGR_MODULE, INLET_MODULE,
        WASTEGATE_MODULE, KEEP_ALIVE, FAULT_CODES, CLEAR_FAULTS,
        GET_INPUTS, GET_FUEL_DEMAND, ABS_INIT_FRAME, GET_VIN, GET_ECUTYPE, GET_MAPVARIANT,
        TEST_ACCLUTCH, TEST_ACFAN, TEST_MILLAMP, TEST_FUELPUMP, TEST_GLOW,
        TEST_PULSEREV, TEST_TURBOMOD, TEST_TEMPGAUGE, TEST_EGRMOD,
        TEST_INJECT1, TEST_INJECT2, TEST_INJECT3, TEST_INJECT4, TEST_INJECT5
    };

    public class Request {
        public byte response_len;
        public String name;
        public byte[] request;

        public Request(byte response_len, String name, byte[] request) {
            this. response_len = response_len;
            this.name = name;
            this.request = request;
        }
    }

    public static EnumMap<RequestPidEnum, Request> request = new EnumMap<RequestPidEnum, Request>(RequestPidEnum.class);

    public Requests() {
        request.put(RequestPidEnum.INIT_FRAME,           new Request((byte)  5, "INIT_FRAME",        new byte[] { (byte) 0x81, (byte) 0x13, (byte) 0xF7, (byte) 0x81, (byte) 0x00 }));
        request.put(RequestPidEnum.ABS_INIT_FRAME,       new Request((byte)  5, "ABS_INIT_FRAME",    new byte[] { (byte) 0xC1, (byte) 0x34, (byte) 0xF1, (byte) 0x81, (byte) 0x00 }));
        request.put(RequestPidEnum.START_DIAGNOSTICS,    new Request((byte)  3, "START_DIAGNOSTICS", new byte[] { (byte) 0x02, (byte) 0x10, (byte) 0xA0, (byte) 0x00 }));
        request.put(RequestPidEnum.REQUEST_SEED,         new Request((byte)  6, "REQUEST_SEED",      new byte[] { (byte) 0x02, (byte) 0x27, (byte) 0x01, (byte) 0x00 }));
        request.put(RequestPidEnum.KEY_RETURN,           new Request((byte)  5, "KEY_RETURN",        new byte[] { (byte) 0x04, (byte) 0x27, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00 }));
        request.put(RequestPidEnum.ENGINE_RPM,           new Request((byte)  6, "ENGINE_RPM",        new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x09, (byte) 0x00 }));
        request.put(RequestPidEnum.BATTERY_VOLTAGE,      new Request((byte)  8, "BATTERY_VOLTAGE",   new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x10, (byte) 0x00 }));
        request.put(RequestPidEnum.VEHICLE_SPEED,        new Request((byte)  5, "VEHICLE_SPEED",     new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x0D, (byte) 0x00 }));
        request.put(RequestPidEnum.START_FUELLING,       new Request((byte)  8, "START_FUELLING",    new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x20, (byte) 0x00 }));
        request.put(RequestPidEnum.TEMPERATURES,         new Request((byte) 20, "TEMPERATURES",      new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x1A, (byte) 0x00 }));
        request.put(RequestPidEnum.MAP_MAF,              new Request((byte) 12, "MAP_MAF",           new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x1C, (byte) 0x00 }));
        request.put(RequestPidEnum.AMBIENT_PRESSURE,     new Request((byte)  8, "AMBIENT_PRESSURE",  new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x23, (byte) 0x00 }));
        request.put(RequestPidEnum.THROTTLE_POSITION,    new Request((byte) 12, "THROTTLE_POSITION", new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x1B, (byte) 0x00 }));
        request.put(RequestPidEnum.POWER_BALANCE,        new Request((byte) 14, "POWER_BALANCE",     new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x40, (byte) 0x00 }));
        request.put(RequestPidEnum.RPM_ERROR,            new Request((byte)  6, "RPM_ERROR",         new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x21, (byte) 0x00 }));
        request.put(RequestPidEnum.EGR_MODULE,           new Request((byte)  6, "EGR_MODULE",        new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x37, (byte) 0x00 }));
        request.put(RequestPidEnum.INLET_MODULE,         new Request((byte)  6, "INLET_MODULE",      new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x38, (byte) 0x00 }));
        request.put(RequestPidEnum.WASTEGATE_MODULE,     new Request((byte)  6, "WASTEGATE_MODULE",  new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x38, (byte) 0x00 }));
        request.put(RequestPidEnum.KEEP_ALIVE,           new Request((byte)  3, "KEEP_ALIVE",        new byte[] { (byte) 0x02, (byte) 0x3E, (byte) 0x01, (byte) 0x00 }));
        request.put(RequestPidEnum.FAULT_CODES,          new Request((byte) 39, "FAULT_CODES",       new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x3B, (byte) 0x00 }));
        request.put(RequestPidEnum.CLEAR_FAULTS,         new Request((byte)  4, "CLEAR_FAULTS",      new byte[] { (byte) 0x14, (byte) 0x31, (byte) 0xDD, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }));
        request.put(RequestPidEnum.GET_INPUTS,           new Request((byte)  6, "GET_INPUTS",        new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x1E, (byte) 0x00 }));
        request.put(RequestPidEnum.GET_FUEL_DEMAND,      new Request((byte) 22, "GET_FUEL_DEMAND",   new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x1D, (byte) 0x00 }));
        request.put(RequestPidEnum.GET_VIN,              new Request((byte) 50, "GET_VIN",           new byte[] { (byte) 0x02, (byte) 0x1A, (byte) 0x87, (byte) 0x00 }));
        request.put(RequestPidEnum.GET_ECUTYPE,          new Request((byte) 10, "GET_ECUTYPE",       new byte[] { (byte) 0x02, (byte) 0x1A, (byte) 0x9A, (byte) 0x00 }));
        request.put(RequestPidEnum.GET_MAPVARIANT,       new Request((byte) 28, "GET_MAPVARIANT",    new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x32, (byte) 0x00 }));

        request.put(RequestPidEnum.TEST_FUELPUMP,        new Request((byte)  4, "TEST_FUELPUMP",     new byte[] { (byte) 0x03, (byte) 0x30, (byte) 0xA1, (byte) 0xFF }));
        request.put(RequestPidEnum.TEST_MILLAMP,         new Request((byte)  4, "TEST_MILLAMP",      new byte[] { (byte) 0x03, (byte) 0x30, (byte) 0xA2, (byte) 0xFF }));
        request.put(RequestPidEnum.TEST_ACCLUTCH,        new Request((byte)  4, "TEST_ACCLUTCH",     new byte[] { (byte) 0x03, (byte) 0x30, (byte) 0xA3, (byte) 0xFF }));
        request.put(RequestPidEnum.TEST_ACFAN,           new Request((byte)  4, "TEST_ACFAN",        new byte[] { (byte) 0x03, (byte) 0x30, (byte) 0xA4, (byte) 0xFF }));
        request.put(RequestPidEnum.TEST_GLOW,            new Request((byte)  4, "TEST_GLOW",         new byte[] { (byte) 0x03, (byte) 0x30, (byte) 0xB3, (byte) 0xFF }));
        request.put(RequestPidEnum.TEST_PULSEREV,        new Request((byte)  4, "TEST_PULSEREV",     new byte[] { (byte) 0x03, (byte) 0x30, (byte) 0xB7, (byte) 0xFF }));
        request.put(RequestPidEnum.TEST_TEMPGAUGE,       new Request((byte)  4, "TEST_TEMPGAUGE",    new byte[] { (byte) 0x03, (byte) 0x30, (byte) 0xBA, (byte) 0xFF }));
        request.put(RequestPidEnum.TEST_INJECT1,         new Request((byte)  4, "TEST_INJECT1",      new byte[] { (byte) 0x03, (byte) 0x31, (byte) 0xC2, (byte) 0x01 }));
        request.put(RequestPidEnum.TEST_INJECT2,         new Request((byte)  4, "TEST_INJECT2",      new byte[] { (byte) 0x03, (byte) 0x31, (byte) 0xC2, (byte) 0x02 }));
        request.put(RequestPidEnum.TEST_INJECT3,         new Request((byte)  4, "TEST_INJECT3",      new byte[] { (byte) 0x03, (byte) 0x31, (byte) 0xC2, (byte) 0x03 }));
        request.put(RequestPidEnum.TEST_INJECT4,         new Request((byte)  4, "TEST_INJECT4",      new byte[] { (byte) 0x03, (byte) 0x31, (byte) 0xC2, (byte) 0x04 }));
        request.put(RequestPidEnum.TEST_INJECT5,         new Request((byte)  4, "TEST_INJECT5",      new byte[] { (byte) 0x03, (byte) 0x31, (byte) 0xC2, (byte) 0x05 }));
        request.put(RequestPidEnum.TEST_EGRMOD,          new Request((byte)  4, "TEST_EGRMOD",       new byte[] { (byte) 0x07, (byte) 0x30, (byte) 0xBD, (byte) 0xFF, (byte) 0x00, (byte) 0xFA, (byte) 0x13, (byte) 0x88 }));
        request.put(RequestPidEnum.TEST_TURBOMOD,        new Request((byte)  4, "TEST_TURBOMOD",     new byte[] { (byte) 0x07, (byte) 0x30, (byte) 0xBE, (byte) 0xFF, (byte) 0x00, (byte) 0x0A, (byte) 0x13, (byte) 0x88 }));
    }

}

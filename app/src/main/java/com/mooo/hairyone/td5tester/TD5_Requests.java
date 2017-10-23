package com.mooo.hairyone.td5tester;

import java.util.EnumMap;

public class TD5_Requests {

    class PidRequest {

        byte request_len;
        byte response_len;
        String name;
        byte[] request;

        public PidRequest(byte request_len, byte response_len, String name, byte[] request) {
            this.request_len = request_len;
            this. response_len = response_len;
            this.name = name;
            this.request = request;
        }
    }

    public static EnumMap<TD5_Pids.Pid, PidRequest> request = new EnumMap<TD5_Pids.Pid, PidRequest>(TD5_Pids.Pid.class);

    public TD5_Requests() {

        request.put(TD5_Pids.Pid.INIT_FRAME,           new PidRequest( (byte)  5, (byte)  7, "INIT_FRAME",        new byte[] { (byte) 0x81, (byte) 0x13, (byte) 0xF7, (byte) 0x81, (byte) 0x00 }));
        request.put(TD5_Pids.Pid.START_DIAGNOSTICS,    new PidRequest( (byte)  4, (byte)  3, "START_DIAGNOSTICS", new byte[] { (byte) 0x02, (byte) 0x10, (byte) 0xA0, (byte) 0x00 }));
        request.put(TD5_Pids.Pid.REQUEST_SEED,         new PidRequest( (byte)  4, (byte)  6, "REQUEST_SEED",      new byte[] { (byte) 0x02, (byte) 0x27, (byte) 0x01, (byte) 0x00 }));
        request.put(TD5_Pids.Pid.KEY_RETURN,           new PidRequest( (byte)  6, (byte)  4, "KEY_RETURN",        new byte[] { (byte) 0x04, (byte) 0x27, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00 }));
        request.put(TD5_Pids.Pid.ENGINE_RPM,           new PidRequest( (byte)  4, (byte)  6, "ENGINE_RPM",        new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x09, (byte) 0x00 }));
        request.put(TD5_Pids.Pid.BATTERY_VOLTAGE,      new PidRequest( (byte)  4, (byte)  8, "BATTERY_VOLTAGE",   new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x10, (byte) 0x00 }));
        request.put(TD5_Pids.Pid.VEHICLE_SPEED,        new PidRequest( (byte)  4, (byte)  5, "VEHICLE_SPEED",     new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x0D, (byte) 0x00 }));
        request.put(TD5_Pids.Pid.START_FUELLING,       new PidRequest( (byte)  4, (byte)  8, "START_FUELLING",    new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x20, (byte) 0x00 }));
        request.put(TD5_Pids.Pid.TEMPERATURES,         new PidRequest( (byte)  4, (byte) 20, "TEMPERATURES",      new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x1A, (byte) 0x00 }));
        request.put(TD5_Pids.Pid.MAP_MAF,              new PidRequest( (byte)  4, (byte) 12, "MAP_MAF",           new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x1C, (byte) 0x00 }));
        request.put(TD5_Pids.Pid.AMBIENT_PRESSURE,     new PidRequest( (byte)  4, (byte)  8, "AMBIENT_PRESSURE",  new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x23, (byte) 0x00 }));
        request.put(TD5_Pids.Pid.THROTTLE_POSITION,    new PidRequest( (byte)  4, (byte) 12, "THROTTLE_POSITION", new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x1B, (byte) 0x00 }));
        request.put(TD5_Pids.Pid.POWER_BALANCE,        new PidRequest( (byte)  4, (byte) 14, "POWER_BALANCE",     new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x40, (byte) 0x00 }));
        request.put(TD5_Pids.Pid.RPM_ERROR,            new PidRequest( (byte)  4, (byte)  6, "RPM_ERROR",         new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x21, (byte) 0x00 }));
        request.put(TD5_Pids.Pid.EGR_MODULE,           new PidRequest( (byte)  4, (byte)  6, "EGR_MODULE",        new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x37, (byte) 0x00 }));
        request.put(TD5_Pids.Pid.INLET_MODULE,         new PidRequest( (byte)  4, (byte)  6, "INLET_MODULE",      new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x38, (byte) 0x00 }));
        request.put(TD5_Pids.Pid.WASTEGATE_MODULE,     new PidRequest( (byte)  4, (byte)  6, "WASTEGATE_MODULE",  new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x38, (byte) 0x00 }));
        request.put(TD5_Pids.Pid.KEEP_ALIVE,           new PidRequest( (byte)  4, (byte)  3, "KEEP_ALIVE",        new byte[] { (byte) 0x02, (byte) 0x3E, (byte) 0x01, (byte) 0x00 }));
        request.put(TD5_Pids.Pid.FAULT_CODES,          new PidRequest( (byte)  4, (byte) 39, "FAULT_CODES",       new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x3B, (byte) 0x00 }));
        request.put(TD5_Pids.Pid.CLEAR_FAULTS,         new PidRequest( (byte) 22, (byte)  4, "CLEAR_FAULTS",      new byte[] { (byte) 0x14, (byte) 0x31, (byte) 0xDD, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }));
        request.put(TD5_Pids.Pid.GET_INPUTS,           new PidRequest( (byte)  4, (byte)  6, "GET_INPUTS",        new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x1E, (byte) 0x00 }));
        request.put(TD5_Pids.Pid.GET_FUEL_DEMAND,      new PidRequest( (byte)  4, (byte)  6, "GET_FUEL_DEMAND",   new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x1D, (byte) 0x00 }));

    }

}

package com.mooo.hairyone.td5tester;

public class TD5Const {

    enum Pid {
        INIT_FRAME,       START_DIAGNOSTICS, REQUEST_SEED,     KEY_RETURN,
        ENGINE_RPM,       BATTERY_VOLTAGE,   VEHICLE_SPEED,    START_FUELLING,
        TEMPERATURES,     MAP_MAF,           AMBIENT_PRESSURE, THROTTLE_POSITION,
        POWER_BALANCE,    RPM_ERROR,         EGR_MODULE,       INLET_MODULE,
        WASTEGATE_MODULE, KEEP_ALIVE,        FAULT_CODES,      CLEAR_FAULTS,
        GET_INPUTS,       GET_FUEL_DEMAND
    };

    class PidType {

        byte request_len;
        byte response_len;
        String name;
        byte[] request;

        public PidType(byte request_len, byte response_len, String name, byte[] request) {
            this.request_len = request_len;
            this. response_len = response_len;
            this.name = name;
            this.request = request;
        }
    }

    PidType[] pid_requests = new PidType[22];

    public void Td5Const() {
        pid_requests[Pid.INIT_FRAME.ordinal()]        = new PidType( (byte)  5, (byte)  7, "INIT_FRAME",        new byte[] { (byte) 0x81, (byte) 0x13, (byte) 0xF7, (byte) 0x81, (byte) 0x00 });
        pid_requests[Pid.START_DIAGNOSTICS.ordinal()] = new PidType( (byte)  4, (byte)  3, "START_DIAGNOSTICS", new byte[] { (byte) 0x02, (byte) 0x10, (byte) 0xA0, (byte) 0x00 });
        pid_requests[Pid.REQUEST_SEED.ordinal()]      = new PidType( (byte)  4, (byte)  6, "REQUEST_SEED",      new byte[] { (byte) 0x02, (byte) 0x27, (byte) 0x01, (byte) 0x00 });
        pid_requests[Pid.KEY_RETURN.ordinal()]        = new PidType( (byte)  6, (byte)  4, "KEY_RETURN",        new byte[] { (byte) 0x04, (byte) 0x27, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00 });
        pid_requests[Pid.ENGINE_RPM.ordinal()]        = new PidType( (byte)  4, (byte)  6, "ENGINE_RPM",        new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x09, (byte) 0x00 });
        pid_requests[Pid.BATTERY_VOLTAGE.ordinal()]   = new PidType( (byte)  4, (byte)  8, "BATTERY_VOLTAGE",   new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x10, (byte) 0x00 });
        pid_requests[Pid.VEHICLE_SPEED.ordinal()]     = new PidType( (byte)  4, (byte)  5, "VEHICLE_SPEED",     new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x0D, (byte) 0x00 });
        pid_requests[Pid.START_FUELLING.ordinal()]    = new PidType( (byte)  4, (byte)  8, "START_FUELLING",    new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x20, (byte) 0x00 });
        pid_requests[Pid.TEMPERATURES.ordinal()]      = new PidType( (byte)  4, (byte) 20, "TEMPERATURES",      new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x1A, (byte) 0x00 });
        pid_requests[Pid.MAP_MAF.ordinal()]           = new PidType( (byte)  4, (byte) 12, "MAP_MAF",           new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x1C, (byte) 0x00 });
        pid_requests[Pid.AMBIENT_PRESSURE.ordinal()]  = new PidType( (byte)  4, (byte)  8, "AMBIENT_PRESSURE",  new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x23, (byte) 0x00 });
        pid_requests[Pid.THROTTLE_POSITION.ordinal()] = new PidType( (byte)  4, (byte) 12, "THROTTLE_POSITION", new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x1B, (byte) 0x00 });
        pid_requests[Pid.POWER_BALANCE.ordinal()]     = new PidType( (byte)  4, (byte) 14, "POWER_BALANCE",     new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x40, (byte) 0x00 });
        pid_requests[Pid.RPM_ERROR.ordinal()]         = new PidType( (byte)  4, (byte)  6, "RPM_ERROR",         new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x21, (byte) 0x00 });
        pid_requests[Pid.EGR_MODULE.ordinal()]        = new PidType( (byte)  4, (byte)  6, "EGR_MODULE",        new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x37, (byte) 0x00 });
        pid_requests[Pid.INLET_MODULE.ordinal()]      = new PidType( (byte)  4, (byte)  6, "INLET_MODULE",      new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x38, (byte) 0x00 });
        pid_requests[Pid.WASTEGATE_MODULE.ordinal()]  = new PidType( (byte)  4, (byte)  6, "WASTEGATE_MODULE",  new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x38, (byte) 0x00 });
        pid_requests[Pid.KEEP_ALIVE.ordinal()]        = new PidType( (byte)  4, (byte)  3, "KEEP_ALIVE",        new byte[] { (byte) 0x02, (byte) 0x3E, (byte) 0x01, (byte) 0x00 });
        pid_requests[Pid.FAULT_CODES.ordinal()]       = new PidType( (byte)  4, (byte) 39, "FAULT_CODES",       new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x3B, (byte) 0x00 });
        pid_requests[Pid.CLEAR_FAULTS.ordinal()]      = new PidType( (byte) 22, (byte)  4, "CLEAR_FAULTS",      new byte[] { (byte) 0x14, (byte) 0x31, (byte) 0xDD, (byte) 0x00,
                                                                                                                             (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                                                                                                                             (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                                                                                                                             (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                                                                                                                             (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                                                                                                                             (byte) 0x00, (byte) 0x00 });
        pid_requests[Pid.GET_INPUTS.ordinal()]        = new PidType( (byte)  4, (byte)  6, "GET_INPUTS",        new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x1E, (byte) 0x00 });
        pid_requests[Pid.GET_FUEL_DEMAND.ordinal()]   = new PidType( (byte)  4, (byte)  6, "GET_FUEL_DEMAND",   new byte[] { (byte) 0x02, (byte) 0x21, (byte) 0x1D, (byte) 0x00 });

    }

}

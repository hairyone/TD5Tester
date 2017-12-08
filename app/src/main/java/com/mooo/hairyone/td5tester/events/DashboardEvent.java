package com.mooo.hairyone.td5tester.events;

public class DashboardEvent {
    public enum DATA_TYPE {
        RPM, BATTERY_VOLTAGE, VEHICLE_SPEED,
        COOLANT_TEMP, INLET_TEMP, EXTERNAL_TEMP, FUEL_TEMP,
        ACC_TRACK_1, ACC_TRACK_2, ACC_TRACK_3, ACC_SUPPLY,
        AMBIENT_PRESSURE, MANIFOLD_AIR_PRESSURE, AIR_FLOW,
        POWER_BALANCE_1, POWER_BALANCE_2, POWER_BALANCE_3, POWER_BALANCE_4, POWER_BALANCE_5
    };

    public DATA_TYPE data_type;
    public double value;

    public DashboardEvent(DATA_TYPE data_type, double value) {
        this.data_type = data_type;
        this.value = value;
    }
}
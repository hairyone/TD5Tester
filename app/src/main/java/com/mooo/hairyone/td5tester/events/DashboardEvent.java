package com.mooo.hairyone.td5tester.events;

public class DashboardEvent {
    public enum DATA_TYPE { RPM, BATTERY_VOLTAGE, VEHICLE_SPEED, COOLANT_TEMP };

    public DATA_TYPE data_type;
    public double value;

    public DashboardEvent(DATA_TYPE data_type, double value) {
        this.data_type = data_type;
        this.value = value;
    }
}
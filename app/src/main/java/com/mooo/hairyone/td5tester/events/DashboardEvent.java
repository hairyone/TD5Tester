package com.mooo.hairyone.td5tester.events;

public class DashboardEvent {
    public enum DATA_TYPE { RPM, BATTERY_VOLTAGE };

    public DATA_TYPE data_type;
    public byte[] data;

    public DashboardEvent(DATA_TYPE data_type, byte[] data) {
        this.data_type = data_type;
        this.data = data;
    }
}
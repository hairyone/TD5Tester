package com.mooo.hairyone.td5tester.fragments;

import android.content.IntentFilter;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mooo.hairyone.td5tester.Consts;
import com.mooo.hairyone.td5tester.Log4jHelper;
import com.mooo.hairyone.td5tester.R;
import com.mooo.hairyone.td5tester.events.DashboardEvent;

import org.apache.log4j.Logger;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import at.grabner.circleprogress.CircleProgressView;

public class DashboardFragment extends Fragment {

    Logger log = Log4jHelper.getLogger(this.getClass());

    CircleProgressView gRPM;
    CircleProgressView gVOLT;
    CircleProgressView gMPH;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        log.debug("");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        log.debug("");
    }

    @Override
    public void onPause() {
        log.debug("");
        super.onPause();
    }

    @Override
    public void onStop() {
        log.debug("");
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDashboardEvent(DashboardEvent event) {
        float value = (float) event.value;
        switch (event.data_type) {
            case RPM:
                gRPM.setValue(value);
                break;
            case BATTERY_VOLTAGE:
                gVOLT.setValue(value);
                break;
            case VEHICLE_SPEED:
                gMPH.setValue(value);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_fragment, container, false);
        gRPM = (CircleProgressView) view.findViewById(R.id.gRPM);
        gVOLT = (CircleProgressView) view.findViewById(R.id.gVOLT);
        gMPH = (CircleProgressView) view.findViewById(R.id.gMPH);

        gRPM.setDelayMillis(0);
        gVOLT.setDelayMillis(0);
        gMPH.setDelayMillis(0);

        gRPM.setSeekModeEnabled(false);
        gVOLT.setSeekModeEnabled(false);
        gMPH.setSeekModeEnabled(false);

        return view;
    }

}
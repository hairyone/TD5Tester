package com.mooo.hairyone.td5tester.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mooo.hairyone.td5tester.R;
import com.mooo.hairyone.td5tester.events.DashboardEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import at.grabner.circleprogress.CircleProgressView;

public class DashboardFragment extends Fragment {

    private static final String TAG = ConnectFragment.class.getSimpleName();

    CircleProgressView gRPM;
    CircleProgressView gVOLT;
    CircleProgressView gMPH;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDashboardEvent(DashboardEvent event) {
        int value = (int) event.value;
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
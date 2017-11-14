package com.mooo.hairyone.td5tester.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mooo.hairyone.td5tester.Consts;
import com.mooo.hairyone.td5tester.R;
import com.mooo.hairyone.td5tester.Util;
import com.mooo.hairyone.td5tester.events.BusyEvent;
import com.mooo.hairyone.td5tester.events.DashboardEvent;
import com.mooo.hairyone.td5tester.events.MessageEvent;
import com.mooo.hairyone.td5tester.events.NotConnectedEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Random;

import de.nitri.gauge.Gauge;

public class DashboardFragment extends Fragment {

    private static final String TAG = ConnectFragment.class.getSimpleName();

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

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onDashboardEvent(DashboardEvent event) {
        gaugeRpm.setValue(new Random().nextInt(101 - 5) + 5);
    }

    Gauge gaugeRpm;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_fragment, container, false);
        gaugeRpm = (Gauge) view.findViewById(R.id.gaugeRpm);
        gaugeRpm.moveToValue(90);
        return view;
    }

}
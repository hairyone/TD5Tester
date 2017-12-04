package com.mooo.hairyone.td5tester.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mooo.hairyone.td5tester.Log4jHelper;
import com.mooo.hairyone.td5tester.R;
import com.mooo.hairyone.td5tester.events.DashboardEvent;

import org.apache.log4j.Logger;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import at.grabner.circleprogress.CircleProgressView;

public class TemperatureFragment extends Fragment {

    Logger log = Log4jHelper.getLogger(this.getClass());

    CircleProgressView gCOOLANT_TEMP;
    CircleProgressView gINLET_TEMP;
    CircleProgressView gFUEL_TEMP;
    CircleProgressView gAMBIENT_PRESSURE;
    CircleProgressView gAIR_FLOW;
    CircleProgressView gMANIFOLD_AIR_PRESSURE;

    public TemperatureFragment() { /* Required empty public constructor*/ }

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
            case COOLANT_TEMP:
                gCOOLANT_TEMP.setValue(value);
                break;
            case INLET_TEMP:
                gINLET_TEMP.setValue(value);
                break;
            case FUEL_TEMP:
                gFUEL_TEMP.setValue(value);
                break;
            case AMBIENT_PRESSURE:
                gAMBIENT_PRESSURE.setValue(value);
                break;
            case MANIFOLD_AIR_PRESSURE:
                gMANIFOLD_AIR_PRESSURE.setValue(value);
                break;
            case AIR_FLOW:
                gAIR_FLOW.setValue(value);
                break;

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log.debug("");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        log.debug("");
        View view = inflater.inflate(R.layout.temperature_fragment, container, false);
        gCOOLANT_TEMP = (CircleProgressView) view.findViewById(R.id.gCOOLANT_TEMP);
        gINLET_TEMP = (CircleProgressView) view.findViewById(R.id.gINLET_TEMP);
        gFUEL_TEMP = (CircleProgressView) view.findViewById(R.id.gFUEL_TEMP);
        gAMBIENT_PRESSURE = (CircleProgressView) view.findViewById(R.id.gAMBIENT_PRESSURE);
        gAIR_FLOW = (CircleProgressView) view.findViewById(R.id.gAIR_FLOW);
        gMANIFOLD_AIR_PRESSURE = (CircleProgressView) view.findViewById(R.id.gMANIFOLD_AIR_PRESSURE);

        /*
        gCOOLANT_TEMP.setDelayMillis(0);
        gINLET_TEMP.setDelayMillis(0);
        gFUEL_TEMP.setDelayMillis(0);
        gEXTERNAL_TEMP.setDelayMillis(0);
        gAMBIENT_PRESSURE.setDelayMillis(0);
        gAIR_FLOW.setDelayMillis(0);
        gMANIFOLD_AIR_PRESSURE.setDelayMillis(0);
        */

        return view;
    }

}
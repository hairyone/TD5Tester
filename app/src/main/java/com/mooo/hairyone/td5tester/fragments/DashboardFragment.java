package com.mooo.hairyone.td5tester.fragments;

import com.mooo.hairyone.td5tester.Log4jHelper;
import com.mooo.hairyone.td5tester.R;
import com.mooo.hairyone.td5tester.events.DashboardEvent;

import org.apache.log4j.Logger;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import at.grabner.circleprogress.CircleProgressView;
import butterknife.BindView;
import butterknife.Unbinder;

public class DashboardFragment extends BaseFragment {

    Logger log = Log4jHelper.getLogger(this.getClass());

    @BindView(R.id.gRPM) CircleProgressView gRPM;
    @BindView(R.id.gVOLT) CircleProgressView gVOLT;
    @BindView(R.id.gMPH) CircleProgressView gMPH;

    public DashboardFragment() { /* Required empty public constructor */ }

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

    @Override protected int getFragmentLayout() { return R.layout.dashboard_fragment; }

}
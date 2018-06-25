package com.mooo.hairyone.td5tester.fragments;

import com.mooo.hairyone.td5tester.Log4jHelper;
import com.mooo.hairyone.td5tester.R;
import com.mooo.hairyone.td5tester.events.DashboardEvent;

import org.apache.log4j.Logger;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import at.grabner.circleprogress.CircleProgressView;
import butterknife.BindView;

public class TemperatureAndPressureFragment extends BaseFragment {

    Logger log = Log4jHelper.getLogger(this.getClass());

    @BindView(R.id.gCOOLANT_TEMP) CircleProgressView gCOOLANT_TEMP;
    @BindView(R.id.gINLET_TEMP) CircleProgressView gINLET_TEMP;
    @BindView(R.id.gFUEL_TEMP) CircleProgressView gFUEL_TEMP;
    @BindView(R.id.gAMBIENT_PRESSURE) CircleProgressView gAMBIENT_PRESSURE;
    @BindView(R.id.gAIR_FLOW) CircleProgressView gAIR_FLOW;
    @BindView(R.id.gMANIFOLD_AIR_PRESSURE) CircleProgressView gMANIFOLD_AIR_PRESSURE;

    public TemperatureAndPressureFragment() { /* Required empty public constructor*/ }

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

    @Override protected int getFragmentLayout() { return R.layout.temperature_and_pressure_fragment; }

}
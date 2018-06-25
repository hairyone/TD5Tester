package com.mooo.hairyone.td5tester.fragments;

import com.mooo.hairyone.td5tester.Log4jHelper;
import com.mooo.hairyone.td5tester.R;
import com.mooo.hairyone.td5tester.events.DashboardEvent;

import org.apache.log4j.Logger;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import at.grabner.circleprogress.CircleProgressView;
import butterknife.BindView;

public class FuelDemandFragment extends BaseFragment {

    Logger log = Log4jHelper.getLogger(this.getClass());

    @BindView(R.id.gDRIVER_DEMAND)      CircleProgressView gDRIVER_DEMAND;
    @BindView(R.id.gMAF_AIR_MASS)       CircleProgressView gMAF_AIR_MASS;
    @BindView(R.id.gMAP_AIR_MASS)       CircleProgressView gMAP_AIR_MASS;
    @BindView(R.id.gINJECTION_QUANTITY) CircleProgressView gINJECTION_QUANTITY;
    @BindView(R.id.gAF_RATIO)           CircleProgressView gAF_RATIO;
    @BindView(R.id.gTORQUE_LIMIT)       CircleProgressView gTORQUE_LIMIT;
    @BindView(R.id.gSMOKE_LIMIT)        CircleProgressView gSMOKE_LIMIT;
    @BindView(R.id.gIDLE_DEMAND)        CircleProgressView gIDLE_DEMAND;

    public FuelDemandFragment() { /* Required empty public constructor*/ }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDashboardEvent(DashboardEvent event) {
        float value = (float) event.value;
        switch (event.data_type) {
            case DRIVER_DEMAND:      gDRIVER_DEMAND.setValue(value);      break;
            case MAF_AIR_MASS:       gMAF_AIR_MASS.setValue(value);       break;
            case MAP_AIR_MASS:       gMAP_AIR_MASS.setValue(value);       break;
            case INJECTION_QUANTITY: gINJECTION_QUANTITY.setValue(value); break;
            case AF_RATIO:           gAF_RATIO.setValue(value);           break;
            case TORQUE_LIMIT:       gTORQUE_LIMIT.setValue(value);       break;
            case SMOKE_LIMIT:        gSMOKE_LIMIT.setValue(value);        break;
            case IDLE_DEMAND:        gIDLE_DEMAND.setValue(value);        break;
        }
    }

    @Override protected int getFragmentLayout() { return R.layout.fuel_demand_fragment; }

}
package com.mooo.hairyone.td5tester.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mooo.hairyone.td5tester.Log4jHelper;

import org.apache.log4j.Logger;
import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    Logger log = Log4jHelper.getLogger(this.getClass());

    Unbinder unbinder;

    @Override public void onAttach(Activity activity) {
        // This is where a fragment is associated with an activity. Keep in mind that when this
        // method is called, your Fragment, and or, Activity is not fully initialized and you will
        // receive a reference to the activity that its associated with.
        super.onAttach(activity);
        log.trace("");
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        // OnCreate gets called when a fragment is first created. Actions like Creating Views & View
        // Groups, Initialization are generally done in OnCreate Method.
        super.onCreate(savedInstanceState);
        log.trace("");
    }

    // Every fragment has to inflate a layout in the onCreateView method. We have added this method
    // to avoid duplicate all the inflate code in every fragment. You only have to return the layout
    // to inflate in this method when extends BaseFragment.
    protected abstract int getFragmentLayout();

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // OnCreateView gets called when Android is ready draw fragment user interface. To draw UI
        // for the fragment we must return a View Component from this method.
        log.trace("");
        View view =  inflater.inflate(getFragmentLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        log.trace("");
    }

    @Override public void onActivityCreated (Bundle savedInstanceState) {
        // OnActivityCreated gets called when the Activity that the fragment is associated with
        // gets created. This is where we can use our handy-dandy FindViewById<T> methods to find
        // views in Activity.
        super.onActivityCreated(savedInstanceState);
        log.trace("");
    }

    @Override public void onStart() {
        // OnStart method is generally used to refresh any data in the View and View Groups, this
        // method gets called when the Fragment is first visible.
        super.onStart();
        log.trace("");
        EventBus.getDefault().register(this);
    }

    @Override public void onResume() {
        super.onResume();
        log.trace("");
    }

    @Override public void onPause() {
        // OnPause gets called when the Fragment is about go into background. This method is
        // generally overridden to save any non-persisted information from the user interface.
        log.trace("");
        super.onPause();
    }

    @Override public void onStop() {
        // OnStop is called when the Fragment is no longer visible to the user and is being stopped.
        EventBus.getDefault().unregister(this);
        log.trace("");
        super.onStop();
    }

    @Override public void onDestroy() {
        // OnDestroy is called when a Fragment is destroyed completely. There are scenario when
        // Android will not call OnDestroy, so don’t count on doing any persistence in OnDestroy.
        log.trace("");
        super.onDestroy();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }


}

package com.mooo.hairyone.td5tester;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.mooo.hairyone.td5tester.fragments.ConnectFragment;
import com.mooo.hairyone.td5tester.fragments.DashboardFragment;
import com.mooo.hairyone.td5tester.fragments.TemperatureAndPressure;

import org.apache.log4j.Logger;

public class MainActivity extends AppCompatActivity {
    Logger log = Log4jHelper.getLogger(this.getClass());

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        // https://stackoverflow.com/questions/4544534/the-xml-is-not-switching-when-device-orientation-change
        super.onConfigurationChanged(newConfig);
        log.debug("");
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String log_filename = MainActivity.this.getExternalFilesDir(null).getAbsolutePath() + File.separator + "td5tester.log";
        Log4jHelper.configure(log_filename);
        if(!(Thread.getDefaultUncaughtExceptionHandler() instanceof CustomExceptionHandler)) {
            Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler());
        }
        log.debug("-------------------------------------------");
        log.info(String.format("logging to %s", log_filename));

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayShowHomeEnabled(false);
        //getSupportActionBar().setDisplayShowTitleEnabled(true);
        //getSupportActionBar().setDisplayUseLogoEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        // https://stackoverflow.com/questions/8348707/prevent-viewpager-from-destroying-off-screen-views
        viewPager.setOffscreenPageLimit(3);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ConnectFragment(), "Connect");
        adapter.addFragment(new DashboardFragment(), "Dashboard");
        adapter.addFragment(new TemperatureAndPressure(), "Temp & Pressure");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
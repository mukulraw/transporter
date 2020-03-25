package com.sumit.myapplication.driver;


import android.content.Context;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sumit.myapplication.driver.drivertablayout.DriverPastFragment;
import com.sumit.myapplication.driver.drivertablayout.DriverViewPagerAdapter;
import com.sumit.myapplication.R;
import com.sumit.myapplication.driver.drivertablayout.DriverUpComingFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderDriverFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    DriverViewPagerAdapter driverViewPagerAdapter;

    public MyOrderDriverFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        driverViewPagerAdapter =new DriverViewPagerAdapter(getChildFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_my_order_driver, container, false);
        viewPager = view.findViewById(R.id.view_pager);
        driverViewPagerAdapter.addFragment(new DriverUpComingFragment(),"Upcoming");
        driverViewPagerAdapter.addFragment(new DriverPastFragment(),"Past");
        viewPager.setAdapter(driverViewPagerAdapter);
        tabLayout=view.findViewById(R.id.tablayout_id);
        tabLayout.setupWithViewPager(viewPager);

        return  view;
    }

}

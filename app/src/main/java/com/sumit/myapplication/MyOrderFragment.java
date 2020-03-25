package com.sumit.myapplication;


import android.content.Context;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sumit.myapplication.tablayout.UpComingFragment;
import com.sumit.myapplication.tablayout.PastFragment;
import com.sumit.myapplication.tablayout.ViewPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderFragment extends Fragment {


    //initialization
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    public MyOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);
        // toolbar=view.findViewById(R.id.toolbar);
        //((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        viewPager = view.findViewById(R.id.view_pager);
        viewPagerAdapter.addFragment(new UpComingFragment(), "Upcoming");
        viewPagerAdapter.addFragment(new PastFragment(), "Past");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = view.findViewById(R.id.tablayout_id);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}

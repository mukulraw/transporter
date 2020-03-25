package com.mukul.onnwaytransporter.truck;

import android.content.Context;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mukul.onnwaytransporter.tablayout.PartLoadPostTruck;
import com.mukul.onnwaytransporter.tablayout.ViewPagerPostTabAdapter;
import com.mukul.onnwaytransporter.PostTruckFragment;
import com.mukul.onnwaytransporter.R;

public class PostTruckFrag extends Fragment
{

    //initialization
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerPostTabAdapter viewPagerPostTabAdapter;
    public PostTruckFrag() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewPagerPostTabAdapter=new ViewPagerPostTabAdapter(getChildFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.post_frag, container, false);
        // toolbar=view.findViewById(R.id.toolbar);
        //((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        viewPager=view.findViewById(R.id.view_pager);
        viewPagerPostTabAdapter.addFragment(new PostTruckFragment(),"FullLoad");
        viewPagerPostTabAdapter.addFragment(new PartLoadPostTruck(),"Partload");
        viewPager.setAdapter(viewPagerPostTabAdapter);
        tabLayout=view.findViewById(R.id.tablayout_id);
        tabLayout.setupWithViewPager(viewPager);

        return  view;
    }
}

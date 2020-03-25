package com.sumit.myapplication.tablayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter
{
    private final List<Fragment> fragmentList=new ArrayList<>();
    private final List<String> fragentTitlrlist=new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fragmentManager)
    {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragentTitlrlist.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragentTitlrlist.get(position);
    }

    public void addFragment(Fragment fragment,String mTitle)
    {
        fragmentList.add(fragment);
        fragentTitlrlist.add(mTitle);
    }
}

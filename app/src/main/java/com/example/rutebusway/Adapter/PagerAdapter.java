package com.example.rutebusway.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.rutebusway.Fragment.ListFragment;
import com.example.rutebusway.Fragment.AboutFragment;
import com.example.rutebusway.Fragment.TesAlgoritmaFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0 :
                TesAlgoritmaFragment tesAlgoritmaFragment = new TesAlgoritmaFragment();
                return tesAlgoritmaFragment;
            case 1 :
                ListFragment listFragment = new ListFragment();
                return listFragment;
            /*case 2 :
                AboutFragment aboutFragment = new AboutFragment();
                return aboutFragment;*/

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}

package com.ajbecknerapps.productivityhomescreen;
/*
Created by AJ Beckner on 7/29/15.
Copyright (c) 2014 ajbecknerapps. All rights reserved.
*/

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

//Todo: update javadoc
/**
 * Holds instances of ChampionListFragment and FavoriteSoundListFragment and allows swiping and clicking
 * to navigate between them; updates views upon swipe when the favorite list changes
 */

public class TabbedPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    /** Connects activity to empty frame and creates two fragments*/
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(null);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);
        //new fragments if none exist
        if (fragmentArrayList.size() == 0) {
            fragmentArrayList.add(new TieredListFragment());
            fragmentArrayList.add(new NerdLauncherFragment());
        }


        //sets up manager, pager, and adapter
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {

                Log.d("tag","getting item");
                return fragmentArrayList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentArrayList.size();
            }

        });

    }
    public void newListFragment(Fragment fragment1, Fragment fragment2){
        fragmentArrayList.remove(fragment1);
        fragmentArrayList.add(fragment2);

    }
}


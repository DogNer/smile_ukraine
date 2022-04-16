package com.example.smile_ukraine;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.smile_ukraine.screen.Person_main;
import com.example.smile_ukraine.screen.Personal_settings;
import com.example.smile_ukraine.screen.Status;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new Person_main();
            case 1: return new Status();
            case 2: return new Personal_settings();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "MENU";
            case 1: return "FRIENDS";
            case 2: return "PROFILE";
        }
        return null;
    }
}

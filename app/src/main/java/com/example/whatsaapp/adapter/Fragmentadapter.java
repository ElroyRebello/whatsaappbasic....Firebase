package com.example.whatsaapp.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.whatsaapp.fragments.callFragment;
import com.example.whatsaapp.fragments.chatsFragment;
import com.example.whatsaapp.fragments.statusFragment;

public class Fragmentadapter  extends FragmentPagerAdapter {
    public Fragmentadapter(@NonNull @org.jetbrains.annotations.NotNull FragmentManager fm) {
        super(fm);
    }



    @NonNull

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:return new chatsFragment();

            case 1: return new statusFragment();

            case 2: return  new callFragment();

            default:return  new chatsFragment();

        }


    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title=null;
        switch (position)
        {
            case 0:
                title="chat";
                return title;

            case 1: title="status";
                return title;

            case 2: title="call";
                return title;



        }

        return  null;
    }
}

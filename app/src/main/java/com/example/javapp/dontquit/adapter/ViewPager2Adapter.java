package com.example.javapp.dontquit.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.javapp.dontquit.view.fragment.Frag1;
import com.example.javapp.dontquit.view.fragment.Frag2;
import com.example.javapp.dontquit.view.fragment.Frag3;

public class ViewPager2Adapter extends FragmentStateAdapter
{

    private int mPageCount= 3;

    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity){
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new Frag1();
            case 1:
                return new Frag2();
            case 2:
                return new Frag3();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return mPageCount;
    }
}

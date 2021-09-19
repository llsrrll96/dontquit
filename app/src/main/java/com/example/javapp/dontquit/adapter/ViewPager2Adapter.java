package com.example.javapp.dontquit.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.javapp.dontquit.domain.Category;
import com.example.javapp.dontquit.view.fragment.FragHscodeInfo;
import com.example.javapp.dontquit.view.fragment.FragTariff;
import com.example.javapp.dontquit.view.fragment.FragStats;

public class ViewPager2Adapter extends FragmentStateAdapter
{

    private int mPageCount= 3;
    private Category category;

    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity, Category category){
        super(fragmentActivity);
        this.category = category;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new FragHscodeInfo(category);
            case 1:
                return new FragTariff(category.getHscode());
            case 2:
                return new FragStats();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return mPageCount;
    }
}

package com.example.javapp.dontquit.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import com.example.javapp.dontquit.R;
import com.example.javapp.dontquit.adapter.ViewPager2Adapter;
import com.example.javapp.dontquit.domain.Category;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;


//https://helloit.tistory.com/309
public class TabActivity extends AppCompatActivity {

    private TabLayout tabLayout ;
    private ViewPager2 viewPager2;
    private ViewPager2Adapter viewPager2Adapter;
    private Context mContext;
    private Intent getIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        mContext = this;
        init();

        final List<String> tabElement = Arrays.asList("품목분류해설","관세율","통계");

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                TextView textView = new TextView(mContext);
                textView.setText(tabElement.get(position));
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(ContextCompat.getColor(mContext,R.color.black));
                textView.setTypeface(ResourcesCompat.getFont(mContext,R.font.font_kotra_bold));
                tab.setCustomView(textView);
            }
        }).attach();
    }

    private void init()
    {
        getIntent=  getIntent();

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager2 = (ViewPager2) findViewById(R.id.viewPager2_container);
        viewPager2Adapter = new ViewPager2Adapter(this,(Category) getIntent.getSerializableExtra("data"));
        viewPager2.setAdapter(viewPager2Adapter);
    }
}
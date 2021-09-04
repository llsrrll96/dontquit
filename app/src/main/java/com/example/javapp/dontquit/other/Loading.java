package com.example.javapp.dontquit.other;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class Loading
{
    public Loading() {
    }

    public static void showLoading(Activity activity, boolean isShow) {
        if(isShow) {
            LinearLayout linear = new LinearLayout(activity);
            linear.setTag("MyProgressBar");
            linear.setGravity(Gravity.CENTER);
            linear.setBackgroundColor(0x33000000);
            ProgressBar progressBar = new ProgressBar(activity);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            progressBar.setLayoutParams(layoutParams);
            linear.addView(progressBar);
            linear.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) { /*클릭방지*/ }
            });

            FrameLayout rootView = activity.findViewById(android.R.id.content);
            rootView.addView(linear);
        } else {
            FrameLayout rootView = activity.findViewById(android.R.id.content);
            LinearLayout linear = rootView.findViewWithTag("MyProgressBar");
            if(linear != null) {
                rootView.removeView(linear);
            }
        }
    }
}

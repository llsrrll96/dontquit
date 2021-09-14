package com.example.javapp.dontquit.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.javapp.dontquit.R;
import com.example.javapp.dontquit.domain.Category;
import com.example.javapp.dontquit.listener.OnSingleClickListener;
import com.example.javapp.dontquit.view.HistoryActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag1 extends Fragment {
    private Category category;
    private TextView tvHscode;
    private TextView tvUnitName;
    private TextView tvDiv;
    private ImageView iv;
    private Button btnHistory;

    public Frag1(Category category) {
        this.category = category;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setViewinit(view);
        setViewText();
        Log.v("category",category.getUnitName());
    }

    private void setViewinit(View view)
    {
        tvHscode = (TextView) view.findViewById(R.id.tv_frag1_hscode);
        tvUnitName = (TextView) view.findViewById(R.id.tv_frag1_unit);
        tvDiv = (TextView) view.findViewById(R.id.tv_frag1_div);
        iv = (ImageView)view.findViewById(R.id.iv_frag1);
        btnHistory = (Button) view.findViewById(R.id.button_flag1_history);
        nextHistoryActivity();
    }

    private void setViewText()
    {
        tvHscode.setText(category.getHscode());
        tvUnitName.setText(category.getUnitName());
        tvDiv.setText(category.getDivinityName());
    }

    private void nextHistoryActivity()
    {
        btnHistory.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(v.getContext(), HistoryActivity.class);
                intent.putExtra("hscode",category.getHscode());
                startActivity(intent);
            }
        });
    }

}
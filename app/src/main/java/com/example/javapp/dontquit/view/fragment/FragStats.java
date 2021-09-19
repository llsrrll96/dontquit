package com.example.javapp.dontquit.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.javapp.dontquit.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragStats#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragStats extends Fragment {
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag3, container, false);
    }
}
package com.example.javapp.dontquit.adapter;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.javapp.dontquit.R;
import com.example.javapp.dontquit.other.SpinnerCountry;
import com.example.javapp.dontquit.other.SpinnerItemRow;

import java.util.ArrayList;

public class SpinnerFrag2Adapter extends ArrayAdapter<SpinnerItemRow>
{
    public SpinnerFrag2Adapter(@NonNull Context context, ArrayList<SpinnerItemRow> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        return initView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getDropDownView(position, convertView, parent);
        return initView(position,convertView,parent);
    }

    private View initView(int position, View convertView,ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.frag2_spinner_row,parent,false
            );
        }

        ImageView imageView = convertView.findViewById(R.id.iv_frag2_spinner_row);
        TextView textView = convertView.findViewById(R.id.tv_frag2_spinner_row);

        Log.v("getItem(position)", getItem(position).getCountryCode());

        SpinnerItemRow spinnerItemRow =  getItem(position);

        if(spinnerItemRow != null)
        {
            imageView.setImageResource(SpinnerCountry.valueOf(spinnerItemRow.getCountryCode()).getIcon());
            textView.setText(SpinnerCountry.valueOf(spinnerItemRow.getCountryCode()).getCountryName());
        }
        return convertView;
    }
}

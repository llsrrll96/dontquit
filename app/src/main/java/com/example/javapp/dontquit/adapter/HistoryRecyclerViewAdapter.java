package com.example.javapp.dontquit.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javapp.dontquit.R;
import com.example.javapp.dontquit.domain.History;

import java.util.ArrayList;
import java.util.List;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.HistoryRecyclerViewHoler>
{
    Context mContext;
    List<History> historyList;

    public class HistoryRecyclerViewHoler extends RecyclerView.ViewHolder
    {
        TextView textView ;

        public HistoryRecyclerViewHoler(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_his_re_it_item);
        }
    }

    public HistoryRecyclerViewAdapter(Context mContext, List<History> historyList) {
        this.mContext = mContext;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryRecyclerViewAdapter.HistoryRecyclerViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.history_recylerview_item, parent, false);
        HistoryRecyclerViewAdapter.HistoryRecyclerViewHoler viewHoler = new HistoryRecyclerViewAdapter.HistoryRecyclerViewHoler(view);
        return  viewHoler;
    }



    @Override
    public void onBindViewHolder(@NonNull HistoryRecyclerViewAdapter.HistoryRecyclerViewHoler holder, int position) {
        holder.textView.setText(historyList.get(position).getNm());
    }


    @Override
    public int getItemCount() {
        Log.v("historyList.size()",historyList.size()+"");
        return historyList.size();
    }

}

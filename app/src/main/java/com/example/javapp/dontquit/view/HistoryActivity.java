package com.example.javapp.dontquit.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.javapp.dontquit.R;
import com.example.javapp.dontquit.adapter.HistoryRecyclerViewAdapter;
import com.example.javapp.dontquit.contract.HistoryContract;
import com.example.javapp.dontquit.domain.History;
import com.example.javapp.dontquit.other.Loading;
import com.example.javapp.dontquit.presenter.HistoryPresenter;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.List;

public class HistoryActivity extends AppCompatActivity implements HistoryContract.View{

    private String hscode=null;

    private HistoryPresenter presenter;

    private RecyclerView recyclerviewHistory;
    private FlexboxLayoutManager layoutManager;
    private HistoryRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        init();
    }

    private void init()
    {
        try{
            recyclerviewHistory = (RecyclerView)findViewById(R.id.recyclerview_history);
            layoutManager = new FlexboxLayoutManager(this);
            layoutManager.setFlexDirection(FlexDirection.ROW);
            layoutManager.setFlexWrap(FlexWrap.WRAP);
            recyclerviewHistory.setLayoutManager(layoutManager);

            hscode = getIntent().getStringExtra("hscode");

            Log.v("hscode",hscode);
            if(hscode == null || hscode.equals("")) finish();

            presenter = new HistoryPresenter((HistoryContract.View) this);
            presenter.requestHistoryFromServer(hscode);

        }catch (Exception e){
            finish();
        }

    }


    @Override
    public void showResult(List<History> historyList)
    {
        adapter = new HistoryRecyclerViewAdapter(this,historyList);
        recyclerviewHistory.setAdapter(adapter);
    }

    @Override
    public void showProgress() {
        Loading.showLoading(this,true);
    }

    @Override
    public void hideProgress() {
        Loading.showLoading(this,false);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG);
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Toast.makeText(this,"Fail",Toast.LENGTH_LONG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
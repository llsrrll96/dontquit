package com.example.javapp.dontquit.model;

import com.example.javapp.dontquit.contract.HistoryContract;
import com.example.javapp.dontquit.contract.ProductListContract;
import com.example.javapp.dontquit.domain.History;
import com.example.javapp.dontquit.network.ApiClient;
import com.example.javapp.dontquit.network.Apiinterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryListModel implements HistoryContract.Model
{
    List<History> historyList = new ArrayList<>();
    final Apiinterface service = ApiClient.getInstance().create(Apiinterface.class);

    @Override
    public void getHistoryList(onFinishedListener onFinishedListener, String hsk)
    {
        Call<List<History>> call = service.getHistoyList(hsk);

        call.enqueue(new Callback<List<History>>() {
            @Override
            public void onResponse(Call<List<History>> call, Response<List<History>> response)
            {
                if(!response.isSuccessful()){
                    onFinishedListener.onFailure(
                            response.message()
                    );
                    return;
                }
                historyList = response.body();
                onFinishedListener.onFinished(historyList);
            }

            @Override
            public void onFailure(Call<List<History>> call, Throwable t)
            {
                onFinishedListener.onFailure(t.getMessage());
            }
        });

    }
}

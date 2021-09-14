package com.example.javapp.dontquit.model;

import com.example.javapp.dontquit.contract.TariffContract;
import com.example.javapp.dontquit.domain.TariffCNLists;
import com.example.javapp.dontquit.domain.TariffRequestBody;
import com.example.javapp.dontquit.domain.TariffEUnUSALists;
import com.example.javapp.dontquit.network.ApiClient;
import com.example.javapp.dontquit.network.Apiinterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TariffListModel implements TariffContract.Model
{
    private TariffEUnUSALists tariffEULists;
    private TariffEUnUSALists tariffEUnUSALists;
    private TariffCNLists tariffCNLists;
    final Apiinterface service = ApiClient.getInstance().create(Apiinterface.class);

    @Override
    public void getTariffEUFromServer(OnFinishedListener onFinishedListener, TariffRequestBody tariffRequestBody)
    {
        Call<TariffEUnUSALists> call = service.getTariffEUList(tariffRequestBody);

        call.enqueue(new Callback<TariffEUnUSALists>() {
            @Override
            public void onResponse(Call<TariffEUnUSALists> call, Response<TariffEUnUSALists> response)
            {
                if(!response.isSuccessful())
                {
                    onFinishedListener.onFailure(response.message());
                    return;
                }
                tariffEULists = response.body();
                onFinishedListener.onFinishedEU(tariffEULists);
            }

            @Override
            public void onFailure(Call<TariffEUnUSALists> call, Throwable t) {
                onFinishedListener.onFailure("불러오기 실패");
            }
        });
    }

    @Override
    public void getTariffUSAFromServer(OnFinishedListener onFinishedListener, TariffRequestBody tariffRequestBody)
    {
        Call<TariffEUnUSALists> call = service.getTariffUSAList(tariffRequestBody);

        call.enqueue(new Callback<TariffEUnUSALists>()
        {
            @Override
            public void onResponse(Call<TariffEUnUSALists> call, Response<TariffEUnUSALists> response)
            {
                if(!response.isSuccessful())
                {
                    onFinishedListener.onFailure(response.message());
                    return;
                }
                tariffEUnUSALists = response.body();
                onFinishedListener.onFinishedUSA(tariffEUnUSALists);
            }

            @Override
            public void onFailure(Call<TariffEUnUSALists> call, Throwable t)
            {
                onFinishedListener.onFailure("불러오기 실패");
            }
        });
    }

    @Override
    public void getTariffCNFromServer(OnFinishedListener onFinishedListener, TariffRequestBody tariffRequestBody)
    {
        Call<TariffCNLists>call =service.getTariffCNList(tariffRequestBody);

        call.enqueue(new Callback<TariffCNLists>()
        {
            @Override
            public void onResponse(Call<TariffCNLists> call, Response<TariffCNLists> response)
            {
                if(!response.isSuccessful()){
                    onFinishedListener.onFailure(response.message());
                    return;
                }
                tariffCNLists = response.body();
                onFinishedListener.onFinishedCN(tariffCNLists);
            }

            @Override
            public void onFailure(Call<TariffCNLists> call, Throwable t)
            {
                // presenter
                onFinishedListener.onFailure("불러오기 실패");
            }
        });
    }
}

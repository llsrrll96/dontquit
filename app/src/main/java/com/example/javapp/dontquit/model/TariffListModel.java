package com.example.javapp.dontquit.model;

import com.example.javapp.dontquit.contract.TariffContract;
import com.example.javapp.dontquit.domain.TariffCN;
import com.example.javapp.dontquit.domain.TariffRequestBody;
import com.example.javapp.dontquit.domain.TariffUSA;
import com.example.javapp.dontquit.network.ApiClient;
import com.example.javapp.dontquit.network.Apiinterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TariffListModel implements TariffContract.Model
{
//    private List<TariffCN> tariffCNList = new ArrayList<>();
    private List<TariffUSA> tariffUSAList = new ArrayList<>();
    private List<TariffCN> tariffCNList = new ArrayList<>();
    final Apiinterface service = ApiClient.getInstance().create(Apiinterface.class);

    @Override
    public void getTariffEUFromServer(OnFinishedListener onFinishedListener, TariffRequestBody tariffRequestBody)
    {
    }

    @Override
    public void getTariffUSAFromServer(OnFinishedListener onFinishedListener, TariffRequestBody tariffRequestBody)
    {
        Call<List<TariffUSA>> call = service.getTariffUSAList(tariffRequestBody);

        call.enqueue(new Callback<List<TariffUSA>>()
        {
            @Override
            public void onResponse(Call<List<TariffUSA>> call, Response<List<TariffUSA>> response)
            {
                if(!response.isSuccessful())
                {
                    onFinishedListener.onFailure(response.message());
                    return;
                }
                tariffUSAList = response.body();
                onFinishedListener.onFinishedUSA(tariffUSAList);
            }

            @Override
            public void onFailure(Call<List<TariffUSA>> call, Throwable t)
            {

            }
        });
    }

    @Override
    public void getTariffCNFromServer(OnFinishedListener onFinishedListener, TariffRequestBody tariffRequestBody)
    {
        Call<List<TariffCN>>call =service.getTariffCNList(tariffRequestBody);

        call.enqueue(new Callback<List<TariffCN>>()
        {
            @Override
            public void onResponse(Call<List<TariffCN>> call, Response<List<TariffCN>> response)
            {
                if(!response.isSuccessful()){
                    onFinishedListener.onFailure(response.message());
                    return;
                }
                tariffCNList = response.body();
                onFinishedListener.onFinishedCN(tariffCNList);
            }

            @Override
            public void onFailure(Call<List<TariffCN>> call, Throwable t)
            {
                // presenter
                onFinishedListener.onFailure(t.getMessage());
            }
        });
    }
}

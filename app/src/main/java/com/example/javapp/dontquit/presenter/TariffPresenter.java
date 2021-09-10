package com.example.javapp.dontquit.presenter;

import com.example.javapp.dontquit.contract.TariffContract;
import com.example.javapp.dontquit.domain.TariffCN;
import com.example.javapp.dontquit.domain.TariffRequestBody;
import com.example.javapp.dontquit.domain.TariffUSA;
import com.example.javapp.dontquit.model.TariffListModel;

import java.util.List;

public class TariffPresenter implements TariffContract.Presenter, TariffContract.Model.OnFinishedListener
{
    private TariffContract.View view;
    private TariffContract.Model model;

    public TariffPresenter(TariffContract.View view)
    {
        this.view = view;
        this.model = new TariffListModel();
    }

    @Override
    public void onFinishedUSA(List<TariffUSA> tariffUSAList)
    {
        if(view != null){
            view.hideProgress();
            view.showResultUSA(tariffUSAList);
        }
    }

    @Override
    public void onFinishedCN(List<TariffCN> tariffCNList)
    {
        if(view != null){
            view.hideProgress();
            view.showResultCN(tariffCNList);
        }
    }

    @Override
    public void onFailure(String message)
    {
        if(view != null){
            view.hideProgress();
            view.showToast(message);
        }
    }


    @Override
    public void requestTariffFromServer(TariffRequestBody tariffRequestBody)
    {
        if(view != null) view.showProgress();

        if(tariffRequestBody.getCountry()=="EU") model.getTariffEUFromServer(this, tariffRequestBody);
        else if(tariffRequestBody.getCountry()=="USA") model.getTariffUSAFromServer(this, tariffRequestBody);
        else model.getTariffCNFromServer(this, tariffRequestBody);
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}

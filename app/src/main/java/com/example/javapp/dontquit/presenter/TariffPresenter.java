package com.example.javapp.dontquit.presenter;

import com.example.javapp.dontquit.contract.TariffContract;
import com.example.javapp.dontquit.domain.TariffCNLists;
import com.example.javapp.dontquit.domain.TariffRequestBody;
import com.example.javapp.dontquit.domain.TariffEUnUSALists;
import com.example.javapp.dontquit.model.TariffListModel;

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
    public void onFinishedEU(TariffEUnUSALists tariffEULists)
    {
        if(view != null){
            view.hideProgress();
            view.showResultEU(tariffEULists);
        }
    }

    @Override
    public void onFinishedUSA(TariffEUnUSALists tariffEUnUSAList)
    {
        if(view != null){
            view.hideProgress();
            view.showResultUSA(tariffEUnUSAList);
        }
    }

    @Override
    public void onFinishedCN(TariffCNLists tariffCNLists)
    {
        if(view != null){
            view.hideProgress();
            view.showResultCN(tariffCNLists);
        }
    }

    @Override
    public void onFailure(String message)
    {
        if(view != null){
            view.hideProgress();
            view.showToast(message);
            view.onResponseFailure(message);
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

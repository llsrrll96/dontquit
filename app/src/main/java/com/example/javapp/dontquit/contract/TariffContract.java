package com.example.javapp.dontquit.contract;

import com.example.javapp.dontquit.domain.TariffCN;
import com.example.javapp.dontquit.domain.TariffRequestBody;
import com.example.javapp.dontquit.domain.TariffUSA;

import java.util.List;

public interface TariffContract
{
    interface Model
    {
        interface OnFinishedListener
        {
//            void onFinished(List<TariffCN> tariffCNList);
            void onFinishedUSA(List<TariffUSA> tariffUSAList);
            void onFinishedCN(List<TariffCN> tariffCNList);

            void onFailure(String message);
        }
        void getTariffEUFromServer(OnFinishedListener onFinishedListener, TariffRequestBody tariffRequestBody);
        void getTariffUSAFromServer(OnFinishedListener onFinishedListener, TariffRequestBody tariffRequestBody);
        void getTariffCNFromServer(OnFinishedListener onFinishedListener, TariffRequestBody tariffRequestBody);
    }

    interface View
    {
//        void showResultUSA(List<TariffUSA> tariffUSAList);
        void showResultUSA(List<TariffUSA> tariffUSAList);
        void showResultCN(List<TariffCN> tariffCNList);
        void showProgress();
        void hideProgress();
        void showToast(String message);
        void onResponseFailure(String message);
    }

    interface Presenter
    {
        void onDestroy();
        void requestTariffFromServer(TariffRequestBody tariffRequestBody);
    }
}

package com.example.javapp.dontquit.contract;

import com.example.javapp.dontquit.domain.TariffCNLists;
import com.example.javapp.dontquit.domain.TariffRequestBody;
import com.example.javapp.dontquit.domain.TariffEUnUSALists;

public interface TariffContract
{
    interface Model
    {
        interface OnFinishedListener
        {
            void onFinishedEU(TariffEUnUSALists tariffEULists);
            void onFinishedUSA(TariffEUnUSALists tariffEUnUSALists);
            void onFinishedCN(TariffCNLists tariffCNLists);

            void onFailure(String message);
        }
        void getTariffEUFromServer(OnFinishedListener onFinishedListener, TariffRequestBody tariffRequestBody);
        void getTariffUSAFromServer(OnFinishedListener onFinishedListener, TariffRequestBody tariffRequestBody);
        void getTariffCNFromServer(OnFinishedListener onFinishedListener, TariffRequestBody tariffRequestBody);
    }

    interface View
    {
        void showResultEU(TariffEUnUSALists tariffEUList);
        void showResultUSA(TariffEUnUSALists tariffEUnUSALists);
        void showResultCN(TariffCNLists tariffCNLists);
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

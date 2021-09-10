package com.example.javapp.dontquit.contract;

import com.example.javapp.dontquit.domain.History;
import com.example.javapp.dontquit.domain.Product;
import com.example.javapp.dontquit.domain.ProductName;

import java.util.List;

public interface HistoryContract
{
    interface Model {
        interface onFinishedListener {
            void onFinished(List<History> historyList);

            void onFailure(String message);
        }

        void getHistoryList(onFinishedListener onFinishedListener, String hsk);
    }

    interface View
    {
        void showResult(List<History> historyList);
        void showProgress();
        void hideProgress();
        void showToast(String message);
        void onResponseFailure(Throwable throwable);
    }

    interface Presenter
    {
        void onDestroy();
        void requestHistoryFromServer(String hsk);
    }
}

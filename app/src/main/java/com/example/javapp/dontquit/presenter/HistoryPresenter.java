package com.example.javapp.dontquit.presenter;

import com.example.javapp.dontquit.contract.HistoryContract;
import com.example.javapp.dontquit.domain.History;
import com.example.javapp.dontquit.domain.Product;
import com.example.javapp.dontquit.model.HistoryListModel;

import java.util.List;

public class HistoryPresenter implements HistoryContract.Presenter, HistoryContract.Model.onFinishedListener
{
    HistoryContract.View view;
    HistoryContract.Model model;

    public HistoryPresenter(HistoryContract.View view) {
        this.view = view;
        this.model = new HistoryListModel();
    }


    @Override
    public void onFinished(List<History> historyList)
    {
        // view 존재 (소멸) 체크
        if(view != null){
            view.hideProgress();
            view.showResult(historyList);
        }
    }

    @Override
    public void onFailure(String message) {
        if(view != null){
            view.hideProgress();
            view.showToast(message);
        }
    }

    @Override
    public void requestHistoryFromServer(String hsk) {
        if(view != null){
            view.showProgress();
        }
        model.getHistoryList(this,hsk);
    }

    @Override
    public void onDestroy() {
        view=null;
    }

}

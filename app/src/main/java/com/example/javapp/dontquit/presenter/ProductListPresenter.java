package com.example.javapp.dontquit.presenter;

import android.widget.Toast;

import com.example.javapp.dontquit.contract.ProductListContract;
import com.example.javapp.dontquit.domain.Product;
import com.example.javapp.dontquit.domain.ProductName;
import com.example.javapp.dontquit.model.ProductListModel;

import java.util.List;

public class ProductListPresenter implements ProductListContract.Presenter, ProductListContract.Model.onFinishedListener
{
    private ProductListContract.View view;
    private ProductListContract.Model model;

    public ProductListPresenter(ProductListContract.View view) {
        this.view = view;
        this.model = new ProductListModel();
    }

    // Model
    @Override
    public void onFinished(List<Product> products)
    {
        // view 존재 (소멸) 체크
        if(view != null){
            view.hideProgress();
            view.showResult(products);
        }
    }
    //model
    @Override
    public void onFailure(String message) {
        if(view != null){
            view.hideProgress();
            view.showToast(message);
        }
    }

    //view
    @Override
    public void requestProductDataFromServer(ProductName productName)
    {
        if(view != null){
            view.showProgress();
        }
        model.getProductList(this,productName);
    }

    @Override
    public void onDestroy() {
        view=null;
    }
}

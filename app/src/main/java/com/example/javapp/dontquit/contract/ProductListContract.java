package com.example.javapp.dontquit.contract;

import com.example.javapp.dontquit.domain.Product;
import com.example.javapp.dontquit.domain.ProductName;

import java.util.List;

public interface ProductListContract
{
    interface Model
    {
        interface onFinishedListener
        {
            void onFinished(List<Product> products);

            void onFailure(String message);
        }

        void getProductList(onFinishedListener onFinishedListener, ProductName productName);
    }

    interface View
    {
        void showResult(List<Product> products);
        void showProgress();
        void hideProgress();
        void showToast(String message);
        void onResponseFailure(Throwable throwable);
    }

    interface Presenter
    {
        void onDestroy();
        void requestProductDataFromServer(ProductName productName);
    }
}

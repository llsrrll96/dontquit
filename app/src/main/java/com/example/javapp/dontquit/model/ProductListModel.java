package com.example.javapp.dontquit.model;

import android.util.Log;

import com.example.javapp.dontquit.contract.ProductListContract;
import com.example.javapp.dontquit.domain.Categories;
import com.example.javapp.dontquit.domain.Product;
import com.example.javapp.dontquit.domain.ProductName;
import com.example.javapp.dontquit.network.ApiClient;
import com.example.javapp.dontquit.network.Apiinterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListModel implements ProductListContract.Model
{

    List<Product> products  = new ArrayList<>();
    final Apiinterface service = ApiClient.getInstance().create(Apiinterface.class);

    @Override
    public void getProductList(onFinishedListener onFinishedListener, ProductName productName)
    {

        Call<List<Product>>call = service.getProduct(productName);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response)
            {
                if(!response.isSuccessful()){
                    onFinishedListener.onFailure(
                            response.message()
                    );
                    return;
                }
                // 통신 성공
                products =response.body();
                onFinishedListener.onFinished(products);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                // presenter 통신 실패 함수 호출

                onFinishedListener.onFailure(t.getMessage());
                Log.v("onFailure",t.getMessage());
            }

        });
    }
}

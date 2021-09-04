package com.example.javapp.dontquit.network;

import com.example.javapp.dontquit.domain.Categories;
import com.example.javapp.dontquit.domain.Hscodes;
import com.example.javapp.dontquit.domain.Product;
import com.example.javapp.dontquit.domain.ProductName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Apiinterface
{
    @POST("api/search")
    Call<List<Product>>getProduct(@Body ProductName productName);

    @POST("api/category")
    Call<Categories>getCategories(@Body Hscodes hscodes);

}


package com.example.javapp.dontquit.network;

import com.example.javapp.dontquit.domain.Categories;
import com.example.javapp.dontquit.domain.History;
import com.example.javapp.dontquit.domain.Hscodes;
import com.example.javapp.dontquit.domain.Product;
import com.example.javapp.dontquit.domain.ProductName;
import com.example.javapp.dontquit.domain.TariffCNLists;
import com.example.javapp.dontquit.domain.TariffRequestBody;
import com.example.javapp.dontquit.domain.TariffEUnUSALists;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Apiinterface
{
    @POST("api/search")
    Call<List<Product>>getProduct(@Body ProductName productName);

    @POST("api/category")
    Call<Categories>getCategories(@Body Hscodes hscodes);

    @GET("api/history/{hsk}")
    Call<List<History>>getHistoyList(@Path("hsk") String hsk);

    // EU tariff
    @POST("api/tariff")
    Call<TariffEUnUSALists>getTariffEUList(@Body TariffRequestBody tariffRequestBody);

    // USA tariff
    @POST("api/tariff")
    Call<TariffEUnUSALists>getTariffUSAList(@Body TariffRequestBody tariffRequestBody);

    // China tariff
    @POST("api/tariff")
    Call<TariffCNLists>getTariffCNList(@Body TariffRequestBody tariffRequestBody);

    // Words
    @POST("api/words")
    Call<String> getWordsHypernyms(@Body ProductName productName);

}


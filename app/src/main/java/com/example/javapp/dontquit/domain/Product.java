package com.example.javapp.dontquit.domain;

import com.google.gson.annotations.SerializedName;

public class Product
{
    @SerializedName("HS6")
    private String hs6;
    @SerializedName("HSCODE")
    private String hscode;


    public Product(String hs6, String hscode)
    {
        this.hs6 = hs6;
        this.hscode = hscode;

    }

    public String getHs6() {
        return hs6;
    }

    public String getHscode() {
        return hscode;
    }
}

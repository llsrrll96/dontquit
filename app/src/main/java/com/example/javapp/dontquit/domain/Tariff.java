package com.example.javapp.dontquit.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tariff
{
    @SerializedName("hscode")
    private List<String> hscode;

    @SerializedName("productname")
    private List<String> productname;

    public Tariff(List<String> hscode, List<String> productname) {
        this.hscode = hscode;
        this.productname = productname;
    }

    public List<String> getHscode() {
        return hscode;
    }

    public List<String> getProductname() {
        return productname;
    }
}

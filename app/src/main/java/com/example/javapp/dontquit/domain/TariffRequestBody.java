package com.example.javapp.dontquit.domain;

public class TariffRequestBody
{
    String hscode;
    String country;

    public TariffRequestBody(String hscode, String country)
    {
        this.hscode = hscode;
        this.country = country;
    }

    public String getHscode() {
        return hscode;
    }

    public String getCountry() {
        return country;
    }
}

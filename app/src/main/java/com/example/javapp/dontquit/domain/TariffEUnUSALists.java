package com.example.javapp.dontquit.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//hscode,품명,기본세율,FTA,hs6
public class TariffEUnUSALists extends Tariff
{

    @SerializedName("기본세율")
    private List<String> defaultRates;

    @SerializedName("FTA")
    private List<String> fta;

    public TariffEUnUSALists(List<String> hscode, List<String> productname) {
        super(hscode, productname);
    }

    public TariffEUnUSALists(List<String> hscode, List<String> productname, List<String> defaultRates, List<String> fta)
    {
        super(hscode, productname);
        this.defaultRates = defaultRates;
        this.fta = fta;
    }

    public List<String> getDefaultRates() {
        return defaultRates;
    }

    public List<String> getFta() {
        return fta;
    }
}

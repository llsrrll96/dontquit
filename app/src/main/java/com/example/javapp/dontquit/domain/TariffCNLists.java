package com.example.javapp.dontquit.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TariffCNLists extends Tariff
{
    @SerializedName("MFN")
    private List<String> mfn;
    @SerializedName("잠정세율")
    private List<String> tempTaxRates;
    @SerializedName("APTA")
    private List<String> apta;
    @SerializedName("FTA")
    private List<String> fta;


    public TariffCNLists(List<String> hscode, List<String> productname, List<String> mfn, List<String> tempTaxRates, List<String> apta, List<String> fta)
    {
        super(hscode, productname);
        this.mfn = mfn;
        this.tempTaxRates = tempTaxRates;
        this.apta = apta;
        this.fta = fta;
    }

    public List<String> getMfn() {
        return mfn;
    }

    public List<String> getTempTaxRates() {
        return tempTaxRates;
    }

    public List<String> getApta() {
        return apta;
    }

    public List<String> getFta() {
        return fta;
    }

}

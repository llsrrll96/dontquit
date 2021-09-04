package com.example.javapp.dontquit.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Categories
{
    @SerializedName("hs6")
    private List<String> hs6;
    @SerializedName("hscodes")
    private List<String> hscodes;
    @SerializedName("hs6content")
    private List<String> hs6content; //호
    @SerializedName("unit_names")
    private List<String> unitNames;  //분류명
    @SerializedName("divinity_names")
    private List<String> divinityNames;//신성질별

    public Categories(List<String> hs6, List<String> hscodes, List<String> hs6content, List<String> unitNames, List<String> divinityNames) {
        this.hs6 = hs6;
        this.hscodes = hscodes;
        this.hs6content = hs6content;
        this.unitNames = unitNames;
        this.divinityNames = divinityNames;
    }

    public List<String> getHs6() {
        return hs6;
    }

    public List<String> getHscodes() {
        return hscodes;
    }

    public List<String> getHs6content() {
        return hs6content;
    }

    public List<String> getUnitNames() {
        return unitNames;
    }

    public List<String> getDivinityNames() {
        return divinityNames;
    }
}

package com.example.javapp.dontquit.domain;

import java.io.Serializable;

public class Category implements Serializable
{
    private String hscode;
    private String hs6content;
    private String unitName;
    private String divinityName;

    public Category(String hscode, String hs6content, String unitName, String divinityName) {
        this.hscode = hscode;
        this.hs6content = hs6content;
        this.unitName = unitName;
        this.divinityName = divinityName;
    }

    public String getHscode() {
        return hscode;
    }

    public String getHs6content() {
        return hs6content;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getDivinityName() {
        return divinityName;
    }
}

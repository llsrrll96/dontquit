package com.example.javapp.dontquit.other;

public class SpinnerItemRow
{
    private String countryCode;
    private int icon;

    public SpinnerItemRow(String countryCode, int icon)
    {
        this.countryCode = countryCode;
        this.icon = icon;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public int getIcon() {
        return icon;
    }
}

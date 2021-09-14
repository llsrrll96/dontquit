package com.example.javapp.dontquit.other;

import com.example.javapp.dontquit.R;

public enum SpinnerCountry
{
    EU("EU", R.drawable.ic_eu),
    USA("United States of America", R.drawable.ic_usa),
    CN("China", R.drawable.ic_china);

    final private String countryName;
    final private int icon;

    SpinnerCountry(String countryName, int icon)
    {
        this.countryName = countryName;
        this.icon = icon;
    }

    public String getCountryName() {
        return countryName;
    }

    public int getIcon() {
        return icon;
    }
}

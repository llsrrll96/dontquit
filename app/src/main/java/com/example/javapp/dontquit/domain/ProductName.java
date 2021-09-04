package com.example.javapp.dontquit.domain;

import java.io.Serializable;

public class ProductName implements Serializable
{
    public String productName;

    public ProductName(String productName) {
        this.productName = productName;
    }
}

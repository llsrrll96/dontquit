package com.example.javapp.dontquit.domain;

import java.util.ArrayList;
import java.util.List;

public class Hscodes
{
    private List<String>hscodes;

    public Hscodes() {
        this.hscodes = new ArrayList<>();
    }

    public Hscodes(List<String> hscodes) {
        this.hscodes = hscodes;
    }

    public List<String> getHscodes() {
        return hscodes;
    }

    public void addHscodes(String hscode){
        this.hscodes.add(hscode);
    }
    public void setHscodes(List<String> hscodes) {
        this.hscodes = hscodes;
    }
}

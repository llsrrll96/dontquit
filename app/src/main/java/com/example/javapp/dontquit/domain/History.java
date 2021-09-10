package com.example.javapp.dontquit.domain;

import com.google.gson.annotations.SerializedName;

public class History
{
    @SerializedName("HSCODE")
    private String hscode;
    @SerializedName("NM")
    private String nm;
    @SerializedName("이유")
    private String reason;
    @SerializedName("설명")
    private String content;

    public History(String hscode, String nm, String reason, String content) {
        this.hscode = hscode;
        this.nm = nm;
        this.reason = reason;
        this.content = content;
    }

    public String getHscode() {
        return hscode;
    }

    public String getNm() {
        return nm;
    }

    public String getReason() {
        return reason;
    }

    public String getContent() {
        return content;
    }
}

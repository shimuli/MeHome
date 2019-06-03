package com.example.mehome;

public class TheData {
    private String mName, mLoaction, mPrice, mDesc;
    private String mImgUrl;

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmLoaction() {
        return mLoaction;
    }

    public void setmLoaction(String mLoaction) {
        this.mLoaction = mLoaction;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public String getmImgUrl() {
        return mImgUrl;
    }

    public void setmImgUrl(String mImgUrl) {
        this.mImgUrl = mImgUrl;
    }

    public TheData(String mName,String mImgUrl,String mDesc, String mLoaction, String mPrice ) {
        this.mName = mName;
        this.mLoaction = mLoaction;
        this.mPrice = mPrice;
        this.mDesc = mDesc;
        this.mImgUrl = mImgUrl;
    }

    public TheData() {
    }



}
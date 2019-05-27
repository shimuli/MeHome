package com.example.mehome.Models.AddingProperty.CommercialSale;

import com.google.firebase.database.Exclude;

public class CommercialData {
    public CommercialData() {
    }


    private String Title_of_Commercial, Commercial_Location, CommercialDesc, CommercialPrice;
    private String imageURL;
    private String mKey;

    public CommercialData(String title_of_Commercial, String commercial_Location, String commercialDesc, String commercialPrice, String imageURL) {
        Title_of_Commercial = title_of_Commercial;
        Commercial_Location = commercial_Location;
        CommercialDesc = commercialDesc;
        CommercialPrice = commercialPrice;
        this.imageURL = imageURL;
    }


    public String getTitle_of_Commercial() {
        return Title_of_Commercial;
    }

    public void setTitle_of_Commercial(String title_of_Commercial) {
        Title_of_Commercial = title_of_Commercial;
    }

    public String getCommercial_Location() {
        return Commercial_Location;
    }

    public void setCommercial_Location(String commercial_Location) {
        Commercial_Location = commercial_Location;
    }

    public String getCommercialDesc() {
        return CommercialDesc;
    }

    public void setCommercialDesc(String commercialDesc) {
        CommercialDesc = commercialDesc;
    }

    public String getCommercialPrice() {
        return CommercialPrice;
    }

    public void setCommercialPrice(String commercialPrice) {
        CommercialPrice = commercialPrice;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String key) {
        mKey = key;
    }


}

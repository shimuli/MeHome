package com.example.mehome.Models.AddingProperty.CommercialSale;

import com.google.firebase.database.Exclude;

public class CommercialData {
    private String Title_of_Holiday_House;

    public CommercialData() {
    }

    private String Holdiay_House_Location;
    private String Holiday_HouseDesc;
    private String Holdiay_HousePrice;
    private String imageURL;
    private String OwnerNumber;
    private String Key;

    public String getOwnerNumber() {
        return OwnerNumber;
    }

    public void setOwnerNumber(String ownerNumber) {
        OwnerNumber = ownerNumber;
    }

    public String getTitle_of_Holiday_House() {
        return Title_of_Holiday_House;
    }

    public void setTitle_of_Holiday_House(String title_of_Holiday_House) {
        Title_of_Holiday_House = title_of_Holiday_House;
    }

    public String getHoldiay_House_Location() {
        return Holdiay_House_Location;
    }

    public void setHoldiay_House_Location(String holdiay_House_Location) {
        Holdiay_House_Location = holdiay_House_Location;
    }

    public String getHoliday_HouseDesc() {
        return Holiday_HouseDesc;
    }

    public void setHoliday_HouseDesc(String holiday_HouseDesc) {
        Holiday_HouseDesc = holiday_HouseDesc;
    }

    public String getHoldiay_HousePrice() {
        return Holdiay_HousePrice;
    }

    public void setHoldiay_HousePrice(String holdiay_HousePrice) {
        Holdiay_HousePrice = holdiay_HousePrice;
    }


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getKey() {
        return Key;
    }

    public void sethKey(String hKey) {
        this.Key = hKey;
    }

    public CommercialData(String title_of_Holiday_House,String holdiay_HousePrice,String holdiay_House_Location,String holiday_HouseDesc,String townerNumber, String imageURL) {
        Title_of_Holiday_House = title_of_Holiday_House;
        Holdiay_House_Location = holdiay_House_Location;
        Holiday_HouseDesc = holiday_HouseDesc;
        Holdiay_HousePrice = holdiay_HousePrice;
        OwnerNumber = townerNumber;
        this.imageURL = imageURL;

    }



}

package com.example.mehome.Models.AddingProperty.SaleHouses;

public class SaleData {
    private String Title_of_HouseSale, House_LocationSale, HouseDescSale, HousePriceSale;
    private String HouseTypeSale, Bedroom_NoSale;
    private String imageURL;
    private String sKey;

    public String getTitle_of_HouseSale() {
        return Title_of_HouseSale;
    }

    public void setTitle_of_HouseSale(String title_of_HouseSale) {
        Title_of_HouseSale = title_of_HouseSale;
    }

    public String getHouse_LocationSale() {
        return House_LocationSale;
    }

    public void setHouse_LocationSale(String house_LocationSale) {
        House_LocationSale = house_LocationSale;
    }

    public String getHouseDescSale() {
        return HouseDescSale;
    }

    public void setHouseDescSale(String houseDescSale) {
        HouseDescSale = houseDescSale;
    }

    public String getHousePriceSale() {
        return HousePriceSale;
    }

    public void setHousePriceSale(String housePriceSale) {
        HousePriceSale = housePriceSale;
    }

    public String getHouseTypeSale() {
        return HouseTypeSale;
    }

    public void setHouseTypeSale(String houseTypeSale) {
        HouseTypeSale = houseTypeSale;
    }

    public String getBedroom_NoSale() {
        return Bedroom_NoSale;
    }

    public void setBedroom_NoSale(String bedroom_NoSale) {
        Bedroom_NoSale = bedroom_NoSale;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getsKey() {
        return sKey;
    }

    public void setsKey(String sKey) {
        this.sKey = sKey;
    }

    public SaleData(String title_of_HouseSale, String house_LocationSale, String houseDescSale, String housePriceSale, String houseTypeSale, String bedroom_NoSale, String imageURL) {
        this.Title_of_HouseSale = title_of_HouseSale;
        this.House_LocationSale = house_LocationSale;
        this.HouseDescSale = houseDescSale;
        this.HousePriceSale = housePriceSale;
        this.HouseTypeSale = houseTypeSale;
        this.Bedroom_NoSale = bedroom_NoSale;
        this.imageURL = imageURL;
    }

    public SaleData() {
    }



}

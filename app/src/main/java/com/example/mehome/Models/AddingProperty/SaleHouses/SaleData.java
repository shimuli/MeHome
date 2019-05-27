package com.example.mehome.Models.AddingProperty.SaleHouses;

public class SaleData {
    public String Title_of_HouseSale, House_LocationSale, HouseDescSale, HousePriceSale;
    public String HouseTypeSale, Bedroom_NoSale;
    public String imageURL;

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

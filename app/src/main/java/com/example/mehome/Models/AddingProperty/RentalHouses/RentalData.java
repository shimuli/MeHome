package com.example.mehome.Models.AddingProperty.RentalHouses;

public class RentalData {

    public String Title_of_House, House_Location, HouseDesc, HousePrice;
    public String HouseType, Bedroom_No;
    public String imageURL;

    public RentalData(){

    }


    public RentalData(String t_house, String l_house, String d_house,String typ_house, String b_room, String url, String h_price){
        this.Title_of_House =t_house;
        this.House_Location = l_house;
        this.HouseDesc = d_house;
        this.HouseType = typ_house;
        this.Bedroom_No = b_room;
        this.imageURL = url;
        this.HousePrice =h_price;

    }


}




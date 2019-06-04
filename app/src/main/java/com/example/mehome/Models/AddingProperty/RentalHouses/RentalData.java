package com.example.mehome.Models.AddingProperty.RentalHouses;

public class RentalData {

    private String Title_of_House, House_Location, HouseDesc, HousePrice, OwnerNumber;
    private String HouseType, Bedroom_No;
    private String imageURL;
    private String rKEy;

    public String getOwnerNumber() {
        return OwnerNumber;
    }

    public void setOwnerNumber(String ownerNumber) {
        OwnerNumber = ownerNumber;
    }

    public String getTitle_of_House() {
        return Title_of_House;
    }

    public void setTitle_of_House(String title_of_House) {
        Title_of_House = title_of_House;
    }

    public String getHouse_Location() {
        return House_Location;
    }

    public void setHouse_Location(String house_Location) {
        House_Location = house_Location;
    }

    public String getHouseDesc() {
        return HouseDesc;
    }

    public void setHouseDesc(String houseDesc) {
        HouseDesc = houseDesc;
    }

    public String getHousePrice() {
        return HousePrice;
    }

    public void setHousePrice(String housePrice) {
        HousePrice = housePrice;
    }

    public String getHouseType() {
        return HouseType;
    }

    public void setHouseType(String houseType) {
        HouseType = houseType;
    }

    public String getBedroom_No() {
        return Bedroom_No;
    }

    public void setBedroom_No(String bedroom_No) {
        Bedroom_No = bedroom_No;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getrKEy() {
        return rKEy;
    }

    public void setrKEy(String rKEy) {
        this.rKEy = rKEy;
    }

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




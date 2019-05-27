package com.example.mehome.Models.AddingProperty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mehome.Models.AddingProperty.CommercialSale.AddCommercial;
import com.example.mehome.Models.AddingProperty.HolidayHouses.HolidayRental;
import com.example.mehome.Models.AddingProperty.RentalHouses.HouseRental;
import com.example.mehome.Models.AddingProperty.SaleHouses.SaleHouse;
import com.example.mehome.R;

public class AddProperty extends AppCompatActivity {

    private ImageView addRentals, addHomesale, commercialHouse, holidayHouse;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_addproperty);


        addRentals=findViewById(R.id.rentals);
        addHomesale=findViewById(R.id.houseSale);
        commercialHouse =findViewById(R.id.commercialSale);
        holidayHouse=findViewById(R.id.holidayRental);

        addRentals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddProperty.this, HouseRental.class));
            }
        });

        addHomesale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddProperty.this, SaleHouse.class));
            }
        });

        commercialHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddProperty.this, AddCommercial.class));
            }
        });

        holidayHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddProperty.this, HolidayRental.class));
            }
        });





    }
}

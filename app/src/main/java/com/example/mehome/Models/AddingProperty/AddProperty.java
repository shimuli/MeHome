package com.example.mehome.Models.AddingProperty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mehome.Models.AddingProperty.CommercialSale.AddCommercial;
import com.example.mehome.Models.AddingProperty.HolidayHouses.HolidayRental;
import com.example.mehome.Models.AddingProperty.RentalHouses.HouseRental;
import com.example.mehome.Models.AddingProperty.SaleHouses.SaleHouse;
import com.example.mehome.Models.Auth.LoginActivity;
import com.example.mehome.Models.Auth.ProfileActivity;
import com.example.mehome.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AddProperty extends AppCompatActivity {

    private ImageView addRentals, addHomesale, commercialHouse, holidayHouse;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_addproperty);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(AddProperty.this, LoginActivity.class));
                    finish();
                }
            }
        };




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

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}

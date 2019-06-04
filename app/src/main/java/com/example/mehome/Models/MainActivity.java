package com.example.mehome.Models;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mehome.Fragments.AboutFragment;
import com.example.mehome.Fragments.FaqFragment;
import com.example.mehome.Fragments.HomeFragment;
import com.example.mehome.Fragments.TermsFragment;
import com.example.mehome.Models.AddingProperty.AddProperty;
import com.example.mehome.Models.Auth.LoginActivity;
import com.example.mehome.Models.Auth.ProfileActivity;
import com.example.mehome.Models.Display.Commercials.CommercialDisplay;
import com.example.mehome.Models.Display.Holiday.HolidayDisplay;
import com.example.mehome.Models.Display.MyProperty;
import com.example.mehome.Models.Display.Rentals.HomeDisplay;
import com.example.mehome.Models.Display.Sale.SaleDisplay;
import com.example.mehome.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView RentalHome, CommercialHome, SaleHome, HolidayHome;
    private TextView RentalText, CommercialText, SaleText, HolidayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RentalHome =findViewById(R.id.rentalsHome);
        CommercialHome = findViewById(R.id.commercialSaleHome);
        SaleHome =findViewById(R.id.houseSaleHome);
        HolidayHome =findViewById(R.id.holidayRentalHome);
        RentalText =findViewById(R.id.rentalText);
        CommercialText =findViewById(R.id.commercialText);
        SaleText = findViewById(R.id.saleText);
        HolidayText =findViewById(R.id.holidayText);


        RentalHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HomeDisplay.class));
            }
        });

        RentalText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HomeDisplay.class));
            }
        });

        CommercialHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CommercialDisplay.class));
            }
        });


        CommercialText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CommercialDisplay.class));
            }
        });

        SaleHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SaleDisplay.class));
            }
        });

        SaleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SaleDisplay.class));
            }
        });


        HolidayHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HolidayDisplay.class));
            }
        });


        HolidayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HolidayDisplay.class));
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_views);
        navigationView.setNavigationItemSelectedListener(this);



        View headView = navigationView.getHeaderView(0);
        ImageView ProfileImage = headView.findViewById(R.id.logoImage);
        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(profileIntent);

            }


        });

        View loginView = navigationView.getHeaderView(0);
        TextView LoginText =  headView.findViewById(R.id.profilename);
        LoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")

    private void displaySelectedFragment(int itemId){
        Fragment fragment = null;

        switch (itemId){


            case R.id.nav_commercial:
                //fragment =new CommercialFragment();
                Intent intentComm = new Intent(MainActivity.this, CommercialDisplay.class);
                startActivity(intentComm);
                break;

            case R.id.nav_my_property:
                Intent intentMyProperty = new Intent(MainActivity.this, AddProperty.class);
                startActivity(intentMyProperty);
                break;

            case  R.id.nav_property_add:
                Intent intentAdd= new Intent(MainActivity.this, AddProperty.class);
                startActivity(intentAdd);
                break;

            case R.id.nav_about:
                fragment = new AboutFragment();
                break;

            case R.id.nav_faq:
                fragment =new FaqFragment();
                break;

            case R.id.nav_terms:
                fragment = new TermsFragment();
                break;


            default:
                fragment = new HomeFragment();

        }

        if (fragment !=null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.relativeLayout, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        displaySelectedFragment(item.getItemId());


        return true;
    }


}

package com.example.mehome.Models.Display.Rentals;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mehome.Models.AddingProperty.RentalHouses.RentalData;
import com.example.mehome.R;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.example.mehome.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;



public class HomeDisplay extends AppCompatActivity  implements HomeAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private HomeAdapter homeAdapter;

    private ProgressBar mProgressCircle;

    private DatabaseReference mDatabaseRef;
    private StorageReference mStorage;
    private List<RentalData> mUploads;
    private ValueEventListener mDBListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_display);

        mRecyclerView = findViewById(R.id.recycler_viewHome);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circleHome);

        mUploads = new ArrayList<>();

        homeAdapter = new HomeAdapter(HomeDisplay.this, mUploads);

        mRecyclerView.setAdapter(homeAdapter);

        homeAdapter.setOnItemClickListener(HomeDisplay.this);


        mStorage = FirebaseStorage.getInstance().getReference();
        //StorageReference pathReference = mStorage.child("Commercial/");

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Rental_houses/");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    RentalData upload = postSnapshot.getValue(RentalData.class);
                    mUploads.add(upload);
                }

                homeAdapter = new HomeAdapter(HomeDisplay.this, mUploads);

                mRecyclerView.setAdapter(homeAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeDisplay.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }
    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "Normal click at position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWhatEverClick(int position) {
        Toast.makeText(this, "Whatever click at position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int position) {
        RentalData selectedItem = mUploads.get(position);
        final String selectedKey = selectedItem.getrKEy();

        StorageReference imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(selectedItem.getImageURL());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(HomeDisplay.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }


}


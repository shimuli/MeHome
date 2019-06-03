package com.example.mehome.Models.Display.Sale;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mehome.Models.AddingProperty.SaleHouses.SaleData;
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


import com.example.mehome.R;

public class SaleDisplay extends AppCompatActivity  implements SaleAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private SaleAdapter homeAdapter;

    private ProgressBar mProgressCircle;

    private DatabaseReference mDatabaseRef;
    private StorageReference mStorage;
    private List<SaleData> mUploads;
    private ValueEventListener mDBListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_display);

        mRecyclerView = findViewById(R.id.recycler_viewSale);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circleSale);

        mUploads = new ArrayList<>();

        homeAdapter = new SaleAdapter(SaleDisplay.this, mUploads);

        mRecyclerView.setAdapter(homeAdapter);

        homeAdapter.setOnItemClickListener(SaleDisplay.this);


        mStorage = FirebaseStorage.getInstance().getReference();
        //StorageReference pathReference = mStorage.child("Commercial/");

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Sale_houses/ ");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    SaleData upload = postSnapshot.getValue(SaleData.class);
                    mUploads.add(upload);
                }

                homeAdapter = new SaleAdapter(SaleDisplay.this, mUploads);

                mRecyclerView.setAdapter(homeAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SaleDisplay.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
        SaleData selectedItem = mUploads.get(position);
        final String selectedKey = selectedItem.getsKey();

        StorageReference imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(selectedItem.getImageURL());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(SaleDisplay.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

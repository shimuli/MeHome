package com.example.mehome.Models.Display.Holiday;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.example.mehome.Models.AddingProperty.CommercialSale.CommercialData;
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

public class HolidayDisplay extends AppCompatActivity  implements CommercialViewHolder.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private CommercialViewHolder commercialViewHolder;

    private ProgressBar mProgressCircle;

    private DatabaseReference mDatabaseRef;
    private StorageReference mStorage;
    private List<CommercialData> mUploads;
    private ValueEventListener mDBListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercial_display);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();

        commercialViewHolder = new CommercialViewHolder(CommercialDisplay.this, mUploads);

        mRecyclerView.setAdapter(commercialViewHolder);

        commercialViewHolder.setOnItemClickListener(CommercialDisplay.this);


        //mStorage = FirebaseStorage.getInstance().getReference();
        //StorageReference pathReference = mStorage.child("Commercial/");

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Commercial/");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    CommercialData upload = postSnapshot.getValue(CommercialData.class);
                    mUploads.add(upload);
                }

                commercialViewHolder = new CommercialViewHolder(CommercialDisplay.this, mUploads);

                mRecyclerView.setAdapter(commercialViewHolder);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CommercialDisplay.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
        CommercialData selectedItem = mUploads.get(position);
        final String selectedKey = selectedItem.getKey();

        StorageReference imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(selectedItem.getImageURL());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(CommercialDisplay.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }


}


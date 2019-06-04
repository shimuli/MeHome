package com.example.mehome.Models.AddingProperty.CommercialSale;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.mehome.Models.AddingProperty.HolidayHouses.HolidayRental;
import com.example.mehome.Models.AddingProperty.RentalHouses.RentalData;
import com.example.mehome.R;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mehome.R;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class AddCommercial extends AppCompatActivity {
    private String Storage_Path = "Commercial/";
    private String Database_path = "Commercial/";
    private Button SelectImage, UploadData;
    EditText Title_of_Commercial, Commercial_Location, CommercialDesc, CommercialPrice;
    private ImageView SelectedImageCom;
    private Uri CommercialPathUri;


    // Creating StorageReference and DatabaseReference object.
    StorageReference storageReference;

    DatabaseReference databaseReference;

    int Image_Request_code = 1;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        GridLayout mainGrid;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_commercial);

        storageReference = FirebaseStorage.getInstance().getReference();

        // Assign FirebaseDatabase instance with root database name.
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_path);

        SelectImage = findViewById(R.id.ButtonChooseCommercial);
        UploadData = findViewById(R.id.ButtonUploadImageCommercial);

        SelectedImageCom =findViewById(R.id.ShowImageCommercial);

        Title_of_Commercial= findViewById(R.id.commercialTitle);
        Commercial_Location =findViewById(R.id.commercialLocation);
        CommercialDesc =findViewById(R.id.commercialDescription);
        CommercialPrice =findViewById(R.id.commercialPrice);

        progressDialog =new ProgressDialog(AddCommercial.this);


        SelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectImageIntent = new Intent();

                // Setting intent type as image to select image from phone storage.
                selectImageIntent.setType("image/*");
                selectImageIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(selectImageIntent, "Please Select" +
                        "Image"),Image_Request_code);

            }
        });

        SelectedImageCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectImageIntent = new Intent();

                // Setting intent type as image to select image from phone storage.
                selectImageIntent.setType("image/*");
                selectImageIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(selectImageIntent, "Please Select" +
                        "Image"),Image_Request_code);

            }
        });

        UploadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Calling method to upload selected data on Firebase storage.
                UploadImageFileToFirebaseStorage();
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode,resultCode, data );

        if (requestCode == Image_Request_code && resultCode==RESULT_OK && data != null && data.getData()!=null){
            CommercialPathUri = data.getData();

            try{

                // Getting selected image into Bitmap.

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), CommercialPathUri);

                // Setting up bitmap selected image into ImageView.
                SelectedImageCom.setImageBitmap(bitmap);

                // After selecting image change choose button above text.

                SelectImage.setText("Image Selected");

            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri){
        ContentResolver contentResolver =getContentResolver();
        MimeTypeMap mimeTypeMap =MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    // Creating UploadImageFileToFirebaseStorage method to upload image on storage.
    public void UploadImageFileToFirebaseStorage(){
        if (CommercialPathUri!=null){

            progressDialog.setTitle("Uploading Data...");

            progressDialog.show();

            //second StorageReference.

            StorageReference storageReference1 = storageReference.child(Storage_Path+System.currentTimeMillis()+"."+GetFileExtension(CommercialPathUri));

            storageReference1.putFile(CommercialPathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String uri = taskSnapshot.toString();
                            Log.d("TAG", uri);
                            String CommImageName =  Title_of_Commercial.getText().toString().trim();
                            String CommArea = Commercial_Location.getText().toString().trim();
                            String CommCost= CommercialPrice.getText().toString().trim();
                            String Comm_Desc = CommercialDesc.getText().toString().trim();


                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(), "Data Uploaded Successfully", Toast.LENGTH_SHORT).show();

                            @SuppressWarnings("VisibleForTest")
                            CommercialData commercialUploadInfo =new CommercialData(CommImageName,CommArea, Comm_Desc,CommCost, taskSnapshot.getStorage().getDownloadUrl().toString());// getResult().getStorage().getDownloadUrl());


                            // Getting image upload ID.
                            String ImageUploadId = databaseReference.push().getKey();

                            //adding an upload to firebase database
                            // String uploadId = mDatabase.push().getKey();
                            // mDatabase.child(uploadId).setValue(upload);

                            // Adding image upload id s child element into databaseReference.
                            databaseReference.child(ImageUploadId).setValue(commercialUploadInfo);


                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();

                            Toast.makeText(AddCommercial.this, e.getMessage(), Toast.LENGTH_SHORT).show();


                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.setTitle("Data is Uploading...");
                        }
                    });


        }else {
            Toast.makeText(AddCommercial.this, "Please Select Image", Toast.LENGTH_SHORT).show();
        }
    }

}

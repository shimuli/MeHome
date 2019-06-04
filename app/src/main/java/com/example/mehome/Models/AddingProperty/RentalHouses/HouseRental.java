package com.example.mehome.Models.AddingProperty.RentalHouses;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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

import com.example.mehome.Models.AddingProperty.AddProperty;
import com.example.mehome.Models.AddingProperty.HolidayHouses.HolidayData;
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
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class HouseRental extends AppCompatActivity {

    private Button selectImage,Uploaddata;
    private ImageView RentalImage;
    private String Storage_Path = "Rentals/";
    private String Database_path = "Rentals/";

    private EditText rName, rPrice, rDesc, rLocation, rbedroom, rnumber;
    private static final int PICK_IMAGE_REQUEST=1;
    private DatabaseReference rdataref;
    private StorageReference rstorageref;
    final int IMAGE_REQUEST_CODE = 999;
    private ProgressDialog hprogressDialog;
    private Uri rimguri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_rental);


        selectImage=(Button)findViewById(R.id.ButtonChooseImageHoliday);

        RentalImage=(ImageView)findViewById(R.id.ShowImageViewHoliday);
        Uploaddata=(Button)findViewById(R.id.ButtonUploadImageHoliday);
        rName=(EditText)findViewById(R.id.houseTitleHoliday);
        rDesc =findViewById(R.id.DescriptionHoliday);
        rLocation =findViewById(R.id.locationHoliday);
        rPrice = findViewById(R.id.housePriceHoliday);
        rbedroom =findViewById(R.id.bedroomSpinnerHoliday);
        rnumber =findViewById(R.id.numberHoliday);


        hprogressDialog=new ProgressDialog(HouseRental.this);

        rstorageref= FirebaseStorage.getInstance().getReference(Storage_Path);
        rdataref= FirebaseDatabase.getInstance().getReference(Database_path);
        Uploaddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectImageIntent = new Intent();

                // Setting intent type as image to select image from phone storage.
                selectImageIntent.setType("image/*");
                selectImageIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(selectImageIntent, "Please Select" +
                        "Image"),IMAGE_REQUEST_CODE);


                ActivityCompat.requestPermissions(HouseRental.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},IMAGE_REQUEST_CODE);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==IMAGE_REQUEST_CODE){
            if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent intent=new Intent(new Intent(Intent.ACTION_PICK));
                intent.setType("image/*");

                startActivityForResult(Intent.createChooser(intent,"select image"),IMAGE_REQUEST_CODE);

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            rimguri = data.getData();
            Picasso.with(this).load(rimguri).into(RentalImage);
        }
    }
    private String getFileExtensoin (Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private void uploadImage() {
        if (rimguri!=null){
            StorageReference storageReference=rstorageref.child(System.currentTimeMillis()+"."+ getFileExtensoin(rimguri));
            storageReference.putFile(rimguri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler=new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    hprogressDialog.setProgress(0);
                                }
                            },5000);
                            Toast.makeText(HouseRental.this,"Upload SuccessFul",Toast.LENGTH_SHORT).show();
                            HolidayData list_data=new HolidayData(rName.getText().toString().trim(),
                                    rPrice.getText().toString().trim(),rLocation.getText().toString().trim(),rbedroom.getText().toString().trim(),
                                    rDesc.getText().toString().trim(), rnumber.getText().toString().trim(),taskSnapshot.getDownloadUrl().toString());

                            //(String title_of_Holiday_House,String holdiay_HousePrice,String holdiay_House_Location,
                            //  String holiday_Bedroom_No,String holiday_HouseDesc, String imageURL, String hKey)
                            String uploadid=rdataref.push().getKey();
                            rdataref.child(uploadid).setValue(list_data);
                            startActivity(new Intent(HouseRental.this, AddProperty.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double pr=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    hprogressDialog.setProgress((int) pr);
                }
            });
        }else {
            Toast.makeText(HouseRental.this,"File Not Selected",Toast.LENGTH_SHORT).show();
        }

    }
}


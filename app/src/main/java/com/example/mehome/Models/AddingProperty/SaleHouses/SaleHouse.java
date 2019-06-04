package com.example.mehome.Models.AddingProperty.SaleHouses;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
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
import com.example.mehome.Models.AddingProperty.RentalHouses.HouseRental;
import com.example.mehome.R;
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

public class SaleHouse extends AppCompatActivity {

    private Button selectImage,Uploaddata;
    private ImageView SaleImage;
    private String Storage_Path = "Sale/";
    private String Database_path = "Sale/";

    private EditText sName, sPrice, sDesc, sLocation, sbedroom, snumber;
    private static final int PICK_IMAGE_REQUEST=1;
    private DatabaseReference sdataref;
    private StorageReference sstorageref;
    final int IMAGE_REQUEST_CODE = 999;
    private ProgressDialog sprogressDialog;
    private Uri simguri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_house);


        selectImage=(Button)findViewById(R.id.ButtonChooseImageHoliday);

        SaleImage=(ImageView)findViewById(R.id.ShowImageViewHoliday);
        Uploaddata=(Button)findViewById(R.id.ButtonUploadImageHoliday);
        sName=(EditText)findViewById(R.id.houseTitleHoliday);
        sDesc =findViewById(R.id.DescriptionHoliday);
        sLocation =findViewById(R.id.locationHoliday);
        sPrice = findViewById(R.id.housePriceHoliday);
        sbedroom =findViewById(R.id.bedroomSpinnerHoliday);
        snumber =findViewById(R.id.numberHoliday);


        sprogressDialog=new ProgressDialog(SaleHouse.this);

        sstorageref= FirebaseStorage.getInstance().getReference(Storage_Path);
        sdataref= FirebaseDatabase.getInstance().getReference(Database_path);
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


                ActivityCompat.requestPermissions(SaleHouse.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},IMAGE_REQUEST_CODE);
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
            simguri = data.getData();
            Picasso.with(this).load(simguri).into(SaleImage);
        }
    }
    private String getFileExtensoin (Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private void uploadImage() {
        if (simguri!=null){
            StorageReference storageReference=sstorageref.child(System.currentTimeMillis()+"."+ getFileExtensoin(simguri));
            storageReference.putFile(simguri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler=new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    sprogressDialog.setProgress(0);
                                }
                            },5000);
                            Toast.makeText(SaleHouse.this,"Upload SuccessFul",Toast.LENGTH_SHORT).show();
                            HolidayData list_data=new HolidayData(sName.getText().toString().trim(),
                                    sPrice.getText().toString().trim(),sLocation.getText().toString().trim(),sbedroom.getText().toString().trim(),
                                    sDesc.getText().toString().trim(), snumber.getText().toString().trim(),taskSnapshot.getDownloadUrl().toString());

                            //(String title_of_Holiday_House,String holdiay_HousePrice,String holdiay_House_Location,
                            //  String holiday_Bedroom_No,String holiday_HouseDesc, String imageURL, String hKey)
                            String uploadid=sdataref.push().getKey();
                            sdataref.child(uploadid).setValue(list_data);
                            startActivity(new Intent(SaleHouse.this, AddProperty.class));
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
                    sprogressDialog.setProgress((int) pr);
                }
            });
        }else {
            Toast.makeText(SaleHouse.this,"File Not Selected",Toast.LENGTH_SHORT).show();
        }

    }
}

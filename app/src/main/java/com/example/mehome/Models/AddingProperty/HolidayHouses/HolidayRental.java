package com.example.mehome.Models.AddingProperty.HolidayHouses;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mehome.Models.AddingProperty.AddProperty;
import com.example.mehome.R;
import com.example.mehome.UploadImage;
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
import java.text.NumberFormat;

public class HolidayRental extends AppCompatActivity {

    private Button selectImage,Uploaddata;
    private ImageView HolidayImage;
    private String Storage_Path = "holiday/";
    private String Database_path = "holiday/";

    private EditText hName, hPrice, hDesc, hbedroom, hnumber;
    private static final int PICK_IMAGE_REQUEST=1;
    private DatabaseReference hdataref;
    private StorageReference hstorageref;
    Spinner HouseType, Bedroom_No, hLocation;
    final int IMAGE_REQUEST_CODE = 999;
    private ProgressDialog hprogressDialog;
    private Uri himguri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday_rental);


        setContentView(R.layout.activity_holiday_rental);

        selectImage=(Button)findViewById(R.id.ButtonChooseImageHoliday);
        HolidayImage=(ImageView)findViewById(R.id.ShowImageViewHoliday);
        Uploaddata=(Button)findViewById(R.id.ButtonUploadImageHoliday);
        hName=(EditText)findViewById(R.id.houseTitleHoliday);
        hDesc =findViewById(R.id.DescriptionHoliday);
        hLocation =findViewById(R.id.HolidaylLoc);
        hPrice = findViewById(R.id.housePriceHoliday);
        Bedroom_No = findViewById(R.id.bedroomSpinnerHoliday);
        HouseType =findViewById(R.id.HolidayType);


        Bedroom_No =findViewById(R.id.bedroomSpinnerHoliday);
        hnumber =findViewById(R.id.numberHoliday);

        hPrice.addTextChangedListener(new CurrencyTextWatcher());

        hName.addTextChangedListener(textWatcher);
        hPrice.addTextChangedListener(textWatcher);
        hDesc.addTextChangedListener(textWatcher);
        hnumber.addTextChangedListener(textWatcher);

        Spinner shLocation = findViewById(R.id.HolidaylLoc);

        String[] items = new String[]{"Kakamega Town", "Amalemba", "Lurambi","Kefinco", "Matende"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        shLocation.setAdapter(adapter);

        Spinner shType = findViewById(R.id.HolidayType);
        String[] items1 = new String[]{"Single Rooms", "Bungalow", "Mansion", "Bed Sitter", "Hostel", "Appartment"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        shType.setAdapter(adapter1);

        Spinner shBedrooms = findViewById(R.id.bedroomSpinnerHoliday);
        String[] items3 = new String[]{"1", "2", "3", "4", "5", "6", "7","8","9","None"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items3);
        shBedrooms.setAdapter(adapter3);


        hprogressDialog=new ProgressDialog(HolidayRental.this);

        hstorageref= FirebaseStorage.getInstance().getReference(Storage_Path);
        hdataref= FirebaseDatabase.getInstance().getReference(Database_path);
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


                ActivityCompat.requestPermissions(HolidayRental.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},IMAGE_REQUEST_CODE);
            }
        });

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String WName = hName.getText().toString().trim();
            String Wcost = hPrice.getText().toString().trim();
            String WDesc = hDesc.getText().toString().trim();
            String WPhone = hnumber.getText().toString().trim();

            Uploaddata.setEnabled(!WName.isEmpty() && !Wcost.isEmpty() && !WDesc.isEmpty() && !WPhone.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

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
            himguri = data.getData();
            Picasso.with(this).load(himguri).into(HolidayImage);
        }
    }
    private String getFileExtensoin (Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private void uploadImage() {
        if (himguri!=null){
            StorageReference storageReference=hstorageref.child(System.currentTimeMillis()+"."+ getFileExtensoin(himguri));
            storageReference.putFile(himguri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String CommercialLoc= hLocation.getSelectedItem().toString().trim();
                            String CommercialTypes= HouseType.getSelectedItem().toString().trim();
                            String CommercialBR= Bedroom_No.getSelectedItem().toString().trim();

                            Handler handler=new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    hprogressDialog.setProgress(0);
                                }
                            },5000);
                            Toast.makeText(HolidayRental.this,"Upload SuccessFul",Toast.LENGTH_SHORT).show();
                            HolidayData list_data=new HolidayData(hName.getText().toString().trim(),
                                    hPrice.getText().toString().trim(),CommercialLoc,CommercialBR,CommercialTypes,
                                    hDesc.getText().toString().trim(), hnumber.getText().toString().trim(),taskSnapshot.getDownloadUrl().toString());

                            //(String title_of_Holiday_House,String holdiay_HousePrice,String holdiay_House_Location,
                            //  String holiday_Bedroom_No,String holiday_HouseDesc, String imageURL, String hKey)
                            String uploadid=hdataref.push().getKey();
                            hdataref.child(uploadid).setValue(list_data);
                            startActivity(new Intent(HolidayRental.this, AddProperty.class));
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
            Toast.makeText(HolidayRental.this,"File Not Selected",Toast.LENGTH_SHORT).show();
        }

    }
}

class CurrencyTextWatcher implements TextWatcher {
    boolean hPrice;
    public CurrencyTextWatcher(){
        hPrice = false;
    }
    public synchronized void afterTextChanged(Editable s){
        if (!hPrice){
            hPrice = true;
            String digits = s.toString().replaceAll("\\D", "");
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
            try{
                String formatted = numberFormat.format(Double.parseDouble(digits)/100);
                s.replace(0, s.length(), formatted);
            } catch (NumberFormatException nfe) {
                s.clear();
            }
            hPrice =false;
        }

    }
    public void beforeTextChanged(CharSequence s, int start, int count, int after){}
    public void onTextChanged(CharSequence s, int start, int before, int count) { }
}


package com.example.mehome.Models.AddingProperty.CommercialSale;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.mehome.Models.AddingProperty.AddProperty;
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
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.NumberFormat;

public class AddCommercial extends AppCompatActivity {
    private Button selectImage,Uploaddata;
    private ImageView RentalImage;
    private String Storage_Path = "Commercial/";
    private String Database_path = "Commercial/";

    private EditText cName, cPrice, cDesc, cnumber;
    private static final int PICK_IMAGE_REQUEST=1;
    private DatabaseReference cdataref;
    private StorageReference cstorageref;
    Spinner cLocation, cType;
    final int IMAGE_REQUEST_CODE = 999;
    private ProgressDialog cprogressDialog;
    private Uri cimguri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_commercial);


        selectImage=(Button)findViewById(R.id.ButtonChooseImageHoliday);

        RentalImage=findViewById(R.id.ShowImageViewHoliday);
        Uploaddata=findViewById(R.id.ButtonUploadImageHoliday);
        cName=findViewById(R.id.houseTitleHoliday);
        cDesc =findViewById(R.id.DescriptionHoliday);
        cLocation =findViewById(R.id.CommercialLocation);
        cType = findViewById(R.id.CommercialType);
        cPrice = findViewById(R.id.housePriceHoliday);
        cnumber =findViewById(R.id.numberHoliday);

        cPrice.addTextChangedListener(new CurrencyTextWatcher());

        cName.addTextChangedListener(textWatcher);
        cPrice.addTextChangedListener(textWatcher);
        cDesc.addTextChangedListener(textWatcher);
        cnumber.addTextChangedListener(textWatcher);

        Spinner scLocation = findViewById(R.id.CommercialLocation);

        String[] items = new String[]{"Kakmega Town", "Amalemba", "Lurambi","Kefinco", "Matende"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        scLocation.setAdapter(adapter);

        Spinner scType = findViewById(R.id.CommercialType);
        String[] items1 = new String[]{"Offices", "Shops", "Stores", "Hotel", "Multi Purpose"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        scType.setAdapter(adapter1);




        cprogressDialog=new ProgressDialog(AddCommercial.this);

        cstorageref= FirebaseStorage.getInstance().getReference(Storage_Path);
        cdataref= FirebaseDatabase.getInstance().getReference(Database_path);
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


                ActivityCompat.requestPermissions(AddCommercial.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        IMAGE_REQUEST_CODE);
            }
        });

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String WName = cName.getText().toString().trim();
            String Wcost = cPrice.getText().toString().trim();
            String WDesc = cDesc.getText().toString().trim();
            String WPhone = cnumber.getText().toString().trim();

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
            cimguri = data.getData();
            Picasso.with(this).load(cimguri).into(RentalImage);
        }
    }
    private String getFileExtensoin (Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private void uploadImage() {
        if (cimguri!=null){
            StorageReference storageReference=cstorageref.child(System.currentTimeMillis()+"."+ getFileExtensoin(cimguri));
            storageReference.putFile(cimguri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String CommercialLoc= cLocation.getSelectedItem().toString().trim();
                            String CommercialTypes= cType.getSelectedItem().toString().trim();
                            Handler handler=new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    cprogressDialog.setProgress(0);
                                }
                            },5000);
                            Toast.makeText(AddCommercial.this,"Upload SuccessFul",Toast.LENGTH_SHORT).show();
                            CommercialData list_data=new CommercialData(cName.getText().toString().trim(),
                                    cPrice.getText().toString().trim(),CommercialLoc, CommercialTypes,
                                    cDesc.getText().toString().trim(), cnumber.getText().toString().trim(),taskSnapshot.getDownloadUrl().toString());


                            String uploadid=cdataref.push().getKey();
                            cdataref.child(uploadid).setValue(list_data);
                            startActivity(new Intent(AddCommercial.this, AddProperty.class));
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
                    cprogressDialog.setProgress((int) pr);
                }
            });
        }else {
            Toast.makeText(AddCommercial.this,"File Not Selected",Toast.LENGTH_SHORT).show();
        }

    }

}
class CurrencyTextWatcher implements TextWatcher {
    boolean cPrice;
    public CurrencyTextWatcher(){
        cPrice = false;
    }
    public synchronized void afterTextChanged(Editable s){
        if (!cPrice){
            cPrice = true;
            String digits = s.toString().replaceAll("\\D", "");
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
            try{
                String formatted = numberFormat.format(Double.parseDouble(digits)/100);
                s.replace(0, s.length(), formatted);
            } catch (NumberFormatException nfe) {
                s.clear();
            }
            cPrice =false;
        }

    }
    public void beforeTextChanged(CharSequence s, int start, int count, int after){}
    public void onTextChanged(CharSequence s, int start, int before, int count) { }
}

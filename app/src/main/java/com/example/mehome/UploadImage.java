package com.example.mehome;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mehome.Models.Display.MyProperty;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;

public class UploadImage extends AppCompatActivity {
    private Button selectimg,btnUpload;
    private ImageView simg;
    private String Storage_Path = "mydata/";
    private String Database_path = "mydata/";
    private EditText etName, etPrice, etDesc;
    private static final int PICK_IMAGE_REQUEST=1;
    private DatabaseReference mdataref;
    Spinner mlocation;
    private StorageReference mstorageref;
    final int IMAGE_REQUEST_CODE = 999;
    private ProgressBar progressBar;
    private Uri mimguri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);


        selectimg=(Button)findViewById(R.id.btn_select);
        simg=(ImageView)findViewById(R.id.selekedimg);
        btnUpload=(Button)findViewById(R.id.btn_upload);
        etName=(EditText)findViewById(R.id.img_name);
        mlocation = findViewById(R.id.img_loc);
        etDesc =findViewById(R.id.img_Desc);
        etPrice = findViewById(R.id.img_price);
        progressBar=new ProgressBar(UploadImage.this);
        mstorageref= FirebaseStorage.getInstance().getReference(Storage_Path);
        mdataref= FirebaseDatabase.getInstance().getReference(Database_path);

        etPrice.addTextChangedListener(new CurrencyTextWatcher());

        etName.addTextChangedListener(textWatcher);
        etPrice.addTextChangedListener(textWatcher);
        etDesc.addTextChangedListener(textWatcher);


        Spinner bedRm = findViewById(R.id.img_loc);

        String[] items = new String[]{"1", "2", "three"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        bedRm.setAdapter(adapter);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        selectimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectImageIntent = new Intent();

                // Setting intent type as image to select image from phone storage.
                selectImageIntent.setType("image/*");
                selectImageIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(selectImageIntent, "Please Select" +
                        "Image"),IMAGE_REQUEST_CODE);


                ActivityCompat.requestPermissions(UploadImage.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},IMAGE_REQUEST_CODE);
            }
        });

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String WName = etName.getText().toString().trim();
            String Wcost = etPrice.getText().toString().trim();
            String WDesc = etDesc.getText().toString().trim();

            btnUpload.setEnabled(!WName.isEmpty() && !Wcost.isEmpty() && !WDesc.isEmpty());


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
            mimguri = data.getData();
            Picasso.with(this).load(mimguri).into(simg);
        }
    }
    private String getFileExtensoin (Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private void uploadImage() {
        if (mimguri!=null){
            StorageReference storageReference=mstorageref.child(System.currentTimeMillis()+"."+ getFileExtensoin(mimguri));
            storageReference.putFile(mimguri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String HouseTypes= mlocation.getSelectedItem().toString().trim();
                            Handler handler=new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            },5000);
                            Toast.makeText(UploadImage.this,"Upload SuccsessFul",Toast.LENGTH_SHORT).show();


                            TheData list_data=new TheData(etName.getText().toString().trim(),taskSnapshot.getDownloadUrl().toString(),etDesc.getText().toString().trim(), HouseTypes, etPrice.getText().toString().trim());
                            String uploadid=mdataref.push().getKey();
                            mdataref.child(uploadid).setValue(list_data);
                            startActivity(new Intent(UploadImage.this, MyProperty.class));
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
                    progressBar.setProgress((int) pr);
                }
            });
        }else {
            Toast.makeText(UploadImage.this,"File Not Selected",Toast.LENGTH_SHORT).show();
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

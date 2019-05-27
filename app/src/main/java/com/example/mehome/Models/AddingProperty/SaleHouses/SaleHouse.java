package com.example.mehome.Models.AddingProperty.SaleHouses;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
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

import java.io.IOException;

public class SaleHouse extends AppCompatActivity {

    private String Storage_Path = "Sale_houses/ ";

    private String Database_Path = "Sale_houses/ ";

    private Button SelectImage, UploadData;
    EditText Title_of_House, House_Location, HouseDesc, HousePrice;
    Spinner HouseType, Bedroom_No;
    private ImageView SelectedImage;

    private Uri FilePathUri;

    // Creating StorageReference and DatabaseReference object.
    StorageReference storageReference;

    DatabaseReference databaseReference;

    int Image_Request_code = 8;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_house);


        //get the spinner from the xml.
        Spinner bedRm = findViewById(R.id.bedroomSpinnersale);
//create a list of items for the spinner.
        String[] rooms = new String[]{"None", "1", "2", "3", "4", "5", "6","7"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, rooms);
//set the spinners adapter to the previously created one.
        bedRm.setAdapter(adapter);

        //get the spinner from the xml.
        Spinner houseTyp = findViewById(R.id.HouseTypeSpinnersale);
//create a list of items for the spinner.
        String[] availabletypes = new String[]{"Family", "Bachelor", "Both"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapters = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, availabletypes);
//set the spinners adapter to the previously created one.
        houseTyp.setAdapter(adapters);


        // Assign FirebaseStorage instance to storageReference.
        storageReference = FirebaseStorage.getInstance().getReference();

        // Assign FirebaseDatabase instance with root database name.
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);


        SelectImage =findViewById(R.id.ButtonChooseImagesale);
        UploadData = findViewById(R.id.ButtonUploadImagesale);

        Title_of_House =  findViewById(R.id.houseTitlesale);
        House_Location = findViewById(R.id.locationsale);
        HouseDesc = findViewById(R.id.Descriptionsale);
        HousePrice =findViewById(R.id.housePricesale);

        HouseType = findViewById(R.id.HouseTypeSpinnersale);
        Bedroom_No = findViewById(R.id.bedroomSpinnersale);


        SelectedImage = findViewById(R.id.ShowImagesale);

        progressDialog =new ProgressDialog(SaleHouse.this);



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

        SelectedImage.setOnClickListener(new View.OnClickListener() {
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
            FilePathUri = data.getData();

            try{

                // Getting selected image into Bitmap.

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                // Setting up bitmap selected image into ImageView.
                SelectedImage.setImageBitmap(bitmap);

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
        if (FilePathUri!=null){

            progressDialog.setTitle("Uploading Data...");

            progressDialog.show();

            //second StorageReference.

            StorageReference storageReference1 = storageReference.child(Storage_Path+System.currentTimeMillis()+"."+GetFileExtension(FilePathUri));

            storageReference1.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String houseImageName =  Title_of_House.getText().toString().trim();
                            String HouseArea = House_Location.getText().toString().trim();
                            String HouseCost= HousePrice.getText().toString().trim();
                            String HouseDescp = HouseDesc.getText().toString().trim();
                            String HouseTypes= HouseType.getSelectedItem().toString().trim();
                            String BedRooms = Bedroom_No.getSelectedItem().toString().trim();

                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(), "Data Uploaded Successfully", Toast.LENGTH_SHORT).show();

                            @SuppressWarnings("VisibleForTest")
                            SaleData dataUploadInfo =new SaleData(houseImageName,HouseArea, HouseCost, HouseDescp,HouseTypes, BedRooms,taskSnapshot.getStorage().getDownloadUrl().toString());// getResult().getStorage().getDownloadUrl());


                            // Getting image upload ID.
                            String ImageUploadId = databaseReference.push().getKey();

                            //adding an upload to firebase database
                            // String uploadId = mDatabase.push().getKey();
                            // mDatabase.child(uploadId).setValue(upload);

                            // Adding image upload id s child element into databaseReference.
                            databaseReference.child(ImageUploadId).setValue(dataUploadInfo);


                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();

                            Toast.makeText(SaleHouse.this, e.getMessage(), Toast.LENGTH_SHORT).show();


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
            Toast.makeText(SaleHouse.this, "Please Select Image", Toast.LENGTH_SHORT).show();
        }
    }
}

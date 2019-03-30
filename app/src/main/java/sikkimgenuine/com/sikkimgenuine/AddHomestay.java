package sikkimgenuine.com.sikkimgenuine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class AddHomestay extends AppCompatActivity {
        private String destinationName,downloadImageUri;
        private ImageView imageView;
        private String homestayRandomKey;
        private String saveCurrentDate,saveCurrentTime;
        private Button add_homestay;
        private ProgressDialog progressDialog;
        private EditText name,address,number,description,amenities,price,type;
        private String homestayName,homestayAddress,homestayNumber,homestayDesciption,homestayAmenities,homestayPrice,homestayType;
        private static final int GalleryPick=1;
        private Uri imageuri;
        private StorageReference homestayImagesRef;
        private DatabaseReference homestayRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_homestay);
        destinationName=getIntent().getExtras().get("Destination").toString();
        imageView=findViewById(R.id.homestay_image);
        add_homestay=findViewById(R.id.add_homestay);
        name=findViewById(R.id.homestay_name);
        address=findViewById(R.id.homestay_address);
        type=findViewById(R.id.homestay_type);
        number=findViewById(R.id.homestay_phone);
        description=findViewById(R.id.homestay_description);
        amenities=findViewById(R.id.homestay_amenities);
        price=findViewById(R.id.homestay_price);
        progressDialog=new ProgressDialog(this);
        homestayImagesRef= FirebaseStorage.getInstance().getReference().child("Homestay Images");
        homestayRef=FirebaseDatabase.getInstance().getReference().child("Homestays");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        add_homestay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validataHomestayData();
            }
        });

    }
    private void openGallery()
    {
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,GalleryPick);


    }
    private void validataHomestayData()
    {
        homestayName=name.getText().toString();
        homestayAddress=address.getText().toString();
        homestayDesciption=description.getText().toString();
        homestayAmenities=amenities.getText().toString();
        homestayPrice=price.getText().toString();
        homestayNumber=number.getText().toString();
        homestayType=type.getText().toString();

        if(imageuri==null)
        {
            Toasty.custom(getApplicationContext(), "Homestay Image is mandatory", getResources().getDrawable(R.drawable.ic_info_outline_white_24dp),
                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();

        }
        else if(TextUtils.isEmpty(homestayName))
        {
            Toasty.custom(getApplicationContext(), "Please fill homestay name", getResources().getDrawable(R.drawable.ic_info_outline_white_24dp),
                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();

        }
        else if(TextUtils.isEmpty(homestayAddress))
        {
            Toasty.custom(getApplicationContext(), "Please fill your homestay address", getResources().getDrawable(R.drawable.ic_info_outline_white_24dp),
                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();

        }
        else if(TextUtils.isEmpty(homestayNumber))
        {
            Toasty.custom(getApplicationContext(), "Please fill your homestay number", getResources().getDrawable(R.drawable.ic_info_outline_white_24dp),
                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();

        }
        else if(TextUtils.isEmpty(homestayDesciption))
        {
            Toasty.custom(getApplicationContext(), "Please fill your homestay description", getResources().getDrawable(R.drawable.ic_info_outline_white_24dp),
                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();

        }
        else if(TextUtils.isEmpty(homestayAmenities))
        {
            Toasty.custom(getApplicationContext(), "Please fill your homestay amenities", getResources().getDrawable(R.drawable.ic_info_outline_white_24dp),
                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();

        }
        else if(TextUtils.isEmpty(homestayPrice))
        {
            Toasty.custom(getApplicationContext(), "Please fill your homestay price", getResources().getDrawable(R.drawable.ic_info_outline_white_24dp),
                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();

        }
        else if(TextUtils.isEmpty(homestayType))
        {
            Toasty.custom(getApplicationContext(), "Please fill your homestay type", getResources().getDrawable(R.drawable.ic_info_outline_white_24dp),
                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();
        }
        else
        {
            storeHomestayInformation();
        }

    }
    private void storeHomestayInformation()
    {

        progressDialog.setTitle("Add new homestay");
        progressDialog.setMessage("Dear Admin, please wait we are adding the homestay detials");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());
        homestayRandomKey=saveCurrentDate+saveCurrentTime;
        final StorageReference filepath=homestayImagesRef.child(imageuri.getLastPathSegment()+ homestayRandomKey + ".jpg");
        final UploadTask uploadTask=filepath.putFile(imageuri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String error=e.toString();
                Toasty.custom(AddHomestay.this, "Error: "+error, getResources().getDrawable(R.drawable.ic_info_outline_white_24dp),
                        getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();

                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toasty.custom(AddHomestay.this, "Homestay Image uploaded successfully", getResources().getDrawable(R.drawable.ic_check),
                        getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();
                Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful())
                        {
                            progressDialog.dismiss();
                            throw task.getException();
                        }
                        downloadImageUri=filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful())
                        {
                            downloadImageUri=task.getResult().toString();
                            Toasty.custom(AddHomestay.this, "Got the homestay image successfully", getResources().getDrawable(R.drawable.ic_check),
                                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();
                                saveHomestayInfoToDatabase();
                        }
                    }
                });
            }
        });




    }

    private void saveHomestayInfoToDatabase() {

        HashMap<String,Object> homestayMap=new HashMap<>();
        homestayMap.put("hid",homestayRandomKey);
        homestayMap.put("date",saveCurrentDate);
        homestayMap.put("time",saveCurrentTime);
        homestayMap.put("destination",destinationName);
        homestayMap.put("description",homestayDesciption);
        homestayMap.put("address",homestayAddress);
        homestayMap.put("number",homestayNumber);
        homestayMap.put("name",homestayName);
        homestayMap.put("amenities",homestayAmenities);
        homestayMap.put("image",downloadImageUri);
        homestayMap.put("price",homestayPrice);
        homestayMap.put("type",homestayType);
        homestayRef.child(homestayRandomKey).updateChildren(homestayMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Intent intent=new Intent(AddHomestay.this,AdminDestination.class);
                            startActivity(intent);
                            progressDialog.dismiss();
                            Toasty.custom(AddHomestay.this, "Homestay Information added successfully", getResources().getDrawable(R.drawable.ic_check),
                                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();

                        }
                        else
                        {
                            progressDialog.dismiss();
                            String error=task.getException().toString();
                            Toasty.custom(AddHomestay.this, "Error: "+error, getResources().getDrawable(R.drawable.ic_info_outline_white_24dp),
                                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();

                        }
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GalleryPick && resultCode==RESULT_OK && data !=null)
        {
            imageuri=data.getData();
            imageView.setImageURI(imageuri);

        }
    }
}

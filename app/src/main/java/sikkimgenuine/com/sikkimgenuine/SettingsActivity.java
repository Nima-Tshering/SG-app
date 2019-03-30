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
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import sikkimgenuine.com.sikkimgenuine.Prevalent.Prevalent;

public class SettingsActivity extends AppCompatActivity {
    private EditText fullname,user_address,phonenumber;
    private TextView close_btn,save_btn,profilebtn;
    private CircleImageView profile_image;
    private Uri imageUri;
    private StorageReference storageProfileRef;
    private String myUrl="";
    private String checker="";
    private StorageTask uploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        storageProfileRef= FirebaseStorage.getInstance().getReference().child("Profile pictures");

        fullname = findViewById(R.id.profile_name);
        user_address = findViewById(R.id.profile_address);
        profilebtn=findViewById(R.id.profile_change);
        phonenumber = findViewById(R.id.profile_number);
        close_btn = findViewById(R.id.close_settings_btn);
        save_btn = findViewById(R.id.update_settings_btn);
        profile_image = findViewById(R.id.update_profile_picture);

        userInfoDisplay(profile_image,fullname,user_address,phonenumber);

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checker.equals("clicked"))
                {

                    userInfoSaved();
                }
                else
                {
                    updateOnlyUserInfo();
                }

            }
        });
        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker="clicked";
                CropImage.activity(imageUri)
                        .setAspectRatio(1,1)
                        .start(SettingsActivity.this);
            }
        });
    }

    private void userInfoDisplay(final CircleImageView profile_image, final EditText fullname, final EditText user_address, final EditText phonenumber) {

        DatabaseReference usersRef= FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getNumber());
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    if(dataSnapshot.child("image").exists())
                    {
                        String image=dataSnapshot.child("image").getValue().toString();
                        String name=dataSnapshot.child("name").getValue().toString();
                        String address=dataSnapshot.child("address").getValue().toString();
                        String number=dataSnapshot.child("phoneOrder").getValue().toString();
                        Picasso.get().load(image).into(profile_image);
                        fullname.setText(name);
                        user_address.setText(address);
                        phonenumber.setText(number);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void updateOnlyUserInfo() {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users");
        HashMap<String,Object>userMap=new HashMap<>();
        userMap.put("name",fullname.getText().toString());
        userMap.put("address",user_address.getText().toString());
        userMap.put("phoneOrder",phonenumber.getText().toString());
        ref.child(Prevalent.currentOnlineUser.getNumber()).updateChildren(userMap);
        startActivity(new Intent(SettingsActivity.this,Home.class));
        Toasty.custom(getApplicationContext(), "Profile info updated successfully", getResources().getDrawable(R.drawable.ic_check),
                getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data!=null)
        {
            CropImage.ActivityResult activityResult=CropImage.getActivityResult(data);
            imageUri=activityResult.getUri();
            profile_image.setImageURI(imageUri);


        }
        else
        {
            Toasty.custom(getApplicationContext(), "Error: Try again", getResources().getDrawable(R.drawable.ic_close),
                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();
            startActivity(new Intent(SettingsActivity.this,SettingsActivity.class));
            finish();
        }
    }

    private void userInfoSaved() {
        if(TextUtils.isEmpty(fullname.getText().toString()))
        {
            Toasty.custom(getApplicationContext(), "Please Fill your name", getResources().getDrawable(R.drawable.ic_error_outline_white_24dp),
                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();
        }
        else if(TextUtils.isEmpty(user_address.getText().toString()))
        {
            Toasty.custom(getApplicationContext(), "Please Fill your address", getResources().getDrawable(R.drawable.ic_error_outline_white_24dp),
                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();
        }
        else if(TextUtils.isEmpty(phonenumber.getText().toString()))
        {
            Toasty.custom(getApplicationContext(), "Please Fill your phone number", getResources().getDrawable(R.drawable.ic_error_outline_white_24dp),
                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();
        }
        else if(checker.equals("clicked"))
        {

            uploadImage();
        }
    }
    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("User Profile");
        progressDialog.setMessage("Please wait, while we are waiting saving your information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if (imageUri != null) {
            final StorageReference fileRef = storageProfileRef
                    .child(Prevalent.currentOnlineUser.getNumber() + ".jpg");

            uploadTask = fileRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {

                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUrl = task.getResult();
                        myUrl = downloadUrl.toString();
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("name", fullname.getText().toString());
                        userMap.put("address", user_address.getText().toString());
                        userMap.put("phoneOrder", phonenumber.getText().toString());
                        userMap.put("image", myUrl);
                        ref.child(Prevalent.currentOnlineUser.getNumber()).updateChildren(userMap);
                        progressDialog.dismiss();
                        startActivity(new Intent(SettingsActivity.this, Home.class));
                        Toasty.custom(getApplicationContext(), "Profile Information Updated Successfully", getResources().getDrawable(R.drawable.ic_check),
                                getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();
                        finish();
                    } else {
                        progressDialog.dismiss();
                        Toasty.custom(getApplicationContext(), "Error Occurred: Try again", getResources().getDrawable(R.drawable.ic_close),
                                getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();

                    }
                }
            });
        } else {
            Toasty.custom(getApplicationContext(), "Image is not selected", getResources().getDrawable(R.drawable.ic_error_outline_white_24dp),
                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();
        }
    }




}

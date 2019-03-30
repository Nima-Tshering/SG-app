package sikkimgenuine.com.sikkimgenuine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class Register extends AppCompatActivity {
        private EditText inputName,inputPhone,inputPassword;
        private Button register;
       private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inputName=findViewById(R.id.button2);
        inputPhone=findViewById(R.id.button3);
        inputPassword=findViewById(R.id.button4);
        register=findViewById(R.id.button);
       progressDialog=new ProgressDialog(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

    }
    private void createAccount()
    {
        String name,password,number;
        name=inputName.getText().toString();
        password=inputPassword.getText().toString();
        number=inputPhone.getText().toString();
        if(TextUtils.isEmpty(name))
        {


            Toasty.custom(this, "Please fill your name", getResources().getDrawable(R.drawable.ic_info_outline_white_24dp),
                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();
        }
        else if(TextUtils.isEmpty(password))
        {

            Toasty.custom(this, "Please fill your password", getResources().getDrawable(R.drawable.ic_info_outline_white_24dp),
                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();

        }
        else if(TextUtils.isEmpty(number))
        {
            Toasty.custom(this, "Please fill your phone number", getResources().getDrawable(R.drawable.ic_info_outline_white_24dp),
                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();
        }
        else
        {
            progressDialog.setTitle("Create Account");
            progressDialog.setMessage("Please wait, while we check your credentials");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            validate(name,number,password);

        }
    }
    private void validate(final String name, final String number, final String password)
    {

        final DatabaseReference databaseReference;
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child("Users").child(number).exists())
                {
                    HashMap<String,Object> userdata=new HashMap<>();
                    userdata.put("number",number);
                    userdata.put("name",name);
                    userdata.put("password",password);
                    databaseReference.child("Users").child(number).updateChildren(userdata)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            Toasty.custom(getApplicationContext(), "Congratulation your account is created ", getResources().getDrawable(R.drawable.ic_check),
                                                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();
                                            progressDialog.dismiss();
                                            Intent intent=new Intent(Register.this,login.class);
                                            startActivity(intent);
                                        }
                                        else
                                        {
                                            progressDialog.dismiss();

                                            Toasty.custom(getApplicationContext(), "Network Error!", getResources().getDrawable(R.drawable.ic_info_outline_white_24dp),
                                                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();

                                        }
                                }
                            });


                }
                else
                {



                    progressDialog.dismiss();

                    Toasty.custom(getApplicationContext(), "This" +number+" already exist. Please user another number", getResources().getDrawable(R.drawable.ic_close),
                            getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_LONG, true, true).show();

                    Intent intent=new Intent(Register.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

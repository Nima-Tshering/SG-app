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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.PrivilegedAction;

import es.dmoral.toasty.Toasty;
import io.paperdb.Paper;
import sikkimgenuine.com.sikkimgenuine.Model.User;
import sikkimgenuine.com.sikkimgenuine.Prevalent.Prevalent;

public class MainActivity extends AppCompatActivity {
    private Button loginButton,registerButton;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton=findViewById(R.id.button3);
        registerButton=findViewById(R.id.button);
        progressDialog=new ProgressDialog(this);
        Paper.init(this);


        String userPhoneKey=Paper.book().read(Prevalent.userPhoneKey);
        String userPasswordKey=Paper.book().read(Prevalent.userPasswordKey);
        if(userPhoneKey!="" && userPasswordKey!="")
        {
            if(!TextUtils.isEmpty(userPhoneKey) && !TextUtils.isEmpty(userPasswordKey))
            {
                AllowAccess(userPhoneKey,userPasswordKey);
                progressDialog.setTitle("Already Logged In");
                progressDialog.setMessage("Please wait.....");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
            }
        }

    }
    private void AllowAccess(final String number, final String password)
    {
        final DatabaseReference databaseReference;
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(number).exists())
                {
                    User userdata=dataSnapshot.child("Users").child(number).getValue(User.class);
                    if(userdata.getNumber().equals(number))
                    {
                        if(userdata.getPassword().equals(password))
                        {
                            Toasty.custom(getApplicationContext(), "Please wait. You are already logged in", getResources().getDrawable(R.drawable.ic_info_outline_white_24dp),
                                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();

                            progressDialog.dismiss();
                            Intent i=new Intent(MainActivity.this,Home.class);
                            Prevalent.currentOnlineUser=userdata;
                            startActivity(i);
                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toasty.custom(getApplicationContext(), "Password does not match. Try again", getResources().getDrawable(R.drawable.ic_close),
                                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();


                        }
                    }
                }
                else if(dataSnapshot.child("Admins").child(number).exists())
                {
                    User userdata=dataSnapshot.child("Admins").child(number).getValue(User.class);
                    if(userdata.getNumber().equals(number))
                    {
                        if(userdata.getPassword().equals(password))
                        {
                            Toasty.custom(getApplicationContext(), "Please wait. You are already logged in", getResources().getDrawable(R.drawable.ic_info_outline_white_24dp),
                                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();

                            progressDialog.dismiss();
                            Intent i=new Intent(MainActivity.this,AdminHome.class);
                            Prevalent.currentOnlineUser=userdata;
                            startActivity(i);
                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toasty.custom(getApplicationContext(), "Password does not match. Try again", getResources().getDrawable(R.drawable.ic_close),
                                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();


                        }
                    }

                }
                else

                {

                    Toasty.custom(getApplicationContext(), "Account with this "+number+" number does not exists", getResources().getDrawable(R.drawable.ic_close),
                            getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();


                    progressDialog.dismiss();
                    Toasty.custom(getApplicationContext(), "You need to create new account", getResources().getDrawable(R.drawable.ic_close),
                            getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void login(View view)
    {
        Intent i=new Intent(getApplicationContext(),login.class);
        startActivity(i);
    }
    public void register(View view)
    {
        Intent in=new Intent(getApplicationContext(),Register.class);
        startActivity(in);
    }


}

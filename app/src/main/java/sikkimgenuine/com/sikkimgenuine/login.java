package sikkimgenuine.com.sikkimgenuine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;
import io.paperdb.Paper;
import sikkimgenuine.com.sikkimgenuine.Model.User;
import sikkimgenuine.com.sikkimgenuine.Prevalent.Prevalent;

public class login extends AppCompatActivity {
    private EditText inputNumber,inputPassword;
    private Button login;
    private TextView notAdmin;
    private TextView Admin;
    private ProgressDialog progressDialog;
    private String parentDbName="Users";
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputNumber=findViewById(R.id.button2);
        inputPassword=findViewById(R.id.button4);
        login=findViewById(R.id.button);
        progressDialog=new ProgressDialog(this);
        notAdmin=findViewById(R.id.not_admin);
        Admin=findViewById(R.id.admin);
        checkBox=(CheckBox) findViewById(R.id.remember_me);

        Paper.init(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });
        notAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            login.setText("Login");
            notAdmin.setVisibility(View.INVISIBLE);
            Admin.setVisibility(View.VISIBLE);
            parentDbName="Users";
            }
        });
        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setText("Admin Login");
                Admin.setVisibility(View.INVISIBLE);
                notAdmin.setVisibility(View.VISIBLE);
                parentDbName="Admins";

            }
        });

    }
    private void LoginUser()
    {
        String number=inputNumber.getText().toString();
        String password=inputPassword.getText().toString();
        if(TextUtils.isEmpty(number))
        {
            Toasty.custom(getApplicationContext(), "Please fill your phone number", getResources().getDrawable(R.drawable.ic_info_outline_white_24dp),
                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();

        }
        else if(TextUtils.isEmpty(password))
        {
            Toasty.custom(getApplicationContext(), "Please fill your password", getResources().getDrawable(R.drawable.ic_info_outline_white_24dp),
                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();

        }
        else
        {
            progressDialog.setTitle("Login Account");
            progressDialog.setMessage("Please wait, while we check your credentials");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            AllowAccessToAccount(number,password);
        }
    }
    private void AllowAccessToAccount(final String number, final String password)
    {
        if(checkBox.isChecked())
        {
            Paper.book().write(Prevalent.userPhoneKey,number);
            Paper.book().write(Prevalent.userPasswordKey,password);

        }
        final DatabaseReference databaseReference;
        databaseReference=FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDbName).child(number).exists())
                {
                    User userdata=dataSnapshot.child(parentDbName).child(number).getValue(User.class);
                    if(userdata.getNumber().equals(number))
                    {
                        if(userdata.getPassword().equals(password))
                        {
                            if(parentDbName.equals("Users"))
                            {

                                Toasty.custom(getApplicationContext(), "Logged in Successfully", getResources().getDrawable(R.drawable.ic_check),
                                        getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();

                                progressDialog.dismiss();
                                Intent i=new Intent(login.this,Home.class);
                                Prevalent.currentOnlineUser=userdata;
                                startActivity(i);
                            }
                            else if(parentDbName.equals("Admins"))
                            {

                                Toasty.custom(getApplicationContext(), "Welcome Admin, you are logged in Successfully", getResources().getDrawable(R.drawable.ic_check),
                                        getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();

                                progressDialog.dismiss();
                                Intent i=new Intent(login.this, AdminHome.class);
                                Prevalent.currentOnlineUser=userdata;
                                startActivity(i);
                            }

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



                    progressDialog.dismiss();
                    Toasty.custom(getApplicationContext(), "Account with this "+number+" number does not exists", getResources().getDrawable(R.drawable.ic_close),
                            getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_LONG, true, true).show();




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}

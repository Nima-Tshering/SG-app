package sikkimgenuine.com.sikkimgenuine;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import sikkimgenuine.com.sikkimgenuine.Prevalent.Prevalent;

public class ConfirmOrder extends AppCompatActivity {
        private String totalamount;
        private EditText confirmName,confirmEmail,confirmNumber,arrival,departure;
        private Button booknow;
        private TextView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        confirmName=findViewById(R.id.confirmname);
        confirmEmail=findViewById(R.id.email);
        confirmNumber=findViewById(R.id.confirmnumber);
        arrival=findViewById(R.id.arrival);
        departure=findViewById(R.id.departure);
        back=findViewById(R.id.back_btn);
        booknow=findViewById(R.id.confirmbook);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConfirmOrder.this,CartList.class));
            }
        });
        totalamount=getIntent().getStringExtra("Total Price");
        Toasty.custom(getApplicationContext(), "Total Charges: INR "+totalamount+"/-", getResources().getDrawable(R.drawable.ic_check),
                getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_LONG, true, true).show();
        booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }

            private void check() {

                if(TextUtils.isEmpty(confirmName.getText().toString()))
                {
                    Toasty.custom(getApplicationContext(), "Please Fill your name", getResources().getDrawable(R.drawable.ic_error_outline_white_24dp),
                            getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();
                }
                else if(TextUtils.isEmpty(confirmEmail.getText().toString()))
                {
                    Toasty.custom(getApplicationContext(), "Please Fill your Email Id", getResources().getDrawable(R.drawable.ic_error_outline_white_24dp),
                            getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();

                }
                else if(TextUtils.isEmpty(confirmNumber.getText().toString()))
                {
                    Toasty.custom(getApplicationContext(), "Please Fill your phone number", getResources().getDrawable(R.drawable.ic_error_outline_white_24dp),
                            getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();
                }
                else if(TextUtils.isEmpty(arrival.getText().toString()))
                {
                    Toasty.custom(getApplicationContext(), "Please Fill Arrival Date", getResources().getDrawable(R.drawable.ic_error_outline_white_24dp),
                            getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();
                }
                else if(TextUtils.isEmpty(departure.getText().toString()))
                {
                    Toasty.custom(getApplicationContext(), "Please Fill Departure Date", getResources().getDrawable(R.drawable.ic_error_outline_white_24dp),
                            getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_SHORT, true, true).show();
                }
                else
                {
                  confirmOrder();
                }

            }

            private void confirmOrder() {
                final String saveCurrentDate,saveCurrentTime;

                Calendar calendar=Calendar.getInstance();
                SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
                saveCurrentDate=currentDate.format(calendar.getTime());
                SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime=currentTime.format(calendar.getTime());


                final DatabaseReference orderRef= FirebaseDatabase.getInstance().getReference().child("Orders")
                        .child(Prevalent.currentOnlineUser.getNumber());
                HashMap<String,Object>orderMap=new HashMap<>();
                orderMap.put("totalamount",totalamount);
                orderMap.put("name",confirmName.getText().toString());
                orderMap.put("email",confirmEmail.getText().toString());
                orderMap.put("phonenumber",confirmNumber.getText().toString());
                orderMap.put("arrivaldate",arrival.getText().toString());
                orderMap.put("departuredate",departure.getText().toString());
                orderMap.put("date",saveCurrentDate);
                orderMap.put("time",saveCurrentTime);
                orderMap.put("state","Not Confirmed");
                orderRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                         FirebaseDatabase.getInstance().getReference().child("Cart List")
                                 .child("User View")
                                 .child(Prevalent.currentOnlineUser.getNumber())
                                 .removeValue()
                                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                                     @Override
                                     public void onComplete(@NonNull Task<Void> task) {
                                         if(task.isSuccessful())
                                         {
                                             Toasty.custom(getApplicationContext(), "Your final order is successfully placed. ", getResources().getDrawable(R.drawable.ic_error_outline_white_24dp),
                                                     getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_LONG, true, true).show();
                                                Intent intent=new Intent(ConfirmOrder.this,Home.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();

                                         }
                                     }
                                 });

                        }
                    }
                });





            }
        });
    }
}

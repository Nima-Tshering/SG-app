package sikkimgenuine.com.sikkimgenuine;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import sikkimgenuine.com.sikkimgenuine.Model.Homestays;
import sikkimgenuine.com.sikkimgenuine.Prevalent.Prevalent;

public class HomestayDetials extends AppCompatActivity {
        private TextView nameHomestay,addressHomestay,descriptionHomestay,facilityHomestay,priceHomestay,destinationHomestay;
        private Button add_btn;
        private ImageView homestayImage;
        private ElegantNumberButton customer;
        private String homestayID,state="Normal";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homestay_detials);
        nameHomestay=findViewById(R.id.name);
        addressHomestay=findViewById(R.id.address);
        descriptionHomestay=findViewById(R.id.description);
        facilityHomestay=findViewById(R.id.facilities);

        homestayImage=findViewById(R.id.image);
        customer=findViewById(R.id.number);
        priceHomestay=findViewById(R.id.price);
        destinationHomestay=findViewById(R.id.desti);
        add_btn=findViewById(R.id.add_to_cart);
        homestayID=getIntent().getStringExtra("hid");
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(state=="Order Confirmed"|| state=="Order Placed")
                {
                    Toasty.custom(getApplicationContext(), "You can book more homestays, ones your reservation is confirmed or you board on your current homestay", getResources().getDrawable(R.drawable.ic_info_outline_white_24dp),
                            getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_LONG, true, true).show();
                }
                else

                {
                    addToCartList();
                }
            }
        });
        getHomestayDetails(homestayID);

    }

    @Override
    protected void onStart() {
        super.onStart();
         checkOrderState();
    }

    private void addToCartList() {
        String saveCurrentDate,saveCurrentTime;
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("hh:mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());
        final DatabaseReference cartlistRef= FirebaseDatabase.getInstance().getReference().child("Cart List");
        final HashMap<String, Object>cartlist=new HashMap<>();
        cartlist.put("hid",homestayID);
        cartlist.put("homestayname",nameHomestay.getText().toString());
        cartlist.put("homestayprice",priceHomestay.getText().toString());
        cartlist.put("date",saveCurrentDate);
        cartlist.put("time",saveCurrentTime);
        cartlist.put("customernumber",customer.getNumber());
        cartlist.put("discount","");
        cartlistRef.child("User View").child(Prevalent.currentOnlineUser.getNumber()).child("Homestays")
                .child(homestayID)
                .updateChildren(cartlist)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {cartlistRef.child("Admin View").child(Prevalent.currentOnlineUser.getNumber()).child("Homestays")
                                .child(homestayID)
                                .updateChildren(cartlist)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            Toasty.custom(getApplicationContext(), "Homestay added to your save list", getResources().getDrawable(R.drawable.ic_check),
                                                    getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_LONG, true, true).show();
                                            Intent intent =new Intent(HomestayDetials.this,Home.class);
                                            startActivity(intent);
                                        }
                                    }
                                });

                        }
                    }
                });



    }

    private void getHomestayDetails(String homestayID) {
        DatabaseReference homeRef= FirebaseDatabase.getInstance().getReference().child("Homestays");
        homeRef.child(homestayID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    Homestays homestays=dataSnapshot.getValue(Homestays.class);
                    nameHomestay.setText(homestays.getName());
                    addressHomestay.setText(homestays.getAddress());
                    facilityHomestay.setText(homestays.getAmenities());
                    priceHomestay.setText(homestays.getPrice());
                    descriptionHomestay.setText(homestays.getDescription());
                    destinationHomestay.setText(homestays.getDestination());


                    Picasso.get().load(homestays.getImage()).placeholder(R.drawable.loading).into(homestayImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void checkOrderState()
    {
        DatabaseReference stateRef=FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getNumber());
        stateRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String orderstate=dataSnapshot.child("state").getValue().toString();
                    String name=dataSnapshot.child("name").getValue().toString();
                    if(orderstate.equals("Confirmed"))
                    {
                        state="Order Confirmed";
                    }
                    else if(orderstate.equals("Not Confirmed"))
                    {
                        state="Order Placed";
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

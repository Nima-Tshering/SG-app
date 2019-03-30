package sikkimgenuine.com.sikkimgenuine;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;
import sikkimgenuine.com.sikkimgenuine.Model.Cart;
import sikkimgenuine.com.sikkimgenuine.Prevalent.Prevalent;
import sikkimgenuine.com.sikkimgenuine.ViewHolder.CartViewHolder;

public class CartList extends AppCompatActivity {
        private RecyclerView cartList;
        private TextView totalPrice,back_button,message;
        private RecyclerView.LayoutManager layoutManager;
        private Button nextButton;
        private int overTotalPrice=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        cartList=findViewById(R.id.cartlist);
        cartList.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        cartList.setLayoutManager(layoutManager);
        totalPrice=findViewById(R.id.totol_price);
        nextButton=findViewById(R.id.next);
        message=findViewById(R.id.messageone);
        back_button=findViewById(R.id.back_btn);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalPrice.setText("Total Price = INR "+String.valueOf(overTotalPrice)+"/-");
                Intent intent=new Intent(CartList.this,ConfirmOrder.class);
                intent.putExtra("Total Price",String.valueOf(overTotalPrice));
                startActivity(intent);
                finish();

            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartList.this,Home.class));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        checkOrderState();
        final DatabaseReference cartlistRef= FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart>options=
                new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartlistRef.child("User View")
                .child(Prevalent.currentOnlineUser.getNumber())
                        .child("Homestays"),Cart.class)
                        .build();
        FirebaseRecyclerAdapter<Cart, CartViewHolder>adapter
                =new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model) {
                holder.textpersons.setText("No of persons: "+model.getCustomernumber());
                holder.textHomestayPrice.setText("Charges: "+model.getHomestayprice()+"X"+model.getCustomernumber());
                holder.textHomestayName.setText(model.getHomestayname());
                int onePrice=((Integer.valueOf(model.getHomestayprice()))) * Integer.valueOf(model.getCustomernumber());
                overTotalPrice=overTotalPrice+onePrice;

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         CharSequence op[]= new CharSequence[]
                                 {
                                         "Edit",
                                         "Delete"
                                 };
                        AlertDialog.Builder builder=new AlertDialog.Builder(CartList.this);
                        builder.setTitle("Cart Option");
                        builder.setItems(op, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==0)
                                {
                                    Intent intent=new Intent(CartList.this,HomestayDetials.class);
                                    intent.putExtra("hid",model.getHid());
                                    startActivity(intent);
                                }
                                if(which==1)
                                {
                                    cartlistRef.child("User View")
                                            .child(Prevalent.currentOnlineUser.getNumber())
                                            .child("Homestays")
                                            .child(model.getHid())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if(task.isSuccessful())
                                                    {
                                                        Toasty.custom(getApplicationContext(), "Saved Homestay Deleted Successfully", getResources().getDrawable(R.drawable.ic_check),
                                                                getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_LONG, true, true).show();
                                                        Intent intent=new Intent(CartList.this,Home.class);

                                                        startActivity(intent);
                                                    }
                                                }
                                            });

                                }
                            }
                        });
                        builder.show();

                    }
                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_list_items,viewGroup,false);
             CartViewHolder cartViewHolder=new CartViewHolder(view);
             return cartViewHolder;

            }
        };

cartList.setAdapter(adapter);
adapter.startListening();
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
                       totalPrice.setText("Dear "+name+" your reservertion is confirmed.");
                       cartList.setVisibility(View.GONE);
                       message.setVisibility(View.VISIBLE);
                       nextButton.setVisibility(View.GONE);

                        Toasty.custom(getApplicationContext(), "You can book more homestays, ones you board on your current reservation ", getResources().getDrawable(R.drawable.ic_check),
                                getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_LONG, true, true).show();

                    }
                    else if(orderstate.equals("Not Confirmed"))
                    {
                        totalPrice.setText("Reservation: Not Confirmed");
                        cartList.setVisibility(View.GONE);
                        message.setVisibility(View.VISIBLE);
                        nextButton.setVisibility(View.GONE);

                        Toasty.custom(getApplicationContext(), "You can book more homestays, ones you board on your current reservation ", getResources().getDrawable(R.drawable.ic_check),
                                getColor(R.color.colorPrimaryDark), Color.WHITE, Toasty.LENGTH_LONG, true, true).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

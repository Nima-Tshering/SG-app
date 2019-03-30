package sikkimgenuine.com.sikkimgenuine;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import sikkimgenuine.com.sikkimgenuine.Model.Cart;
import sikkimgenuine.com.sikkimgenuine.ViewHolder.CartViewHolder;

public class AdminUserHomestay extends AppCompatActivity {
private RecyclerView orderList;
private String userID;
RecyclerView.LayoutManager layoutManager;
private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_homestay);
        userID=getIntent().getStringExtra("uid");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Cart List")
                .child("Admin View").child(userID).child("Homestays");
        orderList=findViewById(R.id.userorderlist);
        orderList.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        orderList.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Cart> options=
                new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(databaseReference,Cart.class)
                .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder>adapter=
                new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {
                        holder.textpersons.setText("No of persons: "+model.getCustomernumber());
                        holder.textHomestayPrice.setText("Charges: "+model.getHomestayprice());
                        holder.textHomestayName.setText(model.getHomestayname());
                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_list_items,viewGroup,false);
                        CartViewHolder cartViewHolder=new CartViewHolder(view);
                        return cartViewHolder;
                    }
                };
        orderList.setAdapter(adapter);
        adapter.startListening();
    }
}

package sikkimgenuine.com.sikkimgenuine;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import sikkimgenuine.com.sikkimgenuine.Model.AdminOrders;

public class AdminNewOrderActivity extends AppCompatActivity {
        private RecyclerView orderlist;
        private DatabaseReference orderRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_order);
        orderlist=findViewById(R.id.orderlist);

        orderRef= FirebaseDatabase.getInstance().getReference().child("Orders");
        orderlist.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseRecyclerOptions<AdminOrders> options=
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                .setQuery(orderRef,AdminOrders.class)
                .build();
       FirebaseRecyclerAdapter<AdminOrders,AdminOrderViewHolder> adapter=
               new FirebaseRecyclerAdapter<AdminOrders, AdminOrderViewHolder>(options) {
                   @Override
                   protected void onBindViewHolder(@NonNull AdminOrderViewHolder holder, final int position, @NonNull final AdminOrders model) {
                        holder.username.setText("Name: "+model.getName());
                        holder.usernumber.setText("Phone: "+model.getPhonenumber());
                        holder.arrival.setText("Arrival Date: "+model.getArrivaldate());
                        holder.departure.setText("Departure Date: "+model.getDeparturedate());
                        holder.totalprice.setText("Total Price: "+model.getTotalamount());
                        holder.booktime.setText("Book Time: "+model.getTime());
                        holder.bookdate.setText("Book Date: "+model.getDate());
                        holder.useremail.setText("Email: "+model.getEmail());
                        holder.showhomestay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String uID=getRef(position).getKey();
                                Intent intent=new Intent(AdminNewOrderActivity.this,AdminUserHomestay.class);
                                intent.putExtra("uid",uID);
                                startActivity(intent);
                            }
                        });
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence op[]= new CharSequence[]
                                        {
                                                "Yes",
                                                "No"
                                        };
                                AlertDialog.Builder builder=new AlertDialog.Builder(AdminNewOrderActivity.this);
                                builder.setTitle("Have You confirmed this booking?");
                                builder.setItems(op, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                            if(which==0)
                                            {
                                                String uID=getRef(position).getKey();
                                                removeOrder(uID);
                                            }
                                            else
                                            {
                                             finish();
                                            }

                                    }
                                });
                                builder.show();
                            }
                        });


                   }

                   @NonNull
                   @Override
                   public AdminOrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                       View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.orderslayout,viewGroup,false);
                       return new AdminOrderViewHolder(view);
                   }
               };

orderlist.setAdapter(adapter);
adapter.startListening();



    }

    private void removeOrder(String uID) {
        orderRef.child(uID).removeValue();
    }

    public static class AdminOrderViewHolder extends RecyclerView.ViewHolder
    {
        public TextView username,usernumber,useremail,totalprice,arrival,departure,booktime,bookdate;
        public Button showhomestay;

        public AdminOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.orderusername);
            usernumber=itemView.findViewById(R.id.orderusernumber);
            useremail=itemView.findViewById(R.id.orderemail);
            totalprice=itemView.findViewById(R.id.ordertotalprice);
            arrival=itemView.findViewById(R.id.orderarrival);
            departure=itemView.findViewById(R.id.orderdeparture);
            bookdate=itemView.findViewById(R.id.bookdate);
            booktime=itemView.findViewById(R.id.booktime);
            showhomestay=itemView.findViewById(R.id.show_all_homestay);

        }
    }
}

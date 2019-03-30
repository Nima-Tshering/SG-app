package sikkimgenuine.com.sikkimgenuine;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import sikkimgenuine.com.sikkimgenuine.Model.Homestays;
import sikkimgenuine.com.sikkimgenuine.ViewHolder.HomestayViewHolder;

public class All_Homestay extends AppCompatActivity{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView back;

    private DatabaseReference homestayRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__homestay);
            homestayRef= FirebaseDatabase.getInstance().getReference().child("Homestays");
            recyclerView=findViewById(R.id.recyle_menu);
            recyclerView.setHasFixedSize(true);
            layoutManager=new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            back=findViewById(R.id.back_btn);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(All_Homestay.this,Home.class);
                    startActivity(intent);
                }
            });

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Homestays> options=
                new FirebaseRecyclerOptions.Builder<Homestays>()
                        .setQuery(homestayRef,Homestays.class)
                        .build();

        FirebaseRecyclerAdapter<Homestays, HomestayViewHolder> adapter=
                new FirebaseRecyclerAdapter<Homestays, HomestayViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull HomestayViewHolder holder, int position, @NonNull final Homestays model) {
                        holder.txtHomestayName.setText(model.getName());
                        holder.txtHomestayAddress.setText(model.getAddress());
                        holder.textPrice.setText(model.getPrice());
                        holder.textDestination.setText(model.getDestination());
                        Picasso.get().load(model.getImage()).placeholder(R.drawable.loading).into(holder.imageView);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(All_Homestay.this,HomestayDetials.class);
                                intent.putExtra("hid",model.getHid());
                                startActivity(intent);

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public HomestayViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.homestay_layout,viewGroup,false);
                        HomestayViewHolder viewHolder=new HomestayViewHolder(view);
                        return viewHolder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

}

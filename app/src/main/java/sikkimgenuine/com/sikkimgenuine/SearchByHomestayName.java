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
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import sikkimgenuine.com.sikkimgenuine.Model.Homestays;
import sikkimgenuine.com.sikkimgenuine.ViewHolder.HomestayViewHolder;

public class SearchByHomestayName extends AppCompatActivity {
    private Button search_btn;
    private String searchText;
    private EditText inputText;
    private RecyclerView search_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_homestay_name);
        search_btn=findViewById(R.id.searchbtn);
        inputText=findViewById(R.id.search_homestay);
        search_list=findViewById(R.id.searchlist);
        search_list.setLayoutManager(new LinearLayoutManager(this));
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText=inputText.getText().toString();
                onStart();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Homestays");
        FirebaseRecyclerOptions<Homestays> options=
                new FirebaseRecyclerOptions.Builder<Homestays>()
                        .setQuery(reference.orderByChild("name").startAt(searchText).endAt(searchText),Homestays.class)
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
                                Intent intent=new Intent(SearchByHomestayName.this,HomestayDetials.class);
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
        search_list.setAdapter(adapter);
        adapter.startListening();
    }
}

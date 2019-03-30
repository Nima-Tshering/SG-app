package sikkimgenuine.com.sikkimgenuine.ViewHolder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import sikkimgenuine.com.sikkimgenuine.All_Homestay;
import sikkimgenuine.com.sikkimgenuine.HomestayDetials;
import sikkimgenuine.com.sikkimgenuine.Interface.ItemClickedListener;
import sikkimgenuine.com.sikkimgenuine.R;

public class HomestayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView txtHomestayName,txtHomestayAddress,textPrice,textDestination;
    public ImageView imageView;
    public ItemClickedListener listener;
    public HomestayViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.image);
        txtHomestayName=itemView.findViewById(R.id.name);
        txtHomestayAddress=itemView.findViewById(R.id.address);
        textPrice=itemView.findViewById(R.id.price);
        textDestination=itemView.findViewById(R.id.homesay_destination);


    }
public void setItemClickListener(ItemClickedListener listener)
{
    this.listener=listener;
}
    @Override
    public void onClick(View v) {
listener.onClick(v,getAdapterPosition(),false);
    }
}

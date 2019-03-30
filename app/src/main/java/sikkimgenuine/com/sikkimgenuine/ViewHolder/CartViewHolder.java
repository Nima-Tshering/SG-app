package sikkimgenuine.com.sikkimgenuine.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import sikkimgenuine.com.sikkimgenuine.Interface.ItemClickedListener;
import sikkimgenuine.com.sikkimgenuine.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView textHomestayName,textHomestayPrice,textpersons;
    private ItemClickedListener itemClickedListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        textHomestayName=itemView.findViewById(R.id.homestayName);
        textHomestayPrice=itemView.findViewById(R.id.homestaycharges);
        textpersons=itemView.findViewById(R.id.nopersons);


    }


    @Override
    public void onClick(View v) {
        itemClickedListener.onClick(v,getAdapterPosition(),false);
    }

    public void setItemClickedListener(ItemClickedListener itemClickedListener) {
        this.itemClickedListener = itemClickedListener;
    }
}

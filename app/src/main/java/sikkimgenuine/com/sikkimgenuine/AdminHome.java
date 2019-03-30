package sikkimgenuine.com.sikkimgenuine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.paperdb.Paper;

public class AdminHome extends AppCompatActivity {
        private Button admin_logout,admin_orders,admin_add,admin_gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        admin_logout=findViewById(R.id.admin_logout);
        admin_orders=findViewById(R.id.admin_check_order);
        admin_add=findViewById(R.id.admin_addhomestay);
        admin_gallery=findViewById(R.id.admin_addgallery);
        Paper.init(this);
        admin_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this,AdminDestination.class));
            }
        });
        admin_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this,AdminNewOrderActivity.class));
            }
        });
        admin_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().destroy();
                Intent intent=new Intent(AdminHome.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        admin_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this,AddGalleryImages.class));
            }
        });

    }
}

package sikkimgenuine.com.sikkimgenuine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import io.paperdb.Paper;

public class AdminDestination extends AppCompatActivity {
ImageView gangtok,mangan,rumtek,lachung,lachen,dzongu,temi,ravangla,namchi,pelling,rangpo,okhrey;
private TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_destination);
        gangtok=findViewById(R.id.gangtokButton);
        mangan=findViewById(R.id.manganButton);
        rumtek=findViewById(R.id.rumtekButton);
        lachen=findViewById(R.id.lachenButton);
        lachung=findViewById(R.id.lachungButton);
        dzongu=findViewById(R.id.dzonguButton);
        temi=findViewById(R.id.temiButton);
        ravangla=findViewById(R.id.ravanglaButton);
        namchi=findViewById(R.id.namchiButton);
        pelling=findViewById(R.id.pellingButton);
        rangpo=findViewById(R.id.rangpoButton);
        okhrey=findViewById(R.id.okhreyButton);
        back=findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDestination.this,AdminHome.class));
            }
        });

        Paper.init(this);
        gangtok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminDestination.this,AddHomestay.class);
                i.putExtra("Destination","Gangtok");
                startActivity(i);
            }
        });
        mangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminDestination.this,AddHomestay.class);
                i.putExtra("Destination","Mangan");
                startActivity(i);
            }
        });
        rumtek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminDestination.this,AddHomestay.class);
                i.putExtra("Destination","Rumtek");
                startActivity(i);
            }
        });
        lachung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminDestination.this,AddHomestay.class);
                i.putExtra("Destination","Lachung");
                startActivity(i);
            }
        });
        lachen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminDestination.this,AddHomestay.class);
                i.putExtra("Destination","Lachen");
                startActivity(i);
            }
        });
     dzongu.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent i=new Intent(AdminDestination.this,AddHomestay.class);
             i.putExtra("Destination","Dzongu");
             startActivity(i);
         }
     });
     temi.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent i=new Intent(AdminDestination.this,AddHomestay.class);
             i.putExtra("Destination","Temi");
             startActivity(i);
         }
     });
     ravangla.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent i=new Intent(AdminDestination.this,AddHomestay.class);
             i.putExtra("Destination","Ravangla");
             startActivity(i);
         }
     });
     namchi.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent i=new Intent(AdminDestination.this,AddHomestay.class);
             i.putExtra("Destination","Namchi");
             startActivity(i);
         }
     });
     pelling.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent i=new Intent(AdminDestination.this,AddHomestay.class);
             i.putExtra("Destination","Pelling");
             startActivity(i);
         }
     });
     rangpo.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent i=new Intent(AdminDestination.this,AddHomestay.class);
             i.putExtra("Destination","Rangpo");
             startActivity(i);
         }
     });
     okhrey.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent i=new Intent(AdminDestination.this,AddHomestay.class);
             i.putExtra("Destination","Okhrey");
             startActivity(i);
         }
     });



    }
}

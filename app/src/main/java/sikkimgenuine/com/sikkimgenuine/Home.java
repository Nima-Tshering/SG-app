package sikkimgenuine.com.sikkimgenuine;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;
import sikkimgenuine.com.sikkimgenuine.Model.Homestays;
import sikkimgenuine.com.sikkimgenuine.Prevalent.Prevalent;
import sikkimgenuine.com.sikkimgenuine.ViewHolder.HomestayViewHolder;

public class Home extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {
    private ImageView imageView;
private ImageView home_capital,home_north,home_cheap;
private ImageView home_room,home_gallery,home_about,home_contact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageView=findViewById(R.id.sliderImage);

        home_capital=findViewById(R.id.home_capital);
        home_cheap=findViewById(R.id.home_cheap);
        home_north=findViewById(R.id.home_north);
        home_about=findViewById(R.id.home_about);
        home_room=findViewById(R.id.home_room);
        home_gallery=findViewById(R.id.home_gallery);
        home_contact=findViewById(R.id.home_contact);
        home_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,ContactUs.class));
            }
        });
        home_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,All_Homestay.class));
            }
        });
        home_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,ShowGallery.class));
            }
        });
        home_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,AboutUs.class));
            }
        });
        home_cheap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,CheapHomestay.class));
            }
        });
        home_capital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,CapitalHomestay.class));
            }
        });
        home_north.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,NorthHomestay.class));
            }
        });
        AnimationDrawable animationDrawable=(AnimationDrawable)imageView.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();
        setSupportActionBar(toolbar);
        toolbar.setTitle("Home");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,CartList.class));

            }
       });
        Paper.init(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
            View viewHeader=navigationView.getHeaderView(0);
        TextView userName=viewHeader.findViewById(R.id.user_profile_name);
        CircleImageView image=viewHeader.findViewById(R.id.user_profile_image);

        userName.setText(Prevalent.currentOnlineUser.getName());
        Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(image);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart) {

            startActivity(new Intent(Home.this,CartList.class));
            // Handle the camera action
        }
        else if(id==R.id.cont)
        {
            startActivity(new Intent(Home.this,ContactUs.class));
        }
        else if(id==R.id.aboutus)
        {
            startActivity(new Intent(Home.this,AboutUs.class));
        }
        else if(id==R.id.allhome)
        {
            startActivity(new Intent(Home.this,All_Homestay.class));
        }
        else if(id==R.id.gallery)
        {
startActivity(new Intent(Home.this,ShowGallery.class));
        }
        else if (id == R.id.nav_searchHome) {
            startActivity(new Intent(Home.this,SearchByHomestayName.class));

        } else if (id == R.id.nav_search) {
            startActivity(new Intent(Home.this,SearchHomestay.class));

        } else if (id == R.id.settings) {
            Intent intent=new Intent(Home.this,SettingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.logout) {
            Paper.book().destroy();
            Intent intent=new Intent(Home.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

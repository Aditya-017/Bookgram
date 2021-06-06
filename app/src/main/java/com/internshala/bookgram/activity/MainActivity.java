package com.internshala.bookgram.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import com.internshala.bookgram.fragment.AboutAppFragment;
import com.internshala.bookgram.fragment.DashboardFragment;
import com.internshala.bookgram.fragment.FavouritesFragment;
import com.internshala.bookgram.fragment.ProfileFragment;
import com.internshala.bookgram.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
      DrawerLayout dl;
      CoordinatorLayout cl;
      Toolbar t;
      FrameLayout fl;
      NavigationView nv;
    private Object DashboardFragment;
    MenuItem previousMenuItem=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dl=findViewById(R.id.drawerLayout);
        cl=findViewById(R.id.coordinatoeLayout);
        t=findViewById(R.id.toolbar);
        fl=findViewById(R.id.frame);
        nv=findViewById(R.id.navigationView);
        setUpToolbar();
        openDashboard();
        //enabling drawer layout to listen to action on toggle
        ActionBarDrawerToggle abdt=new ActionBarDrawerToggle(this,dl,R.string.open_drawer,R.string.close_drawer);
        dl.addDrawerListener(abdt);
        abdt.syncState();//synchronize state of toggle and navigation drawer
        //adding click listeners now
        nv.setNavigationItemSelectedListener(this);

    }

    private void setUpToolbar(){
        setSupportActionBar(t);
        getSupportActionBar().setTitle("Toolbar Title");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //callback method for "addDrawerListener
     int id=item.getItemId();
     if(id==android.R.id.home){
         dl.openDrawer(GravityCompat.START);
     }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(previousMenuItem!=null){
            previousMenuItem.setChecked(false);
        }
        item.setCheckable(true);
        item.setChecked(true);
        previousMenuItem=item;
        switch(item.getItemId()){
            case R.id.dashboard:
                openDashboard();
                dl.closeDrawers();
                break;
            case R.id.favourites:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new FavouritesFragment()).commit();
                getSupportActionBar().setTitle("Favourites");
                dl.closeDrawers();
                break;
            case R.id.profile:

                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new ProfileFragment()).commit();
                getSupportActionBar().setTitle("Profile");
                dl.closeDrawers();
                break;
            case R.id.aboutApp:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new AboutAppFragment()).commit();
                getSupportActionBar().setTitle("About App");
                dl.closeDrawers();
                break;
        }
        return true;
    }
    private void openDashboard(){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new DashboardFragment()).commit();
        getSupportActionBar().setTitle("Dashboard");
        nv.setCheckedItem(R.id.dashboard);
    }

    @Override
    public void onBackPressed() {
        if(!(getSupportFragmentManager().findFragmentById(R.id.frame) ==DashboardFragment))
        {
            openDashboard();}

        else
            if(getSupportFragmentManager().findFragmentById(R.id.frame) ==DashboardFragment){
            super.onBackPressed();
            }
    }
}

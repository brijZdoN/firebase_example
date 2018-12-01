package com.example.brijj.jsonexample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction().add(R.id.m,new Home(),Home.class.getSimpleName()).commit();
       //TODO this method id used for give the animation to the acitivity during start
       /// overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {   switch (item.getItemId())
    {
        case R.id.setting://getSupportFragmentManager().beginTransaction().add(R.id.m, new chat()).addToBackStack(null).commit();
            Toast.makeText(getApplicationContext(), "setting clicked", Toast.LENGTH_SHORT).show();
            break;

        case R.id.logout:
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent=new Intent(MainActivity.this,Login.class);
            startActivity(intent);

    }
        return super.onOptionsItemSelected(item);


    }


}

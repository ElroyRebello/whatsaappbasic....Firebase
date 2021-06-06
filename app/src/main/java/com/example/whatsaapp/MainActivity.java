package com.example.whatsaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsaapp.adapter.Fragmentadapter;

import com.example.whatsaapp.databinding.ActivityMainBinding;
import com.example.whatsaapp.model.settings;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
   ActivityMainBinding binding;
   FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        binding.viewpag.setAdapter(new Fragmentadapter(getSupportFragmentManager()));
        binding.tablayout.setupWithViewPager(binding.viewpag);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                auth.signOut();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this,SignIn.class);
                startActivity(intent);
                break;

            case  R.id.settings:
                Intent i = new Intent(MainActivity.this, settings.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
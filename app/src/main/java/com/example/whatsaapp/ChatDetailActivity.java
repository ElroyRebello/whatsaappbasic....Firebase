package com.example.whatsaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.whatsaapp.adapter.chatadapter;
import com.example.whatsaapp.databinding.ActivityChatDetailBinding;
import com.example.whatsaapp.model.messagesmodel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {


    ActivityChatDetailBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();


        final String senderId = auth.getUid();
        String reciveId =getIntent().getStringExtra("userid");
        String profilepic =getIntent().getStringExtra("profilepic");
        String username =getIntent().getStringExtra("username");

        binding.back.setOnClickListener(v -> {
            Intent i = new Intent(ChatDetailActivity.this,MainActivity.class);
            startActivity(i);
        });
        binding.textViewofname.setText(username);
        Picasso.get().load(profilepic).placeholder(R.drawable.ic_user).into(binding.profileImage);

        //3:40

        final
        ArrayList<messagesmodel> messagesmodels = new ArrayList<>();

        final chatadapter chatadapter = new chatadapter(messagesmodels,this);

        binding.chatdetailrecycle.setAdapter(chatadapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatdetailrecycle.setLayoutManager(layoutManager);
           final String senderroom = senderId+reciveId;
        final String reciverroom = reciveId+senderId;


        database.getReference().child("chats")
                .child(senderroom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        messagesmodels.clear();
                        for(DataSnapshot snapshot1 : snapshot.getChildren() )
                        {
                            messagesmodel model = snapshot1.getValue(messagesmodel.class);
                            messagesmodels.add(model);
                        }
                        chatadapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

   //3:43
        binding.sendimg.setOnClickListener(v -> {
            if(binding.entermsg.getText().toString().isEmpty())
            {
                binding.entermsg.setError("blank not allowed");
                return;
            }

           String message= binding.entermsg.getText().toString();

           final messagesmodel model = new messagesmodel(senderId,message);

           model.setTimestamp(new Date().getTime());

           binding.entermsg.setText("");

           database.getReference()
                   .child("chats")
                   .child(senderroom)
                   .push()
                   .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
               @Override
               public void onSuccess(Void unused) {
                   database.getReference()
                           .child("chats")
                           .child(reciverroom)
                           .push()
                           .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void unused) {

                       }
                   });
               }
           });


        });

    }
}
package com.example.whatsaapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whatsaapp.R;
import com.example.whatsaapp.adapter.useradapter;
import com.example.whatsaapp.databinding.FragmentChatsBinding;
import com.example.whatsaapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class chatsFragment extends Fragment {


    public chatsFragment() {
        // Required empty public constructor
    }
     FragmentChatsBinding binding;
      ArrayList<User> list = new ArrayList<>();
      FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =FragmentChatsBinding.inflate(inflater,container,false);
        database = FirebaseDatabase.getInstance();
        useradapter useradapter = new useradapter(list,getContext());

        binding.chatrecv.setAdapter(useradapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.chatrecv.setLayoutManager(layoutManager);

        database.getReference().child("public").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                 for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                   //  User s = new User(dataSnapshot.child("profilepic").getValue(String.class),
                   //          dataSnapshot.child("userName").getValue(String.class),
                   //          dataSnapshot.child("mail").getValue(String.class),
                   //          dataSnapshot.child("pass").getValue(String.class),
                   ////          dataSnapshot.child("userId").getValue(String.class),
                        //     dataSnapshot.child("lastMessage").getValue(String.class));
                      User user = dataSnapshot.getValue(User.class);
                      user.setUserId(dataSnapshot.getKey());
                      if(!user.getUserId().equals(FirebaseAuth.getInstance().getUid()))
                         list.add(user);

                 }

                 useradapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }
}
package com.example.whatsaapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsaapp.ChatDetailActivity;
import com.example.whatsaapp.R;
import com.example.whatsaapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class useradapter extends RecyclerView.Adapter<useradapter.ViewHolder> {

    ArrayList<User> list;
    Context context;

    public useradapter(ArrayList<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.samplerecy,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull useradapter.ViewHolder holder, int position) {
          User wauser = list.get(position);
        Picasso.get().load(wauser.getProfilepic()).placeholder(R.drawable.ic_user).into(holder.image);
         holder.chatname.setText(wauser.getUserName());

        FirebaseDatabase.getInstance().getReference().child("chats")
                .child(FirebaseAuth.getInstance().getUid() + wauser.getUserId())
                .orderByChild("time")
                .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1:snapshot.getChildren())
                        {
                            holder.lastmsg.setText(snapshot1.child("message").getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

         holder.itemView.setOnClickListener(v -> {
             Intent  intent = new Intent(context, ChatDetailActivity.class);
             intent.putExtra("username",wauser.getUserName());
             intent.putExtra("profilepic",wauser.getProfilepic());
             intent.putExtra("userid",wauser.getUserId());
             context.startActivity(intent);
         });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
           ImageView image;
           TextView chatname,lastmsg;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.profile_image);
            chatname = itemView.findViewById(R.id.chatname);
            lastmsg = itemView.findViewById(R.id.lastmsg);
        }
    }
}

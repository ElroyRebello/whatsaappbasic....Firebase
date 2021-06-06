package com.example.whatsaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsaapp.R;
import com.example.whatsaapp.model.messagesmodel;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EventListener;

public class chatadapter extends  RecyclerView.Adapter {

    ArrayList<messagesmodel> messagesmodels;
    Context context;
    int SENDER_VIEW_TYPE = 1;
    int RECIVER_VIEW_TYPE = 2;
    public chatadapter(ArrayList<messagesmodel> messagesmodels, Context context) {
        this.messagesmodels = messagesmodels;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        if(viewType == SENDER_VIEW_TYPE)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender_chatbubble,parent,false);
            return new SenderViewHolder(view);
        }
       else {
           View view = LayoutInflater.from(context).inflate(R.layout.sample_reciver_chatbubble,parent,false);
                return new ReciverViewHolder(view);
        }

    }

    @Override
    public int getItemViewType(int position) {

        if(messagesmodels.get(position).getUid().equals(FirebaseAuth.getInstance().getUid()))
        {
            return  SENDER_VIEW_TYPE;
        }
        else {
            return  RECIVER_VIEW_TYPE;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
           messagesmodel msg = messagesmodels.get(position);
        if(holder.getClass()==SenderViewHolder.class)
        {
            ((SenderViewHolder)holder).sendermsg.setText(msg.getMessage());

            //((SenderViewHolder)holder).sendertimestamp.setText( msg.getTimestamp());
        }
        else {
            ((ReciverViewHolder)holder).recivermsg.setText(msg.getMessage());
           // ((ReciverViewHolder)holder).recivermsg.setText( msg.getTimestamp());
        }
    }

    @Override
    public int getItemCount() {
        return messagesmodels.size();
    }

    public class ReciverViewHolder extends RecyclerView.ViewHolder {

        TextView recivermsg,recivertimestamp;
        public ReciverViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            recivermsg = itemView.findViewById(R.id.recivertext);
            recivertimestamp = itemView.findViewById(R.id.recivertimestamp);

        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {

        TextView sendermsg,sendertimestamp;
        public SenderViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            sendermsg = itemView.findViewById(R.id.sendertextview);
            sendertimestamp = itemView.findViewById(R.id.sendertimestamp);

        }


    }

}

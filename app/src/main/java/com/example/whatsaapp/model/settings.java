package com.example.whatsaapp.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.example.whatsaapp.MainActivity;
import com.example.whatsaapp.R;
import com.example.whatsaapp.SignIn;
import com.example.whatsaapp.databinding.ActivitySettingsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class settings extends AppCompatActivity {
      FirebaseAuth auth;
      FirebaseDatabase database;
      FirebaseStorage storage;
    ActivitySettingsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        binding.button.setOnClickListener(v -> {
            String username= binding.serttingname.getText().toString();
            HashMap<String,Object> obj = new HashMap<>();
            obj.put("userName",username);

            database.getReference().child("public").child(FirebaseAuth.getInstance().getUid())
                    .updateChildren(obj);
            Toast.makeText(settings.this,"profile updated",Toast.LENGTH_SHORT).show();

        });

        database.getReference().child("public").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        User model = snapshot.getValue(User.class);
                        Picasso.get().load(model.getProfilepic())
                                .placeholder(R.drawable.ic_user)
                                .into(binding.profileImage);
                        binding.serttingname.setText(model.getUserName());
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
        binding.imageView8.setOnClickListener(v -> {
            Intent intent = new Intent(settings.this, MainActivity.class);
            startActivity(intent);
        });

        binding.plusimg.setOnClickListener(v -> {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_GET_CONTENT);
            i.setType("image/*");
            startActivityForResult(i,33);
        });




    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData()!= null)
        {
            Uri file = data.getData();
            binding.profileImage.setImageURI(file);
            final StorageReference storageReference = storage.getReference().child("prifile pic")
                    .child(FirebaseAuth.getInstance().getUid());


            storageReference.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("public").child(FirebaseAuth.getInstance().getUid())
                                    .child("profilepic").setValue(uri.toString());
                            Toast.makeText(settings.this,"pic updated",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });

        }

    }


}

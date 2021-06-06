package com.example.whatsaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsaapp.databinding.ActivitySignUpAvtivityBinding;
import com.example.whatsaapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;



public class SignUpAvtivity extends AppCompatActivity {


     ActivitySignUpAvtivityBinding binding;
     private FirebaseAuth auth;
      FirebaseDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding=ActivitySignUpAvtivityBinding.inflate(getLayoutInflater());
         setContentView(binding.getRoot());
         getSupportActionBar().hide();

         auth = FirebaseAuth.getInstance();
         database = FirebaseDatabase.getInstance();

        if(auth.getCurrentUser()!=null)
        {
            Intent intent = new Intent(SignUpAvtivity.this,MainActivity.class);
            startActivity(intent);
        }
         binding.btsignupsu.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                // String s1,s2,s3;
                // s1=binding.editTextTextPersonName.getText().toString();
                // s3=binding.etpasssu.getText().toString();
               //  s2=binding.etEmailsu.getText().toString();
               //  Toast.makeText(SignUpAvtivity.this, s1+s2+s3,Toast.LENGTH_SHORT).show();
                 Toast.makeText(SignUpAvtivity.this,binding.editTextTextPersonName.getText().toString()
                         +binding.etpasssu.getText().toString()+
                         binding.etEmailsu.getText().toString(),Toast.LENGTH_SHORT).show();



                 auth.createUserWithEmailAndPassword(binding.etEmailsu.getText().toString(),binding.etpasssu.getText().toString())
                         .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {

                         if(task.isSuccessful())
                         {
                             User user = new User(binding.editTextTextPersonName.getText().toString(),
                                     binding.etEmailsu.getText().toString(),
                                     binding.etpasssu.getText().toString());

                             String id = task.getResult().getUser().getUid();
                             database.getReference().child("public").child(id).setValue(user);
                             Toast.makeText(SignUpAvtivity.this,"user Succefully Created",Toast.LENGTH_SHORT).show();
                         }
                         else
                         {
                             Toast.makeText(SignUpAvtivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                         }

                     }
                 });
             }
         });

        binding.alreadthavesu.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpAvtivity.this,SignIn.class);
            startActivity(intent);
        });



    }
}
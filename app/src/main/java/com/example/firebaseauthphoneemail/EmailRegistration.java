package com.example.firebaseauthphoneemail;

import android.content.Intent;
import android.media.MediaCodec;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EmailRegistration extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private EditText name;
    private Button registered;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_registration_activity);

        email = findViewById(R.id.emailEdit);
        password = findViewById(R.id.passwordEdit);
        name = findViewById(R.id.nameEdit);
        registered = findViewById(R.id.registered);

        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(email.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please Enter email", Toast.LENGTH_LONG).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    Toast.makeText(getApplicationContext(), "Please Enter valid email", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please Enter password", Toast.LENGTH_LONG).show();
                } else if (password.getText().toString().length() < 6) {
                    Toast.makeText(getApplicationContext(), "Please Enter minimum 6 digit password", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(name.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please Enter name", Toast.LENGTH_LONG).show();
                } else {
                    doRegistration();
                }
            }
        });

    }

    private void doRegistration() {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("email",email.getText().toString());
                        map.put("password",password.getText().toString());
                        map.put("name",name.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("user")
                                .child(FirebaseAuth.getInstance().getUid())
                                .setValue(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getApplicationContext(),"Login Successfully",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(EmailRegistration.this,DashBoardActivity.class));
                                        finish();
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Failed"+e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}

package com.example.firebaseauthphoneemail;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EmailLoginActivity extends AppCompatActivity {
    private Button login;
    private EditText email;
    private android.widget.EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_login_activity);
        email = findViewById(R.id.emailEdit);
        password = findViewById(R.id.passwordEdit);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(email.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please Enter email", Toast.LENGTH_LONG).show();
                } else if (Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    Toast.makeText(getApplicationContext(), "Please Enter valid email", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please Enter password", Toast.LENGTH_LONG).show();
                } else if (password.getText().toString().length() < 6) {
                    Toast.makeText(getApplicationContext(), "Please Enter minimum 6 digit password", Toast.LENGTH_LONG).show();
                } else {
                    doLogin();
                }
            }
        });

    }

    private void doLogin() {
    }
}

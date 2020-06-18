package com.example.firebaseauthphoneemail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button otp;
    private Button email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        otp = findViewById(R.id.otpButton);
        email = findViewById(R.id.emailButton);

        otp.setOnClickListener(this);
        email.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.otpButton:
                startActivity(new Intent(MainActivity.this,PhoneLoginActivity.class));
                break;
            case R.id.emailButton:
                startActivity(new Intent(MainActivity.this,EmailLoginActivity.class));
                break;
            default:
                break;
        }
    }
}
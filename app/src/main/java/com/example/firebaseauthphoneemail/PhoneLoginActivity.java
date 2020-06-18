package com.example.firebaseauthphoneemail;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hbb20.CountryCodePicker;

public class PhoneLoginActivity extends AppCompatActivity {
    private EditText number;
    private CountryCodePicker countryCodePicker;
    private Button next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_activity);
        number = findViewById(R.id.editText_carrierNumber);
        countryCodePicker = findViewById(R.id.ccp);
        next = findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(number.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please enter phone number", Toast.LENGTH_LONG).show();
                } else if (number.getText().toString().length() < 10) {
                    Toast.makeText(getApplicationContext(), "Please enter valid number", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(PhoneLoginActivity.this, OtpVerification.class);
                    intent.putExtra("number", "+" + countryCodePicker.getSelectedCountryCodeWithPlus() + number.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}

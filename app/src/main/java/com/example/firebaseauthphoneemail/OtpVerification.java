package com.example.firebaseauthphoneemail;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpVerification extends AppCompatActivity {
    private EditText otp;
    private Button verify;
    private String number, id;
    private ProgressBar progress;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification_activity);
        otp = findViewById(R.id.otpEdit);
        verify = findViewById(R.id.verify);
        progress = findViewById(R.id.progress);
        progress.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();
        number = getIntent().getStringExtra("number");

        sendVerificationCode();

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(otp.getText().toString())) {
                    Toast.makeText(OtpVerification.this, "Enter Otp", Toast.LENGTH_SHORT).show();
                } else if (otp.getText().toString().replace(" ", "").length() != 6) {
                    Toast.makeText(OtpVerification.this, "Enter right otp", Toast.LENGTH_SHORT).show();
                } else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id, otp.getText().toString().replace(" ", ""));
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
    }

    private void sendVerificationCode() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                //super.onCodeSent(s, forceResendingToken);
                OtpVerification.this.id = s;
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(getApplicationContext(), "OTP verification Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(OtpVerification.this, DashBoardActivity.class));
                    finish();
                    FirebaseUser user = task.getResult().getUser();
                }
            }
        });
    }
}

package com.example.mehome.Models.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mehome.Models.MainActivity;
import com.example.mehome.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText userEmail, userPassword;
    private AppCompatButton logIn, forgetPassword, Registerlink;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() !=null){
        startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
         finish();
        }



        userEmail = (TextInputEditText)findViewById(R.id.EditTextEmail);
        userPassword = (TextInputEditText)findViewById(R.id.EditTextPassword);
        logIn = (AppCompatButton)findViewById(R.id.logButton);
        forgetPassword = (AppCompatButton)findViewById(R.id.ButtonForgetPassword);
        Registerlink = (AppCompatButton)findViewById(R.id.ButtonTextReg);
        progressBar =(ProgressBar)findViewById(R.id.progressBar);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        Registerlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usermail = userEmail.getText().toString();
                final String userpasswd = userPassword.getText().toString();

                if (TextUtils.isEmpty(usermail)){
                    Toast.makeText(getApplicationContext(), "Enter an Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(userpasswd)){
                    Toast.makeText(getApplicationContext(), "Enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user

                auth.signInWithEmailAndPassword(usermail, userpasswd)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if(!task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Log in failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();

                                    if (userpasswd.length() < 8) {
                                        userPassword.setError(getString(R.string.password_short));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed),
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }else {
                                    Intent intent =new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                }

                            }
                        });
            }
        });


    }
}

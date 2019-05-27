package com.example.mehome.Models.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mehome.Models.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.example.mehome.R;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout layoutName, layoutEmail, layoutPassword, layoutConfirmpassword, layoutPhone;
    private AppCompatButton RegButton, logInlink;
    private TextInputEditText inputName, inputEmail, inputPassword, inputConfirmPassword, inputPhone;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstantState){
        super.onCreate(savedInstantState);
        setContentView(R.layout.activity_sign_in);

        //Getting FireBase auth instance

        auth = FirebaseAuth.getInstance();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        layoutName =(TextInputLayout) findViewById(R.id.inputRegName);
        layoutEmail =(TextInputLayout)findViewById(R.id.inputRegEmail);
        layoutPassword = (TextInputLayout) findViewById(R.id.inputPassord);
        layoutConfirmpassword = (TextInputLayout) findViewById(R.id.inputRegConfirmPassword);
        layoutPhone = (TextInputLayout)findViewById(R.id.inputRegPhone);

        RegButton = (AppCompatButton)findViewById(R.id.NewAccountRegButton);
        logInlink = (AppCompatButton)findViewById(R.id.Already_have_account_link);

        inputName =(TextInputEditText)findViewById(R.id.EditTextRegName);
        inputEmail = (TextInputEditText)findViewById(R.id.EditTextRegEmail);
        inputPassword = (TextInputEditText)findViewById(R.id.EditTextRegPassword);
        inputConfirmPassword =(TextInputEditText)findViewById(R.id.EditTextRegConfirmPassword);
        inputPhone = (TextInputEditText)findViewById(R.id.EditTextRegPhone);

        logInlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        RegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String confirmPassword = inputConfirmPassword.getText().toString().trim();
                String phoneNumber = inputPhone.getText().toString().trim();
                //final int i=Integer.parseInt(e1.toString().trim());
                // int inputNumber = Integer.parseInt(editText.getText().toString().trim());
                //int phoneNumber = Integer.parseInt(inputPhone.getText().toString().trim());

                if (TextUtils.isEmpty(name)){
                    Toast.makeText(getApplicationContext(), "Enter Your Name", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Enter a valid email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phoneNumber)){
                    Toast.makeText(getApplicationContext(),"Enter a valid Phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 8) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 8 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                   Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                   return;

                }
                progressBar.setVisibility(View.VISIBLE);

                //create user
                auth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                                Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                    finish();
                                }
                                }

                        });

            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}

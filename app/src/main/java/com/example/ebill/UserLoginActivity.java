package com.example.ebill;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserLoginActivity extends AppCompatActivity {

    EditText emailText, passwordText,newPasstxt;
    Button loginbtn;
    TextView forgotPass, signUp;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        getSupportActionBar().hide();

        dbHelper = new DBHelper(UserLoginActivity.this);
        emailText = findViewById(R.id.emailtxt);
        passwordText = findViewById(R.id.passwordtxt);
        loginbtn = findViewById(R.id.loginbtn);
        forgotPass = findViewById(R.id.forgot_Pass);
        signUp = findViewById(R.id.sign_Up);
        newPasstxt = findViewById(R.id.newpasstext2);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateemailid()) {
                    if (validatePass()) {
                        String email_ID = emailText.getText().toString();
                        String password = passwordText.getText().toString();

                        if (dbHelper.insertLoginData(email_ID, password)) {
                            Log.d("Hii", "Rishabh" + email_ID + password);
                            emailText.setText("");
                            passwordText.setText("");
                        }
                        Toast.makeText(UserLoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(UserLoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(UserLoginActivity.this,SignupActivity.class);
                startActivity(i2);
                finish();
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3 = new Intent(UserLoginActivity.this, ForgotPassActivity.class);
                startActivity(i3);
                finish();
            }
        });
    }

    private boolean validatePass() {
        loginbtn.setEnabled(true);
        String passInput = passwordText.getText().toString();
        Boolean pass = dbHelper.checkPassword(passInput);

        Log.d("Hiii","RK"+" "+passInput+" "+pass+" "+pass.toString());
        if (passInput.isEmpty()) {
            passwordText.setError("Can't be Empty");
            passwordText.requestFocus();
            return false;
        }
        else if (pass==true) {
            return true;
        }
        else {
            Toast.makeText(UserLoginActivity.this, "Invalid Password ", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean validateemailid() {
        loginbtn.setEnabled(true);
        String emailId = emailText.getText().toString().trim();
        Boolean email = dbHelper.checkEmailId(emailId);
        Log.d("Hiii","RD"+ emailId +" "+email+" "+email.toString());
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (emailId.isEmpty()) {
            emailText.setError("Can't be Empty");
            emailText.requestFocus();
            return false;
        }
        else if (email==true && emailId.matches(emailPattern))
        {
            return true;
        }
        else {
            Toast.makeText(UserLoginActivity.this, "Invalid Email ID", Toast.LENGTH_SHORT).show();
            validatePass();
            return false;
        }


    }
}
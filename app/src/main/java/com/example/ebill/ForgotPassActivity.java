package com.example.ebill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends AppCompatActivity {

    EditText emailTxt, newPasstxt, confrmNewPassTxt;
    Button submitBtn;
    TextView backSymbol;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        getSupportActionBar().hide();

        dbHelper = new DBHelper(ForgotPassActivity.this);
        emailTxt = findViewById(R.id.emailText);
        newPasstxt = findViewById(R.id.newpasstext2);
        confrmNewPassTxt = findViewById(R.id.newpassconfrmtext2);
        submitBtn = (Button) findViewById(R.id.submitbtn);
        backSymbol = findViewById(R.id.backsymbol);

        backSymbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(ForgotPassActivity.this,UserLoginActivity.class);
                startActivity(in);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateEmailId()){
                    if(validateNewPass()){
                        if(validateConfirmPass()){
                            String email_text = emailTxt.getText().toString().trim();
                            String new_password = newPasstxt.getText().toString();
                            String confrm_newpassword = confrmNewPassTxt.getText().toString();
                            Boolean email = dbHelper.checkEmailId(email_text);
                            if (dbHelper.insertForgotPassData(email_text,new_password ,confrm_newpassword)) {
                                Log.d("Hii", "Hemant" + email_text + new_password + confrm_newpassword);
                                if(email==true){
                                    Boolean check_pass = dbHelper.updatePassword(email_text,new_password);
                                    if(check_pass==true){
                                        Toast.makeText(ForgotPassActivity.this, "Password Reset Successfully!", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(ForgotPassActivity.this, UserLoginActivity.class);
                                        startActivity(i);
                                        emailTxt.setText("");
                                        newPasstxt.setText("");
                                        confrmNewPassTxt.setText("");
                                    }
                                    else{
                                        Toast.makeText(ForgotPassActivity.this, "Password Not Reset Successfully!", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(ForgotPassActivity.this, "Invalid Email Id", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    private boolean validateEmailId() {
        submitBtn.setEnabled(true);
        String eId = emailTxt.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (eId.isEmpty()) {
            emailTxt.setError("Can't be Empty");
            emailTxt.requestFocus();

            return false;
        } else {
            if (eId.matches(emailPattern)) {
                return true;
            } else {
                emailTxt.setError("Invalid Email Id");
                return false;
            }
        }
    }


    private boolean validateConfirmPass() {
        submitBtn.setEnabled(true);
        String emailID = emailTxt.getText().toString();
        String passconfirm = confrmNewPassTxt.getText().toString();
        String confrmpass = newPasstxt.getText().toString();
        if (passconfirm.isEmpty()) {
            confrmNewPassTxt.setError("Can't be Empty");
            confrmNewPassTxt.requestFocus();
            return false;
        }
        else {
            if (passconfirm.equals(confrmpass)) {
                return true;
            } else {
                confrmNewPassTxt.setError("Retype Password");
                return false;
            }
        }
    }

    private boolean validateNewPass() {
        submitBtn.setEnabled(true);
        String passInput = newPasstxt.getText().toString();
        if(passInput.isEmpty())
        {
            newPasstxt.setError("Can't be Empty");
            newPasstxt.requestFocus();
            return false;

        }
        else if(passInput.length()<6){
            newPasstxt.setError("Minimum 6 character required");
            newPasstxt.requestFocus();
            return false;
        }
        else{
            newPasstxt.setError(null);
            return true;
        }
    }
}
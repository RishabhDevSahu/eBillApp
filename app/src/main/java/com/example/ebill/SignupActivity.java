package com.example.ebill;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    EditText fullNametxt, emailIdtxt, passwordtxt, confirmpasstxt, mobilenotxt;
    Button signUpBtn;
    TextView backSymbol,signintxt;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();

        dbHelper = new DBHelper(SignupActivity.this);
        fullNametxt = findViewById(R.id.fullnametext);
        mobilenotxt = findViewById(R.id.mobilenotext);
        emailIdtxt = findViewById(R.id.emailtext);
        passwordtxt = findViewById(R.id.passwordtext);
        confirmpasstxt = findViewById(R.id.confirmpasswordtext);
        signUpBtn = findViewById(R.id.signupbtn);
        backSymbol = findViewById(R.id.backsymbol);
        signintxt = findViewById(R.id.signin);

        backSymbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(SignupActivity.this,UserLoginActivity.class);
                startActivity(in);
            }
        });

        signintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignupActivity.this,UserLoginActivity.class);
                startActivity(i);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateName()) {
                    if(validateMobileNo()){
                        if(validateEmailAddress()) {
                            if(validatePassword()) {
                                if(validateConfirmPassword()) {
                                    String name = fullNametxt.getText().toString();
                                    String mobileno = mobilenotxt.getText().toString();
                                    String email_ID = emailIdtxt.getText().toString();
                                    String password = passwordtxt.getText().toString();
                                    String confrm_password = confirmpasstxt.getText().toString();
                                    if (dbHelper.insertSignUpData(name,mobileno,email_ID,password,confrm_password)) {
                                        Log.d("Hii", "Ravi" + name + mobileno + email_ID + password + confrm_password);
                                        fullNametxt.setText("");
                                        mobilenotxt.setText("");
                                        emailIdtxt.setText("");
                                        passwordtxt.setText("");
                                        confirmpasstxt.setText("");
                                    }
                                    Toast.makeText(SignupActivity.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                                    Intent i2 = new Intent(SignupActivity.this, UserLoginActivity.class);
                                    startActivity(i2);
                                }
                            }
                        }
                    }
                }
            }
        });

    }

    private boolean validateMobileNo() {
        signUpBtn.setEnabled(true);
        // @SuppressLint("ServiceCast") TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELECOM_SERVICE);

        String mnumber = mobilenotxt.getText().toString();
        String MobilePattern = "[6-9][0-9]{9}";
        if(mnumber.matches(MobilePattern)) {
            return true;

        } else{
            mobilenotxt.setError("Please enter valid 10 digit mobile number");
            return false;
        }
    }

    private boolean validateConfirmPassword() {
        signUpBtn.setEnabled(true);
        String passconfirm = confirmpasstxt.getText().toString();
        String confrmpass = passwordtxt.getText().toString();
        if (passconfirm.isEmpty()) {
            confirmpasstxt.setError("Can't be Empty");
            confirmpasstxt.requestFocus();
            return false;

        } else {
            if (passconfirm.equals(confrmpass)) {
                return true;
            } else {
                confirmpasstxt.setError("Retype Password");
                return false;
            }

        }
    }

    private boolean validatePassword() {
        signUpBtn.setEnabled(true);
        String passInput = passwordtxt.getText().toString();
        if(passInput.isEmpty())
        {
            passwordtxt.setError("Can't be Empty");
            passwordtxt.requestFocus();
            return false;

        }
        else if(passInput.length()<6){
            passwordtxt.setError("Minimum 6 character required");
            passwordtxt.requestFocus();
            return false;
        }
        else{
            passwordtxt.setError(null);
            return true;
        }
    }

    private boolean validateEmailAddress() {
        signUpBtn.setEnabled(true);
        String eId = emailIdtxt.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (eId.isEmpty()) {
            emailIdtxt.setError("Can't be Empty");
            emailIdtxt.requestFocus();

            return false;
        } else {
            if (eId.matches(emailPattern)) {
                return true;
            } else {
                emailIdtxt.setError("Invalid Email Id");
                return false;
            }
        }
    }

    private boolean validateName() {
        signUpBtn.setEnabled(true);
        String inputName = fullNametxt.getText().toString();
        if(inputName.isEmpty())
        {
            fullNametxt.setError("Can't be Empty");
            fullNametxt.requestFocus();
            return false;
        }
        else{
            fullNametxt.setError(null);
            return true;
        }
    }
}
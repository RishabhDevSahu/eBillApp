package com.example.ebill;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    SharedPref sharedPref;
    TextView nameView,contactView,emailView;
    EditText emailText;
    Button updateBtn,logoutBtn;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        DBHelper dbHelper = new DBHelper(ProfileActivity.this);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.navigation_home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.navigation_sale:
                        startActivity(new Intent(getApplicationContext(), SaleActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.navigation_purchase:
                        startActivity(new Intent(getApplicationContext(), PurchaseActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.navigation_bill:
                        startActivity(new Intent(getApplicationContext(), BillActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.navigation_profile:
                        return true;
                }
                return false;
            }
        });



        nameView = findViewById(R.id.userNameView);
        contactView = findViewById(R.id.mobilenoView);
        emailView = findViewById(R.id.emailView);
        updateBtn = findViewById(R.id.updatebtn);
        logoutBtn = findViewById(R.id.logoutbtn);
        SharedPref sharedPref = new SharedPref(getApplicationContext());


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPref.setLoginInfo(false);
                Toast.makeText(getApplicationContext(), "Logout Successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ProfileActivity.this,UserLoginActivity.class));

            }
        });


        String name = nameView.getText().toString().trim();
        String contact = contactView.getText().toString().trim();
        String email = emailView.getText().toString().trim();
        Boolean emailID = dbHelper.getLogindata();

        if(emailID==true){
            String data = dbHelper.getName(name);
            nameView.setText(data);
            Log.d("Hii","RDS"+data);

            String data2 = dbHelper.getContact(contact);
            contactView.setText(data2);
            Log.d("Hii","RDS"+data2);

            String data3 = dbHelper.getEmailID(email);
            emailView.setText(data3);
            Log.d("Hii","RDS"+data3);
        }else{
            Toast.makeText(getApplicationContext(), "Data not show", Toast.LENGTH_SHORT).show();
        }






    }
}
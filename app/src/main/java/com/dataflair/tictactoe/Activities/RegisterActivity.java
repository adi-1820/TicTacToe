package com.dataflair.tictactoe.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dataflair.tictactoe.Helper.DBHelper;
import com.dataflair.tictactoe.R;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    final String DB_NAME = "tictactoe";
    final int DB_VERSION = 1;
    TextInputEditText mobile,name,email,age,pass,c_pass;
    TextView log_txt;
    Button reg_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        log_txt = findViewById(R.id.log_txt);
        mobile = findViewById(R.id.mobile);
        name = findViewById(R.id.name);
        email =findViewById(R.id.email);
        age = findViewById(R.id.age);
        pass = findViewById(R.id.pass);
        c_pass = findViewById(R.id.c_pass);
        reg_btn = findViewById(R.id.btn_reg);

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(age.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Something Went Wrong !!!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }

                if(valid_reg()){
                    // Insert Data into Database
                    DBHelper db = new DBHelper(getApplicationContext(), DB_NAME, null, DB_VERSION);
                    boolean ch = db.data_insert(mobile.getText().toString(), name.getText().toString(), email.getText().toString(), age.getText().toString(), pass.getText().toString());
                    if(ch){
                        Toast.makeText(RegisterActivity.this, "You are Registered !!!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Mobile Number Already Exist !!!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                }
            }
        });

        // For Login Link
        log_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Method for Validation
    private boolean valid_reg() {

        String str_pass = pass.getText().toString();
        String str_c_pass = c_pass.getText().toString();
        String str_email = email.getText().toString();
        int age_valid = Integer.parseInt(age.getText().toString());

        // Validation For registration Details
        if(mobile.length() != 10){
            mobile.setError("Mobile Number Length must be 10 ");
            Toast.makeText(this, "Mobile Number Length must be 10 ", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!(Patterns.EMAIL_ADDRESS.matcher(str_email).matches())){
            email.setError("Please Enter Valid Email");
            Toast.makeText(this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(age_valid <= 5){
            age.setError("Enter Valid Age (Greater than 5)");
            Toast.makeText(this, "Enter Valid Age (Greater than 5)", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(pass.length() != 8){
            pass.setError("Password Contain Must 8 Character ");
            Toast.makeText(this, "Password Contain Must 8 Character ", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(c_pass.length() != 8){
            c_pass.setError("Confirm Password Contain Must 8 Character ");
            Toast.makeText(this, "Confirm Password Contain Must 8 Character ", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!(str_pass.equals(str_c_pass))){
            pass.setError("Both Password Contain Must be Same ");
            c_pass.setError("Both Password Contain Must be Same ");
            Toast.makeText(this, "Both Password Contain Must be Same ", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }
}
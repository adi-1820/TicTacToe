package com.dataflair.tictactoe.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dataflair.tictactoe.Helper.DBHelper;
import com.dataflair.tictactoe.R;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    static final String SHARED_NAME = "shared_prefs";
    static final String KEY_MOBILE = "mobile";
    public static final String DB_NAME = "tictactoe";
    public static final int DB_VERSION = 1;
    TextInputEditText mobile,pass;
    TextView reg_txt;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        reg_txt = findViewById(R.id.reg_txt);
        mobile = findViewById(R.id.mobile);
        pass = findViewById(R.id.pass);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valid()){
                    DBHelper helper = new DBHelper(getApplicationContext(), DB_NAME,null, DB_VERSION);
                    boolean ch = helper.checkUser(mobile.getText().toString(), pass.getText().toString());

                    if(ch){
                        SharedPreferences preferences = getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(KEY_MOBILE, mobile.getText().toString());
                        editor.apply();

                        Toast.makeText(LoginActivity.this, "Logged In !!!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, UsersActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Please Enter Valid Details", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        // For Registration Link
        reg_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Method for Login Details Validation
    private boolean valid() {
        if(mobile.length() != 10){
            mobile.setError("Mobile Number Length must be 10 ");
            Toast.makeText(this, "Mobile Number Length must be 10 ", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(pass.length() != 8){
            pass.setError("Password Contain Must 8 Character ");
            Toast.makeText(this, "Password Contain Must 8 Character ", Toast.LENGTH_SHORT).show();
            return  false;
        }
        else{
            return true;
        }
    }
}
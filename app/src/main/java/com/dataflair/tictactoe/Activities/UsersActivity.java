package com.dataflair.tictactoe.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dataflair.tictactoe.Helper.DBHelper;
import com.dataflair.tictactoe.MainActivity;
import com.dataflair.tictactoe.R;

public class UsersActivity extends AppCompatActivity {

    static final String SHARED_NAME = "shared_prefs";
    static final String KEY_MOBILE = "mobile";
    Button singlePlayerBtn, multiPlayerBtn;
    TextView txt;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.i1){
            Intent intent = new Intent(UsersActivity.this, showAccount.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        txt = findViewById(R.id.txt);
        singlePlayerBtn = (Button) findViewById(R.id.SinglePlayerBtn);
        multiPlayerBtn = (Button) findViewById(R.id.MultiPlayerBtn);

        //Implementing OnclickListener and navigating to Single Player activity on button Click
        singlePlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SinglePlayerActivity.class);
                startActivity(intent);
            }
        });

        //Implementing OnclickListener and navigating to Main Activity on button Click
        multiPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences preferences = getSharedPreferences(SHARED_NAME, MODE_PRIVATE);
        String mobile = preferences.getString(KEY_MOBILE, "0");
        DBHelper helper = new DBHelper(getApplicationContext(), LoginActivity.DB_NAME, null, LoginActivity.DB_VERSION);
        String uname = helper.showName(mobile);
        txt.setText("Welcome " + uname);
    }
}
package com.dataflair.tictactoe.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.dataflair.tictactoe.Helper.DBHelper;
import com.dataflair.tictactoe.R;

public class showAccount extends AppCompatActivity {

    static final String SHARED_NAME = "shared_prefs";
    static final String KEY_MOBILE = "mobile";
    TextView text_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_account);

        text_info = findViewById(R.id.text_info);

        SharedPreferences preferences = getSharedPreferences(SHARED_NAME, MODE_PRIVATE);
        String mobile = preferences.getString(KEY_MOBILE, "0");
        ShowAcc acc = new ShowAcc(getApplicationContext(), LoginActivity.DB_NAME, null, LoginActivity.DB_VERSION);
        String[] info = acc.showDetails(mobile);
        text_info.setText("Mobile No. : " + info[0] +
                    "\n\nUsername : " + info[1] +
                    "\n\nEmail : " + info[2] +
                    "\n\nAge : " + info[3]);
    }
}

class ShowAcc extends DBHelper{

    public ShowAcc(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public String[] showDetails(String mobile){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TAB_NAME+" WHERE "+COL_MOB+"=?", new String[]{mobile} );
        c.moveToFirst();

        String mob = c.getString(c.getColumnIndex(COL_MOB));
        String name = c.getString(c.getColumnIndex(COL_NAME));
        String email = c.getString(c.getColumnIndex(COL_EMAIL));
        String age = c.getString(c.getColumnIndex(COL_AGE));

        String[] infos = {mob,name,email,age};

        return infos;
    }
}
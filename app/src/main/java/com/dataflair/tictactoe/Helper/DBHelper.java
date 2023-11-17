package com.dataflair.tictactoe.Helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dataflair.tictactoe.Activities.LoginActivity;
import com.dataflair.tictactoe.Activities.RegisterActivity;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TAB_NAME = "register";
//    private final String ID = "id";
    public static final String COL_MOB = "mobile";
    public static final String COL_NAME = "name";
    public static final String COL_EMAIL = "email";
    public static final String COL_AGE = "age";
    public static final String COL_PASS = "pass";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {

        String sql = "CREATE TABLE "+ TAB_NAME +
                "("+ COL_MOB + " VARCHAR(10) PRIMARY KEY," +
                COL_NAME + " VARCHAR(50)," +
                COL_EMAIL + " VARCHAR(65)," +
                COL_AGE + " VARCHAR(3)," +
                COL_PASS + " VARCHAR(8))";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TAB_NAME);
//        onCreate(db);
    }

    // Register new User
    public boolean data_insert(String mobile, String name, String email, String age, String pass){
        SQLiteDatabase db = getWritableDatabase();
        String[] cols = {COL_MOB};
        String select = COL_MOB+"=?";
        String[] selectArgs = {mobile};

        Cursor cursor = db.query(TAB_NAME, cols, select, selectArgs, null, null, null);

        if(cursor.getCount() == 0){
            ContentValues cv = new ContentValues();
            cv.put(COL_MOB, mobile);
            cv.put(COL_NAME, name);
            cv.put(COL_EMAIL, email);
            cv.put(COL_AGE, age);
            cv.put(COL_PASS, pass);

            db.insert(TAB_NAME, null, cv);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkUser(String mobile, String pass){
        SQLiteDatabase db = getWritableDatabase();
        String[] cols = {COL_MOB};
        String select = COL_MOB+"=? and "+COL_PASS+"=?";
        String[] selectArgs = {mobile, pass};

        Cursor cursor = db.query(TAB_NAME, cols, select, selectArgs, null, null, null);

        if(cursor.getCount() == 1){
            return true;
        }
        else {
            return false;
        }
    }

    public String showName(String mob){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TAB_NAME+" WHERE "+COL_MOB+"=?", new String[]{mob} );
        c.moveToFirst();
        return c.getString(c.getColumnIndex(COL_NAME));
    }
}
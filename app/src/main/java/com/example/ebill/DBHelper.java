package com.example.ebill;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME ="EbillRecords.db";
    public static final String TABLE_NAME ="User";
    public static final String COLUMN_SNO ="S. No.";
    public static final String COLUMN_MOBILENO ="Mobileno";
    public static final String COLUMN_EMAIL ="Emailid";
    public static final String COLUMN_PASSWORD ="Password";
    public static final String COLUMN_CONFIRMPASSWORD ="Confirm Password";
    public static final String COLUMN_NAME ="Name";

    public DBHelper(@Nullable Context context) {
        super(context, context.getPackageName(),null ,12);
    }

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DBHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table User"+"(SNo integer primary key, Name text,Mobileno int, Emailid text , Password text,Confirm_Password text,NewPassword text, Confirm_NewPassword text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS User");
        onCreate(sqLiteDatabase);
    }

    public boolean insertLoginData(String Email, String Password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("Emailid",COLUMN_EMAIL);
        contentValues.put("Password",Password);
       // db.insert(TABLE_NAME, null, contentValues);
        db.update(TABLE_NAME,contentValues, COLUMN_EMAIL +"=?", new String[]{Email});
        return true;
    }

    public boolean insertSignUpData(String COLUMN_NAME, String COLUMN_MOBILENO, String COLUMN_EMAIL, String COLUMN_PASSWORD, String COLUMN_CONFIRMPASSWORD)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name",COLUMN_NAME);
        contentValues.put("Mobileno",COLUMN_MOBILENO);
        contentValues.put("Emailid",COLUMN_EMAIL);
        contentValues.put("Password",COLUMN_PASSWORD);
        contentValues.put("Confirm_Password",COLUMN_CONFIRMPASSWORD);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean insertForgotPassData(String Email, String Password, String Confirm_Password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("Emailid",COLUMN_EMAIL);
        contentValues.put("NewPassword",Password);
        contentValues.put("Confirm_NewPassword",Confirm_Password);
//        db.insert(TABLE_NAME, null, contentValues);
        db.update(TABLE_NAME,contentValues, COLUMN_EMAIL +"=?", new String[]{Email});
        return true;
    }

    public Boolean getLogindata() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor rs = db.rawQuery("Select Emailid from User", null);
        if (rs.getCount() > 0)
            return true;
        else
            return false;

    }
    public Boolean checkEmailId(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor rs = db.rawQuery("Select * from User where Emailid = ?", new String[]{email});
        if (rs.getCount() > 0)
            return true;
        else
            return false;

    }

    public Boolean checkPassword(String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from User where Password = ?", new String[] {password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean updatePassword(String email,String password)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Password",password);
        int val=db.update(TABLE_NAME,values, COLUMN_EMAIL +"=?", new String[]{email});
        if(val>0)
        return true;
        else
        return false;
        //return db.update(TABLE2_NAME, values, COLUMN_PASSWORD + "=" + password, null)>0;
    }

    public String getName(String Name)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select Name from " +TABLE_NAME,null);
        if(c!=null && c.getCount()>0)
        {
            c.moveToFirst();
            do {
                Name = c.getString(0);
            } while (c.moveToNext());
        }return Name;
    }

    public String getContact(String MobileNo)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT Mobileno FROM User ", null);
        if(c!=null && c.getCount()>0)
        {
            c.moveToFirst();
            do {
                MobileNo = c.getString(0);
            } while (c.moveToNext());
        }return MobileNo;
    }
    public String getEmailID(String EmailID)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT Emailid FROM User ", null);
        if(c!=null && c.getCount()>0)
        {
            c.moveToFirst();
            do {
                EmailID = c.getString(0);
            } while (c.moveToNext());
        }return EmailID;
    }
}

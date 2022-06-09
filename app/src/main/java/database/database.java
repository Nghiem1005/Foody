package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import models.User;

public class database extends SQLiteOpenHelper {
    GetDatabase getData;

    public database(@Nullable Context context) {
        super(context, "Food.sqlite", null, 1);

    }

    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public void QueryDataImg(int idCH, int idMon, String description, String price, byte[] image, int typeQuery ){
        SQLiteDatabase database = getWritableDatabase();
        String sql;
        if(typeQuery == 1){
            sql = "INSERT INTO CTCuaHang VALUES (?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE CTCuaHang SET idCH = ?, idMon = ?, description = ?, price = ?, image = ? CTCuaHang VALUES (?, ?, ?, ?, ?)";
        }
    }

    public void updateUser(User user){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE CTCuaHang SET idCH = ?, idMon = ?, description = ?, price = ?, image = ? CTCuaHang VALUES (?, ?, ?, ?, ?)";

    }

    public SQLiteDatabase getWritdatabase(){
        SQLiteDatabase database = getWritableDatabase();
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

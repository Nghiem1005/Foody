package DAO;

import android.app.Activity;
import android.database.AbstractWindowedCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteBlobTooBigException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;

import androidx.annotation.RequiresApi;

import database.database;
import hcmute.nhom35.foody.MainActivity1;
import models.User;
import database.GetDatabase;

public class UserDAO {
    database data;
    User userModel;

    public UserDAO(database data) {
        this.data = data;
    }

    public void insert(User user) {
        data.QueryData("INSERT INTO User VALUES(null, '"+ user.getUserName() +"','"+ user.getPassword() + "','"  + user.getFullName() + "','"+ user.getPhone()+"','"+ user.getBirthday()+ "','"+ user.getEmail()+ "','"+ user.getRole()+"', null)");
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public User getUser(String userName, String password) {
        Cursor dataUSer = data.GetData("SELECT * FROM User where userName = '" + userName + "' and password ='"+ password + "' LIMIT 1");

        //dataUSer.moveToFirst();
        // Now reduce window size, so that no rows can fit
        CursorWindow cw = new CursorWindow("test", 20000000);
        AbstractWindowedCursor ac = (AbstractWindowedCursor) dataUSer;
        ac.setWindow(cw);
        try {
            ac.moveToFirst();
        } catch (SQLiteBlobTooBigException expected) {
        }
        if (ac.getCount() > 0){
            userModel = new User(ac.getInt(0), ac.getString(1), ac.getString(2), ac.getString(3), ac.getString(4), ac.getString(5), ac.getString(6), ac.getString(7), ac.getBlob(8));
        } else {
            userModel = null;
        }

        return userModel;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public User getUserById(int userId) {
        Cursor dataUSer = data.GetData("SELECT * FROM User where id ='" + userId + "' LIMIT 1");
        //dataUSer.moveToFirst();
        // Now reduce window size, so that no rows can fit
        CursorWindow cw = new CursorWindow("test", 20000000);
        AbstractWindowedCursor ac = (AbstractWindowedCursor) dataUSer;
        ac.setWindow(cw);
        try {
            ac.moveToFirst();
        } catch (SQLiteBlobTooBigException expected) {
        }
        userModel = new User(ac.getInt(0), ac.getString(1), ac.getString(2), ac.getString(3), ac.getString(4), ac.getString(5), ac.getString(6), ac.getString(7), ac.getBlob(8));
        return userModel;
    }

    public void updateUser(User user) {
        SQLiteDatabase databases = data.getWritdatabase();
        String sql;
        sql = "UPDATE User SET userName = ?,password =?,fullName =?,birthday =?,email = ?,phone= ?,role =?,img=? where id=?";
        SQLiteStatement statement = databases.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, user.getUserName());
        statement.bindString(2, user.getPassword());
        statement.bindString(3, user.getFullName());
        statement.bindString(4, user.getBirthday());
        statement.bindString(5, user.getEmail());
        statement.bindString(6, user.getPhone());
        statement.bindString(7, user.getRole());
        statement.bindBlob(8, user.getImg());
        statement.bindDouble(9, user.getId());
        statement.executeUpdateDelete();
    }
    public void insert(int idCH, int idMon, String description, String price, byte[] image ){
        SQLiteDatabase databases = data.getWritdatabase();
        String sql;
        sql = "INSERT INTO CTCuaHang VALUES (?, ?, ?, ?, ?)";
        SQLiteStatement statement = databases.compileStatement(sql);
        statement.clearBindings();

        statement.bindDouble(1, idCH);
        statement.bindDouble(2, idMon);
        statement.bindString(3, description);
        statement.bindString(4, price);
        statement.bindBlob(5, image);

        statement.executeInsert();
    }


    public boolean checkExistUser (String userName) {
        Cursor dataUser = data.GetData("SELECT * from User where userName ='" +  userName + "'");
        if (dataUser.getCount() > 0 ) return true;
        return false;
    }
}

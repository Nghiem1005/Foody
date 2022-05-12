package DAO;

import android.app.Activity;
import android.database.Cursor;

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
        data.QueryData("INSERT INTO User VALUES(null, '"+ user.getUserName() +"','"+ user.getPassword() + "','"  + user.getFullName() + "','"+ user.getPhone()+"','"+ user.getBirthday()+ "','"+ user.getEmail()+ "','"+ user.getRole()+"')");
    }

    public User getUser(String userName, String password) {
        Cursor dataUSer = data.GetData("SELECT * FROM User where userName = '" + userName + "' and password ='"+ password + "' LIMIT 1");

        dataUSer.moveToFirst();
        if (dataUSer.getCount() > 0){
            userModel = new User(dataUSer.getInt(0), dataUSer.getString(1), dataUSer.getString(2), dataUSer.getString(3), dataUSer.getString(4), dataUSer.getString(5), dataUSer.getString(6), dataUSer.getString(7));
        } else {
            userModel = null;
        }

        return userModel;
    }

    public User getUserById(int userId) {
        Cursor dataUSer = data.GetData("SELECT * FROM User where id ='" + userId + "'");
        dataUSer.moveToFirst();
        userModel = new User(dataUSer.getInt(0), dataUSer.getString(1), dataUSer.getString(2), dataUSer.getString(3), dataUSer.getString(4), dataUSer.getString(5), dataUSer.getString(6), dataUSer.getString(6));
        return userModel;
    }

    public void updateUser(User user) {
        data.QueryData("UPDATE User SET userName = '"+ user.getUserName() + "'" + ",password ='" + user.getPassword() + "'" + ",fullName ='" + user.getFullName() + "'" + ",birthday ='" + user.getBirthday() + "'" +
                ",email = '" + user.getEmail() + "'" + ",phone= '"+ user.getPhone() + "'" + ",role ='"+ user.getRole() + "'" + "where id="+ user.getId());
    }

    public boolean checkExistUser (String userName) {
        Cursor dataUser = data.GetData("SELECT * from User where userName ='" +  userName + "'");
        if (dataUser.getCount() > 0 ) return true;
        return false;
    }
}

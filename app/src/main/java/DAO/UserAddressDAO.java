package DAO;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import database.database;
import models.NhomMon;
import models.User;
import models.UserAddress;

public class UserAddressDAO {
    database data;
    UserAddress userAddressModel;

    public UserAddressDAO(database data) {
        this.data = data;
    }

    public void insert(UserAddress userAddress) {
        data.QueryData("INSERT INTO UserAddress VALUES(null, '"+ userAddress.getDescriptions() +"',"+ userAddress.getUserID() + ")");
    }

    /*public User getUser(String userName, String password) {
        Cursor dataUSer = data.GetData("SELECT * FROM User where userName = '" + userName + "' and password ='"+ password + "' LIMIT 1");

        dataUSer.moveToFirst();
        if (dataUSer.getCount() > 0){
            userModel = new User(dataUSer.getInt(0), dataUSer.getString(1), dataUSer.getString(2), dataUSer.getString(3), dataUSer.getString(4), dataUSer.getString(5), dataUSer.getString(6), dataUSer.getString(7));
        } else {
            userModel = null;
        }

        return userModel;
    }*/

    public List<UserAddress> getUserAddressById(int userId) {
        List<UserAddress> list = new ArrayList<>();
        Cursor dataUSer = data.GetData("SELECT * FROM UserAddress where userID =" + userId);
        while (dataUSer.moveToNext()){
            list.add(new UserAddress(dataUSer.getInt(0), dataUSer.getString(1), dataUSer.getInt(2)));
        }
        return list;
    }

    public void updateUserAddress(UserAddress userAddress) {
        data.QueryData("UPDATE UserAddress SET descriptions ='"+ userAddress.getDescriptions() + "' where id="+ userAddress.getId());
    }

    public boolean checkExistUser (String userName) {
        Cursor dataUser = data.GetData("SELECT * from User where userName ='" +  userName + "'");
        if (dataUser.getCount() > 0 ) return true;
        return false;
    }
}

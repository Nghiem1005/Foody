package DAO;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import database.database;
import models.CTCuaHang;
import models.CartDetail;
import models.User;
import models.UserAddress;

public class CartDetailDAO {
    database data;
    CartDetail cartDetail;

    public CartDetailDAO(database data) {
        this.data = data;
    }

    public void insert(CartDetail cartDetail) {
        data.QueryData("INSERT INTO CartDetail VALUES(null, "+ cartDetail.getIdCH() +","+ cartDetail.getIdMon() + ","  + cartDetail.getQuantity() + ")");
    }

    public List<CartDetail> getAllCart() {
        List<CartDetail> list = new ArrayList<>();
        Cursor dataUSer = data.GetData("SELECT * FROM CartDetail");
        while (dataUSer.moveToNext()){
            list.add(new CartDetail(dataUSer.getInt(0), dataUSer.getInt(1), dataUSer.getInt(2), dataUSer.getInt(3)));
        }
        return list;
    }

    public void clear(){
        data.QueryData("DELETE FROM CartDetail");
    }
}

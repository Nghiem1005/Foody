package DAO;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import database.database;
import models.HoaDon;
import models.NhomMon;
import models.User;

public class HoaDonDAO {
    database data;
    HoaDon hoaDon1;

    public HoaDonDAO(database data) {
        this.data = data;
    }

    public void insert(HoaDon hoaDon) {
        data.QueryData("INSERT INTO HoaDon VALUES(null, '"+ hoaDon.getDay() +"',"+ hoaDon.getTotal() + ","  + hoaDon.getUserID() + ")");
    }

    public List<HoaDon> getAllHoaDon() {
        List<HoaDon> list = new ArrayList<>();

        Cursor dataUSer = data.GetData("SELECT * FROM HoaDon");
        while (dataUSer.moveToNext()){
            list.add(new HoaDon(dataUSer.getInt(0), dataUSer.getString(1), dataUSer.getInt(2), dataUSer.getInt(3)));
        }
        return list;
    }

    public List<HoaDon> getAllHoaDonByIdUser(int id) {
        List<HoaDon> list = new ArrayList<>();

        Cursor dataUSer = data.GetData("SELECT * FROM HoaDon WHERE userID = " + id);
        while (dataUSer.moveToNext()){
            list.add(new HoaDon(dataUSer.getInt(0), dataUSer.getString(1), dataUSer.getInt(2), dataUSer.getInt(3)));
        }
        return list;
    }
}

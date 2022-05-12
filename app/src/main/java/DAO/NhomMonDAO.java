package DAO;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import database.database;
import models.CuaHang;
import models.NhomMon;

public class NhomMonDAO {
    database data;
    NhomMon nhomMon;

    public NhomMonDAO(database data) {
        this.data = data;
    }

    public List<NhomMon> getNhomMon() {
        List<NhomMon> list = new ArrayList<>();

        Cursor dataUSer = data.GetData("SELECT * FROM NhomMon");
        while (dataUSer.moveToNext()){
            list.add(new NhomMon(dataUSer.getInt(0), dataUSer.getString(1)));
        }
        return list;
    }
}

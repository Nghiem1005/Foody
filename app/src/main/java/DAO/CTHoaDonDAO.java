package DAO;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import database.database;
import models.CTCuaHang;
import models.CTHoaDon;
import models.User;

public class CTHoaDonDAO {
    database data;
    CTHoaDon ctHoaDon;

    public CTHoaDonDAO(database data) {
        this.data = data;
    }

    public void insert(CTHoaDon ctHoaDon) {
        data.QueryData("INSERT INTO CTHoaDon VALUES("+ ctHoaDon.getIdHD() +","+ ctHoaDon.getIdMon() + ","  + ctHoaDon.getResID() + ","  + ctHoaDon.getQuantity() + ")");
    }

    public List<CTHoaDon> getCTHDById(int id) {

        List<CTHoaDon> list = new ArrayList<>();
        Cursor dataUSer = data.GetData("SELECT * FROM CTHoaDon where idHD =" + id);
        while (dataUSer.moveToNext()){
            list.add(new CTHoaDon(dataUSer.getInt(0), dataUSer.getInt(1), dataUSer.getInt(2), dataUSer.getInt(3)));
        }


        return list;
    }

    public List<CTHoaDon> getCTHD(int id) {

        List<CTHoaDon> list = new ArrayList<>();
        Cursor dataUSer = data.GetData("SELECT * FROM CTHoaDon");
        while (dataUSer.moveToNext()){
            list.add(new CTHoaDon(dataUSer.getInt(0), dataUSer.getInt(1), dataUSer.getInt(2), dataUSer.getInt(3)));
        }


        return list;
    }
}

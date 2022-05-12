package DAO;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import database.database;
import models.CTCuaHang;
import models.CuaHang;
import models.User;

public class CuaHangDAO {
    database data;
    CuaHang cuaHang;

    public CuaHangDAO(database data) {
        this.data = data;
    }

    public void insert(CuaHang cuaHang) {
        data.QueryData("INSERT INTO CuaHang VALUES(null, '"+ cuaHang.getName() +"','"+ cuaHang.getAddress() + "','"  + cuaHang.getTimeOpen() + "','"+ cuaHang.getTimeClose()+"','"+ cuaHang.getIdUser()+"')");
    }

    public List<CuaHang> get7CuaHang() {
        List<CuaHang> list = new ArrayList<>();

        Cursor dataUSer = data.GetData("SELECT * FROM CuaHang LIMIT 7");
        while (dataUSer.moveToNext()){
            list.add(new CuaHang(dataUSer.getInt(0), dataUSer.getString(1), dataUSer.getString(2), dataUSer.getString(3), dataUSer.getString(4), dataUSer.getInt(5)));
        }
        return list;
    }

    /*public CuaHang getCuaHangBy() {
        List<CuaHang> list = new ArrayList<>();

        Cursor dataUSer = data.GetData("SELECT * FROM CuaHang LIMIT 7");
        while (dataUSer.moveToNext()){
            list.add(new CuaHang(dataUSer.getInt(0), dataUSer.getString(1), dataUSer.getString(2), dataUSer.getString(3), dataUSer.getString(4), dataUSer.getInt(5)));
        }
        return list;
    }*/

    public List<CuaHang> getAllCuaHang() {
        List<CuaHang> list = new ArrayList<>();

        Cursor dataUSer = data.GetData("SELECT * FROM CuaHang");
        while (dataUSer.moveToNext()){
            list.add(new CuaHang(dataUSer.getInt(0), dataUSer.getString(1), dataUSer.getString(2), dataUSer.getString(3), dataUSer.getString(4), dataUSer.getInt(5)));
        }
        return list;
    }

    public List<CuaHang> getCuaHangByType(int id) {
        List<CuaHang> list = new ArrayList<>();

        Cursor dataUSer = data.GetData("SELECT DISTINCT CuaHang.id, CuaHang.name, CuaHang.address, CuaHang.timeOpen, CuaHang.timeClose, CuaHang.idUser FROM CTCuaHang INNER JOIN MonAn ON MonAn.id = CTCuaHang.idMon INNER JOIN CuaHang ON CuaHang.id = CTCuaHang.idCH WHERE MonAn.type = " + id);
        while (dataUSer.moveToNext()){
            list.add(new CuaHang(dataUSer.getInt(0), dataUSer.getString(1), dataUSer.getString(2), dataUSer.getString(3), dataUSer.getString(4), dataUSer.getInt(5)));
        }
        return list;
    }

}

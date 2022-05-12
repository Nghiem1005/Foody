package DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import database.database;
import models.CTCuaHang;
import models.CuaHang;
import models.NhomMon;
import models.User;

public class CTCuaHangDAO {

    database data;
    CTCuaHang ctCuaHangModel;

    public CTCuaHangDAO(database data) {
        this.data = data;
    }

    public List<CTCuaHang> getCuaHangByIdCuaHang(int idCH) {
        List<CTCuaHang> list = new ArrayList<>();

        Cursor dataUSer = data.GetData("SELECT * FROM CTCuaHang where idCH =" + idCH);
        while (dataUSer.moveToNext()){
            list.add(new CTCuaHang(dataUSer.getInt(0), dataUSer.getInt(1), dataUSer.getString(2), dataUSer.getString(3), dataUSer.getBlob(4)));
        }
        return list;
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

    public CTCuaHang getCTCuaHangByIdCuaHangMon(int idCH, int idMon){
        Cursor dataUSer = data.GetData("SELECT * FROM CTCuaHang where idCH =" + 1 + " and idMon = " + 3);
        dataUSer.moveToFirst();
        System.out.println("Đây là" + dataUSer.getCount());
        CTCuaHang ctCuaHang = new CTCuaHang(dataUSer.getInt(0), dataUSer.getInt(1), dataUSer.getString(2), dataUSer.getString(3), dataUSer.getBlob(4));
        return ctCuaHang;
    }

    public List<CTCuaHang> getAll() {
        List<CTCuaHang> list = new ArrayList<>();

        Cursor dataUSer = data.GetData("SELECT * FROM CTCuaHang");
        while (dataUSer.moveToNext()){
            list.add(new CTCuaHang(dataUSer.getInt(0), dataUSer.getInt(1), dataUSer.getString(2), dataUSer.getString(3), dataUSer.getBlob(4)));
        }
        return list;
    }
}

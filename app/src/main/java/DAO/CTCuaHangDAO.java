package DAO;

import android.database.AbstractWindowedCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteBlobTooBigException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;

import androidx.annotation.RequiresApi;

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

    @RequiresApi(api = Build.VERSION_CODES.P)
    public List<CTCuaHang> getCuaHangByIdCuaHang(int idCH) {
        List<CTCuaHang> list = new ArrayList<>();

        Cursor dataUSer = data.GetData("SELECT * FROM CTCuaHang where idCH =" + idCH);
        // Now reduce window size, so that no rows can fit
        CursorWindow cw = new CursorWindow("test", 30000000);
        AbstractWindowedCursor ac = (AbstractWindowedCursor) dataUSer;
        ac.setWindow(cw);
        try {
            while (ac.moveToNext()){
                list.add(new CTCuaHang(ac.getInt(0), ac.getInt(1), ac.getString(2), ac.getString(3), ac.getBlob(4)));
            }
        } catch (SQLiteBlobTooBigException expected) {
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

    @RequiresApi(api = Build.VERSION_CODES.P)
    public CTCuaHang getCTCuaHangByIdCuaHangMon(int idCH, int idMon){
        Cursor dataUSer = data.GetData("SELECT * FROM CTCuaHang where idCH =" + idCH + " and idMon = " + idMon);
        // Now reduce window size, so that no rows can fit
        CursorWindow cw = new CursorWindow("test", 25000000);
        AbstractWindowedCursor ac = (AbstractWindowedCursor) dataUSer;
        ac.setWindow(cw);
        try {
            ac.moveToFirst();
        } catch (SQLiteBlobTooBigException expected) {
        }


        CTCuaHang ctCuaHang = new CTCuaHang(ac.getInt(0), ac.getInt(1), ac.getString(2), ac.getString(3), ac.getBlob(4));
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

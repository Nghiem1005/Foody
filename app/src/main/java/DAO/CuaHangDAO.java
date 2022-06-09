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
import models.User;

public class CuaHangDAO {
    database data;
    CuaHang cuaHang;

    public CuaHangDAO(database data) {
        this.data = data;
    }



    public void insert(CuaHang cuaHang){
        SQLiteDatabase databases = data.getWritdatabase();
        String sql;
        sql = "INSERT INTO CuaHang VALUES (?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = databases.compileStatement(sql);
        statement.clearBindings();

        statement.bindDouble(1, cuaHang.getId());
        statement.bindString(2, cuaHang.getName());
        statement.bindString(3, cuaHang.getAddress());
        statement.bindString(4, cuaHang.getTimeOpen());
        statement.bindString(5, cuaHang.getTimeClose());
        statement.bindDouble(6, cuaHang.getIdUser());
        if(cuaHang.getImg() != null){
            statement.bindBlob(7, cuaHang.getImg());
        }


        statement.executeInsert();
    }

    public void update(CuaHang cuaHang){
        SQLiteDatabase databases = data.getWritdatabase();
        String sql;

        sql = "UPDATE CuaHang SET name = ?,address =?,timeOpen =?,timeClose =?,idUser = ?,img=? where id=?";
        SQLiteStatement statement = databases.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, cuaHang.getName());
        statement.bindString(2, cuaHang.getAddress());
        statement.bindString(3, cuaHang.getTimeOpen());
        statement.bindString(4, cuaHang.getTimeClose());
        statement.bindDouble(5, cuaHang.getIdUser());
        statement.bindDouble(7, cuaHang.getId());
        if(cuaHang.getImg() != null){
            statement.bindBlob(6, cuaHang.getImg());
        }

        statement.executeUpdateDelete();
    }

    public List<CuaHang> get7CuaHang() {
        List<CuaHang> list = new ArrayList<>();

        Cursor dataUSer = data.GetData("SELECT * FROM CuaHang LIMIT 7");
        while (dataUSer.moveToNext()){
            list.add(new CuaHang(dataUSer.getInt(0), dataUSer.getString(1), dataUSer.getString(2), dataUSer.getString(3), dataUSer.getString(4), dataUSer.getInt(5), dataUSer.getBlob(6)));
        }
        return list;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public CuaHang getCuaHangByIdUser(int id) {
        CuaHang cuaHang = null;

        Cursor dataUSer = data.GetData("SELECT * FROM CuaHang WHERE idUser = " + id);
        // Now reduce window size, so that no rows can fit
        CursorWindow cw = new CursorWindow("test", 30000000);
        AbstractWindowedCursor ac = (AbstractWindowedCursor) dataUSer;
        ac.setWindow(cw);
        try {
            if(ac.getCount() == 0){
                cuaHang = null;
            }else {
                ac.moveToFirst();
                cuaHang = new CuaHang(ac.getInt(0), ac.getString(1), ac.getString(2), ac.getString(3), ac.getString(4), ac.getInt(5), ac.getBlob(6));
            }
        } catch (SQLiteBlobTooBigException expected) {
        }

        return cuaHang;
    }

    public CuaHang getCuaHangById(int id) {
        CuaHang cuaHang;

        Cursor dataUSer = data.GetData("SELECT * FROM CuaHang WHERE id = " + id);
        if(dataUSer.getCount() == 0){
            cuaHang = null;
        }else {
            dataUSer.moveToFirst();
            cuaHang = new CuaHang(dataUSer.getInt(0), dataUSer.getString(1), dataUSer.getString(2), dataUSer.getString(3), dataUSer.getString(4), dataUSer.getInt(5), dataUSer.getBlob(6));
        }

        return cuaHang;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public List<CuaHang> getAllCuaHang() {
        List<CuaHang> list = new ArrayList<>();

        Cursor dataUSer = data.GetData("SELECT * FROM CuaHang");
        // Now reduce window size, so that no rows can fit
        CursorWindow cw = new CursorWindow("test", 30000000);
        AbstractWindowedCursor ac = (AbstractWindowedCursor) dataUSer;
        ac.setWindow(cw);
        try {
            while (ac.moveToNext()){
                list.add(new CuaHang(ac.getInt(0), ac.getString(1), ac.getString(2), ac.getString(3), ac.getString(4), ac.getInt(5), ac.getBlob(6)));
            }
        } catch (SQLiteBlobTooBigException expected) {
        }

        return list;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public List<CuaHang> getCuaHangByType(int id) {
        List<CuaHang> list = new ArrayList<>();

        Cursor dataUSer = data.GetData("SELECT DISTINCT CuaHang.id, CuaHang.name, CuaHang.address, CuaHang.timeOpen, CuaHang.timeClose, CuaHang.idUser, CuaHang.img FROM CTCuaHang INNER JOIN MonAn ON MonAn.id = CTCuaHang.idMon INNER JOIN CuaHang ON CuaHang.id = CTCuaHang.idCH WHERE MonAn.type = " + id);
        // Now reduce window size, so that no rows can fit
        CursorWindow cw = new CursorWindow("test", 30000000);
        AbstractWindowedCursor ac = (AbstractWindowedCursor) dataUSer;
        ac.setWindow(cw);
        try {
            while (ac.moveToNext()){
                list.add(new CuaHang(ac.getInt(0), ac.getString(1), ac.getString(2), ac.getString(3), ac.getString(4), ac.getInt(5), ac.getBlob(6)));
            }
        } catch (SQLiteBlobTooBigException expected) {
        }

        return list;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public List<CuaHang> getCuaHangByNameMon(String name) {
        List<CuaHang> list = new ArrayList<>();

        Cursor dataUSer = data.GetData("SELECT DISTINCT CuaHang.id, CuaHang.name, CuaHang.address, CuaHang.timeOpen, CuaHang.timeClose, CuaHang.idUser, CuaHang.img FROM CTCuaHang INNER JOIN MonAn ON MonAn.id = CTCuaHang.idMon INNER JOIN CuaHang ON CuaHang.id = CTCuaHang.idCH WHERE MonAn.name LIKE '%" + name + "%'");
        // Now reduce window size, so that no rows can fit
        CursorWindow cw = new CursorWindow("test", 30000000);
        AbstractWindowedCursor ac = (AbstractWindowedCursor) dataUSer;
        ac.setWindow(cw);
        try {
            while (ac.moveToNext()){
                list.add(new CuaHang(ac.getInt(0), ac.getString(1), ac.getString(2), ac.getString(3), ac.getString(4), ac.getInt(5), ac.getBlob(6)));
            }
        } catch (SQLiteBlobTooBigException expected) {
        }

        return list;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public CuaHang getCuaHangByUserId(int id){
        CuaHang cuaHang = null;

        Cursor dataUSer = data.GetData("SELECT * FROM CuaHang WHERE idUser = " + id);
        // Now reduce window size, so that no rows can fit
        CursorWindow cw = new CursorWindow("test", 30000000);
        AbstractWindowedCursor ac = (AbstractWindowedCursor) dataUSer;
        ac.setWindow(cw);
        try {
            if(ac.getCount() == 0){
                cuaHang = null;
            }else {
                ac.moveToFirst();
                cuaHang = new CuaHang(ac.getInt(0), ac.getString(1), ac.getString(2), ac.getString(3), ac.getString(4), ac.getInt(5), ac.getBlob(6));
            }
        } catch (SQLiteBlobTooBigException expected) {
        }


        return cuaHang;
    }

}

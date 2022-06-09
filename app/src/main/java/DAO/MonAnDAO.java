package DAO;

import android.database.Cursor;

import database.database;
import models.CTCuaHang;
import models.HoaDon;
import models.MonAn;

public class MonAnDAO {
    database data;
    MonAn monAnModel;

    public MonAnDAO(database data) {
        this.data = data;
    }

    public MonAn getMonAnByIdMonAn(int id) {
        Cursor dataUSer = data.GetData("SELECT * FROM MonAn where id ='" + id + "'");
        dataUSer.moveToFirst();
        monAnModel = new MonAn(dataUSer.getInt(0), dataUSer.getString(1), dataUSer.getInt(2));
        return monAnModel;
    }

    public MonAn getMonAnByMonAn(String name) {
        Cursor dataUSer = data.GetData("SELECT * FROM MonAn where name ='" + name + "'");
        if(dataUSer.getCount() == 0){
            monAnModel = null;
        } else {
            dataUSer.moveToFirst();
            monAnModel = new MonAn(dataUSer.getInt(0), dataUSer.getString(1), dataUSer.getInt(2));
        }
        return monAnModel;
    }

    public void insert(MonAn monAn) {
        data.QueryData("INSERT INTO MonAn VALUES(null, '"+ monAn.getName() +"','"+ monAn.getType() + "')");
    }
}

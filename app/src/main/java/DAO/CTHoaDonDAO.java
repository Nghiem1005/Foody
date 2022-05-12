package DAO;

import database.database;
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
}

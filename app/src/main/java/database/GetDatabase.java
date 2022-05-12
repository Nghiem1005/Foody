package database;


import android.app.Activity;

import hcmute.nhom35.foody.MainActivity;

public class GetDatabase {

    public void createDatabase (database data) {


        //tao bang user
        data.QueryData("CREATE TABLE IF NOT EXISTS User(id INTEGER PRIMARY KEY AUTOINCREMENT, userName NVARCHAR(290),password VARCHAR(290), fullName NVARCHAR(290), phone NVARCHAR(290), birthday VARCHAR(290), email VARCHAR(290), role VARCHAR(290))");

        //tao bang user address
        data.QueryData("CREATE TABLE IF NOT EXISTS UserAddress(id INTEGER PRIMARY KEY AUTOINCREMENT, descriptions NVARCHAR(290),userID INTEGER, CONSTRAINT lien_ket_01 FOREIGN KEY (userID) REFERENCES User(id))");

        //tao bang cuaHang
        data.QueryData("CREATE TABLE IF NOT EXISTS CuaHang(id INTEGER PRIMARY KEY AUTOINCREMENT, name NVARCHAR(290), address NVARCHAR(290), timeOpen NVARCHAR(290), timeClose NVARCHAR(290), idUser INTEGER, CONSTRAINT lien_ket_02 FOREIGN KEY (idUser) REFERENCES User(id))");

        //tao bang hoaDon
        data.QueryData("CREATE TABLE IF NOT EXISTS HoaDon(id INTEGER PRIMARY KEY AUTOINCREMENT, day NVARCHAR(290), total INTEGER, userID INTEGER, CONSTRAINT lien_ket_03 FOREIGN KEY (userID) REFERENCES User(id))");

        //tao bang NhomMon
        data.QueryData("CREATE TABLE IF NOT EXISTS NhomMon(id INTEGER PRIMARY KEY AUTOINCREMENT, name NVARCHAR(290))");

        //tao bang MonAn
        data.QueryData("CREATE TABLE IF NOT EXISTS MonAn(id INTEGER PRIMARY KEY AUTOINCREMENT, name NVARCHAR(290), type INTEGER, CONSTRAINT lien_ket_05 FOREIGN KEY (type) REFERENCES NhomMon(id))");

        //tao bang CTHoaDon
        data.QueryData("CREATE TABLE IF NOT EXISTS CTHoaDon(idHD INTEGER, idMon INTEGER, resID INTEGER, quantity INTEGER,  PRIMARY KEY(idHD, idMon, resID), CONSTRAINT lien_ket_06 FOREIGN KEY (idHD) REFERENCES HoaDon(id), CONSTRAINT lien_ket_07 FOREIGN KEY (idMon) REFERENCES MonAn(id) , CONSTRAINT lien_ket_04 FOREIGN KEY (resID) REFERENCES CuaHang(id))");

        //tao bang CTCuaHang
        data.QueryData("CREATE TABLE IF NOT EXISTS CTCuaHang(idCH INTEGER, idMon INTEGER, description NVARCHAR(290), price NVARCHAR(290), image BLOB, PRIMARY KEY(idCH, idMon), CONSTRAINT lien_ket_08 FOREIGN KEY (idCH) REFERENCES CuaHang(id), CONSTRAINT lien_ket_09 FOREIGN KEY (idMon) REFERENCES MonAn(id))");

        //tao bang cart detail
        data.QueryData("CREATE TABLE IF NOT EXISTS CartDetail(id INTEGER PRIMARY KEY AUTOINCREMENT, idCH INTEGER, idMon INTEGER, quantity INTEGER, CONSTRAINT lien_ket_10 FOREIGN KEY (idCH) REFERENCES CuaHang(id), CONSTRAINT lien_ket_11 FOREIGN KEY (idMon) REFERENCES MonAn(id))");
    }

    public void addData(database data) {

        /*data.QueryData("INSERT INTO User VALUES(null, 'nghiem', '123', 'nguyen phuc nghiem', '09678293812', '10/05/2001', 'nghiem@gmail.com', 'User')");

        data.QueryData("INSERT INTO CuaHang VALUES(null, 'Nhà hàng Bà Tám', '370 Hoàng Diệu', '10:00', '20:00', '1')");
        data.QueryData("INSERT INTO CuaHang VALUES(null, 'Quán kem Hữu Tín', '80 Phan Văn Trị', '10:00', '20:00', '1')");
        data.QueryData("INSERT INTO CuaHang VALUES(null, 'Quán cơm Cơm Tấm', '12/3 Khổng Tử', '10:00', '20:00', '1')");
        data.QueryData("INSERT INTO CuaHang VALUES(null, 'Quán cơm An Nhiên', '370 Dương Quảng Hàm', '10:00', '20:00', '1')");
        data.QueryData("INSERT INTO CuaHang VALUES(null, 'Quán Kem Bá Cháy', '372 Dương Quảng Hàm', '10:00', '20:00', '1')");

        data.QueryData("INSERT INTO NhomMon VALUES(null, 'Ăn nhanh')");

        data.QueryData("INSERT INTO MonAn VALUES(null, 'Cơm cháy Tây Bắc', 1)");
        data.QueryData("INSERT INTO MonAn VALUES(null, 'Cơm gà xối mỡ', 1)");
        data.QueryData("INSERT INTO MonAn VALUES(null, 'Cơm gà xối mỡ quận 12', 1)");
        data.QueryData("INSERT INTO MonAn VALUES(null, 'Cơm niêu ', 1)");
        data.QueryData("INSERT INTO MonAn VALUES(null, 'Cơm Chiên Dương Châu', 1)");
        data.QueryData("INSERT INTO MonAn VALUES(null, 'Kem Sầu Riêng 200 kí', 1)");
        data.QueryData("INSERT INTO MonAn VALUES(null, 'Kem kí ', 1)");
        data.QueryData("INSERT INTO MonAn VALUES(null, 'Sinh tố cà chua', 1)");

        data.QueryData("INSERT INTO CTCuaHang VALUES(1, 3, 'Món ngon miền Bắc', '10.000', NULL)");
        data.QueryData("INSERT INTO CTCuaHang VALUES(2, 4,'Món ngon miền Bắc', '10.000', NULL)");
        data.QueryData("INSERT INTO CTCuaHang VALUES(3, 1,'Cơm gà xối mỡ quận 12', '10.000', NULL)");
        data.QueryData("INSERT INTO CTCuaHang VALUES(4, 1,'Cơm niêu ', '10.000', NULL)");
        data.QueryData("INSERT INTO CTCuaHang VALUES(4, 5, 'Cơm Chiên Dương Châu', '10.000', NULL)");
        data.QueryData("INSERT INTO CTCuaHang VALUES(2, 6, 'Kem Sầu Riêng 200 kí', '10.000', NULL)");
        data.QueryData("INSERT INTO CTCuaHang VALUES(3, 7, 'Kem kí ', '10.000', NULL)");
        data.QueryData("INSERT INTO CTCuaHang VALUES(1, 8, 'Sinh tố cà chua', '10.000', NULL)");

        data.QueryData("INSERT INTO NhomMon VALUES(null, 'Ăn vặt')");
        data.QueryData("INSERT INTO NhomMon VALUES(null, 'Ăn khuya')");

        data.QueryData("INSERT INTO MonAn VALUES(null, 'Cơm Chiên Dương Châu', 2)");
        data.QueryData("INSERT INTO MonAn VALUES(null, 'Kem Sầu Riêng 200 kí', 3)");
        data.QueryData("INSERT INTO MonAn VALUES(null, 'Kem kí ', 3)");
        data.QueryData("INSERT INTO MonAn VALUES(null, 'Sinh tố cà chua', 2)");

        data.QueryData("INSERT INTO CTCuaHang VALUES(12, 25, 'Món ngon miền Bắc', '10.000', NULL)");
        data.QueryData("INSERT INTO CTCuaHang VALUES(13, 26,'Món ngon miền Bắc', '10.000', NULL");
        data.QueryData("INSERT INTO CTCuaHang VALUES(15, 26,'Cơm gà xối mỡ quận 12', '10.000', NULL)");
        data.QueryData("INSERT INTO CTCuaHang VALUES(14, 27,'Cơm niêu ', '10.000', NULL)");

        data.QueryData("INSERT INTO CTCuaHang VALUES(13, 26,'Món ngon miền Bắc', '10.000', NULL)");*/


        data.QueryData("INSERT INTO UserAddress VALUES(null, 'Kim Tây, Phước Hòa, Tuy Phước, Bình Định', 1)");
        data.QueryData("INSERT INTO UserAddress VALUES(null, 'Kim Tây, Phước Sơn, Tuy Phước, Bình Định', 1)");



    }
}

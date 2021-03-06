package database;


import android.app.Activity;

import hcmute.nhom35.foody.MainActivity;

public class GetDatabase {

    public void createDatabase (database data) {


        //tao bang user
        data.QueryData("CREATE TABLE IF NOT EXISTS User(id INTEGER PRIMARY KEY AUTOINCREMENT, userName NVARCHAR(90),password VARCHAR(90), fullName NVARCHAR(90), phone NVARCHAR(90), birthday VARCHAR(90), email VARCHAR(90), role VARCHAR(90), img BLOB)");

        //tao bang user address
        data.QueryData("CREATE TABLE IF NOT EXISTS UserAddress(id INTEGER PRIMARY KEY AUTOINCREMENT, descriptions NVARCHAR(290),userID INTEGER, CONSTRAINT lien_ket_01 FOREIGN KEY (userID) REFERENCES User(id))");

        //tao bang cuaHang
        data.QueryData("CREATE TABLE IF NOT EXISTS CuaHang(id INTEGER PRIMARY KEY AUTOINCREMENT, name NVARCHAR(290), address NVARCHAR(290), timeOpen NVARCHAR(290), timeClose NVARCHAR(290), idUser INTEGER, img BLOB, CONSTRAINT lien_ket_02 FOREIGN KEY (idUser) REFERENCES User(id))");

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

        /*data.QueryData("INSERT INTO User VALUES(null, 'nghiem', '123', 'nguyen phuc nghiem', '09678293812', '10/05/2001', 'nghiem@gmail.com', 'User', null)");

        data.QueryData("INSERT INTO CuaHang VALUES(null, 'Nh?? h??ng B?? T??m', '370 Ho??ng Di???u', '10:00', '20:00', '1', null)");
        data.QueryData("INSERT INTO CuaHang VALUES(null, 'Qu??n kem H???u T??n', '80 Phan V??n Tr???', '10:00', '20:00', '1', null)");
        data.QueryData("INSERT INTO CuaHang VALUES(null, 'Qu??n c??m C??m T???m', '12/3 Kh???ng T???', '10:00', '20:00', '1', null)");
        data.QueryData("INSERT INTO CuaHang VALUES(null, 'Qu??n c??m An Nhi??n', '370 D????ng Qu???ng H??m', '10:00', '20:00', '1', null)");
        data.QueryData("INSERT INTO CuaHang VALUES(null, 'Qu??n Kem B?? Ch??y', '372 D????ng Qu???ng H??m', '10:00', '20:00', '1', null)");

        data.QueryData("INSERT INTO NhomMon VALUES(null, '??n nhanh')");

        data.QueryData("INSERT INTO MonAn VALUES(null, 'C??m ch??y T??y B???c', 1)");
        data.QueryData("INSERT INTO MonAn VALUES(null, 'C??m g?? x???i m???', 1)");
        data.QueryData("INSERT INTO MonAn VALUES(null, 'C??m g?? x???i m??? qu???n 12', 1)");
        data.QueryData("INSERT INTO MonAn VALUES(null, 'C??m ni??u ', 1)");
        data.QueryData("INSERT INTO MonAn VALUES(null, 'C??m Chi??n D????ng Ch??u', 1)");
        data.QueryData("INSERT INTO MonAn VALUES(null, 'Kem S???u Ri??ng 200 k??', 1)");
        data.QueryData("INSERT INTO MonAn VALUES(null, 'Kem k?? ', 1)");
        data.QueryData("INSERT INTO MonAn VALUES(null, 'Sinh t??? c?? chua', 1)");

        data.QueryData("INSERT INTO CTCuaHang VALUES(1, 3, 'M??n ngon mi???n B???c', '10.000', NULL)");
        data.QueryData("INSERT INTO CTCuaHang VALUES(2, 4,'M??n ngon mi???n B???c', '10.000', NULL)");
        data.QueryData("INSERT INTO CTCuaHang VALUES(3, 1,'C??m g?? x???i m??? qu???n 12', '10.000', NULL)");
        data.QueryData("INSERT INTO CTCuaHang VALUES(4, 1,'C??m ni??u ', '10.000', NULL)");
        data.QueryData("INSERT INTO CTCuaHang VALUES(4, 5, 'C??m Chi??n D????ng Ch??u', '10.000', NULL)");
        data.QueryData("INSERT INTO CTCuaHang VALUES(2, 6, 'Kem S???u Ri??ng 200 k??', '10.000', NULL)");
        data.QueryData("INSERT INTO CTCuaHang VALUES(3, 7, 'Kem k?? ', '10.000', NULL)");
        data.QueryData("INSERT INTO CTCuaHang VALUES(1, 8, 'Sinh t??? c?? chua', '10.000', NULL)");

        data.QueryData("INSERT INTO NhomMon VALUES(null, '??n v???t')");
        data.QueryData("INSERT INTO NhomMon VALUES(null, '??n khuya')");

        data.QueryData("INSERT INTO MonAn VALUES(null, 'C??m Chi??n D????ng Ch??u', 2)");
        data.QueryData("INSERT INTO MonAn VALUES(null, 'Kem S???u Ri??ng 200 k??', 3)");
        data.QueryData("INSERT INTO MonAn VALUES(null, 'Kem k?? ', 3)");
        data.QueryData("INSERT INTO MonAn VALUES(null, 'Sinh t??? c?? chua', 2)");

        data.QueryData("INSERT INTO CTCuaHang VALUES(12, 25, 'M??n ngon mi???n B???c', '10.000', NULL)");
        data.QueryData("INSERT INTO CTCuaHang VALUES(13, 26,'M??n ngon mi???n B???c', '10.000', NULL");
        data.QueryData("INSERT INTO CTCuaHang VALUES(15, 26,'C??m g?? x???i m??? qu???n 12', '10.000', NULL)");
        data.QueryData("INSERT INTO CTCuaHang VALUES(14, 27,'C??m ni??u ', '10.000', NULL)");

        data.QueryData("INSERT INTO CTCuaHang VALUES(13, 26,'M??n ngon mi???n B???c', '10.000', NULL)");


        data.QueryData("INSERT INTO UserAddress VALUES(null, 'Kim T??y, Ph?????c H??a, Tuy Ph?????c, B??nh ?????nh', 1)");
        data.QueryData("INSERT INTO UserAddress VALUES(null, 'Kim T??y, Ph?????c S??n, Tuy Ph?????c, B??nh ?????nh', 1)");*/

        data.QueryData("INSERT INTO NhomMon VALUES(null, 'Ph???')");
        data.QueryData("INSERT INTO NhomMon VALUES(null, '??n nhanh')");
        data.QueryData("INSERT INTO NhomMon VALUES(null, 'Pizza')");
        data.QueryData("INSERT INTO NhomMon VALUES(null, 'Sushi')");
        data.QueryData("INSERT INTO NhomMon VALUES(null, 'G??')");
        data.QueryData("INSERT INTO NhomMon VALUES(null, 'Coffe')");

    }
}

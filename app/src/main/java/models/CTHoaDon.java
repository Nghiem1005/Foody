package models;

public class CTHoaDon {
    private int idHD;
    private int idMon;
    private int resID;
    private int quantity;

    public CTHoaDon(int idHD, int idMon, int resID, int quantity) {
        this.idHD = idHD;
        this.idMon = idMon;
        this.resID = resID;
        this.quantity = quantity;
    }

    public int getIdHD() {
        return idHD;
    }

    public void setIdHD(int idHD) {
        this.idHD = idHD;
    }

    public int getIdMon() {
        return idMon;
    }

    public void setIdMon(int idMon) {
        this.idMon = idMon;
    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

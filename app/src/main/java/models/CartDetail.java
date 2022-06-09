package models;

import java.io.Serializable;

public class CartDetail implements Serializable {
    private int id;
    private int idMon;
    private int idCH;
    private int Quantity;

    public CartDetail(int id, int idCH, int idMon, int quantity) {
        this.id = id;
        this.idCH = idCH;
        this.idMon = idMon;
        this.Quantity = quantity;
    }

    public CartDetail(int idCH, int idMon, int quantity) {
        this.idCH = idCH;
        this.idMon = idMon;
        this.Quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMon() {
        return idMon;
    }

    public void setIdMon(int idMon) {
        this.idMon = idMon;
    }

    public int getIdCH() {
        return idCH;
    }

    public void setIdCH(int idCH) {
        this.idCH = idCH;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}

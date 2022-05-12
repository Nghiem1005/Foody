package models;

public class CTCuaHang {
    private int idCH;
    private int idMon;
    private String description;
    private String price;
    private byte[] imgage;

    public CTCuaHang(int idCH, int idMon, String description, String price, byte[] imgage) {
        this.idCH = idCH;
        this.idMon = idMon;
        this.description = description;
        this.price = price;
        this.imgage = imgage;
    }

    public int getIdCH() {
        return idCH;
    }

    public void setIdCH(int idCH) {
        this.idCH = idCH;
    }

    public int getIdMon() {
        return idMon;
    }

    public void setIdMon(int idMon) {
        this.idMon = idMon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public byte[] getImgage() {
        return imgage;
    }

    public void setImgage(byte[] imgage) {
        this.imgage = imgage;
    }
}

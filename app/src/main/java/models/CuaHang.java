package models;

public class CuaHang {
    private int id;
    private String name;
    private String address;
    private String timeOpen;
    private String timeClose;
    private int idUser;

    public CuaHang(int id, String name, String address, String timeOpen, String timeClose, int idUser) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.timeOpen = timeOpen;
        this.timeClose = timeClose;
        this.idUser = idUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTimeOpen() {
        return timeOpen;
    }

    public void setTimeOpen(String timeOpen) {
        this.timeOpen = timeOpen;
    }

    public String getTimeClose() {
        return timeClose;
    }

    public void setTimeClose(String timeClose) {
        this.timeClose = timeClose;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}

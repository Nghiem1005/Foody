package models;

public class HoaDon {
    private int id;
    private String day;
    private int total;
    private int userID;

    public HoaDon(int id, String day, int total, int userID) {
        this.id = id;
        this.userID = userID;
        this.day = day;
        this.total = total;
    }

    public HoaDon() {
    }

    public HoaDon(String day, int total, int userID) {
        this.userID = userID;
        this.day = day;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

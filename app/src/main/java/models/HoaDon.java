package models;

public class HoaDon {
    private int id;
    private int userID;
    private String day;
    private int total;

    public HoaDon(int id, int userID, String day, int total) {
        this.id = id;
        this.userID = userID;
        this.day = day;
        this.total = total;
    }

    public HoaDon(int userID, String day, int total) {
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

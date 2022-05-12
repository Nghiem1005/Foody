package models;

public class UserAddress {
    private int id;
    private String descriptions;
    private int userID;

    public UserAddress(int id, String descriptions, int userID) {
        this.id = id;
        this.descriptions = descriptions;
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}

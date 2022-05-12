package food;

public class food {
    private int id;
    private String name;
    private String info;
    private int price;
    private int quantity;

    public food(int id, String name, String info, int price) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.price = price;
    }

    public food(int id, String name, String info, int price, int quantity) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.price = price;
        this.quantity = quantity;
    }

    public food(int id, String name, String info) {
        this.id = id;
        this.name = name;
        this.info = info;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }
}

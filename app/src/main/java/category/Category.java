package category;

public class Category {
    private int idCate;
    private String nameCate;

    public Category(int idCate, String nameCate) {
        this.idCate = idCate;
        this.nameCate = nameCate;
    }

    public int getIdCate() {
        return idCate;
    }

    public void setIdCate(int idCate) {
        this.idCate = idCate;
    }

    public String getNameCate() {
        return nameCate;
    }

    public void setNameCate(String nameCate) {
        this.nameCate = nameCate;
    }
}

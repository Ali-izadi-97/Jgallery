package model.Logic;

public class Type {
    private Integer id;
    private String name;


    @Override
    public String toString() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public Type(Integer id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }
}

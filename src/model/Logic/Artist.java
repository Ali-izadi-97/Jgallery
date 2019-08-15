package model.Logic;


public class Artist {
    public Integer ID;
    private String firstname;
    private String lastname;
    private Integer age;
    private String picturePath;

    @Override
    public String toString() {
        return getFirstname() + " " + getLastname();
    }

    public Artist(Integer ID, String firstname, String lastname, Integer age, String picturePath) {
        this.ID = ID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.picturePath = picturePath;
    }

    public void setID(Integer ID) {

        this.ID = ID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }


    public Integer getID() {
        return ID;
    }
}


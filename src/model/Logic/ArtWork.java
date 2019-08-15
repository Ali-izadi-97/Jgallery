package model.Logic;


public class ArtWork {
    private String name;
    private Artist artist;
    private Integer id;
    private String description;
    private Double price;
    private Type type_artwork;
    private Genre genre;
    private String Address;
    private Integer check;

//    public ArtWork(Integer id, Artist artist,String name , String description, Double price, Type type_artwork , Genre genre , String Address) {
//        this.name = name;
//        this.artist = artist;
//        this.description = description;
//        this.price = price;
//        this.type_artwork = type_artwork;
//        this.genre = genre;
//        this.Address = Address;
//        this.id = id;
//    }


    public ArtWork(Integer id, Artist artist, String name, String description, Double price, Type type_artwork, Genre genre, String Address, Integer check) {
        this.name = name;
        this.artist = artist;
        this.description = description;
        this.price = price;
        this.type_artwork = type_artwork;
        this.genre = genre;
        this.Address = Address;
        this.id = id;
        this.check = check;
    }

    //*****************************************************
    public Integer getCheck() {
        return check;
    }

    public void setCheck(Integer check) {
        this.check = check;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Integer getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return name;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Type getType_artwork() {
        return type_artwork;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setType_artwork(Type type_artwork) {
        this.type_artwork = type_artwork;
    }
}

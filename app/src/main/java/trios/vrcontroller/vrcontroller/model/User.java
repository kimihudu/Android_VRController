package trios.vrcontroller.vrcontroller.model;

/**
 * Created by kimiboo on 2017-09-20.
 */

public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private int geoInfo;

    public User(){}

    public User(String email, String password){

        this.email      = email;
        this.password   = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGeoInfo(int geoInfo) {
        this.geoInfo = geoInfo;
    }

    public int getGeoInfo() {
        return geoInfo;
    }
}
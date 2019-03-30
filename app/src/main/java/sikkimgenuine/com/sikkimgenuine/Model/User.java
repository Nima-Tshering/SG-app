package sikkimgenuine.com.sikkimgenuine.Model;

public class User {
    String name,number,password,address,image;
     public User()
    {

    }

    public User(String name, String number, String password, String address, String image) {

        this.name = name;
        this.number = number;
        this.password = password;
        this.address = address;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

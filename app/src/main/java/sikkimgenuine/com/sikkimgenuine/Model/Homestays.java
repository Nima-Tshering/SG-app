package sikkimgenuine.com.sikkimgenuine.Model;

public class Homestays {
   private String hid,name,address,description,amenities,destination,image,date,price,number,time,type;
  public Homestays()
   {}


    public Homestays(String hid, String name, String address, String description, String amenities, String destination, String image, String date, String price, String number, String time, String type) {

        this.hid = hid;
        this.name = name;
        this.address = address;
        this.description = description;
        this.amenities = amenities;
        this.destination = destination;
        this.image = image;
        this.date = date;
        this.price = price;
        this.number = number;
        this.time = time;
        this.type = type;
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}


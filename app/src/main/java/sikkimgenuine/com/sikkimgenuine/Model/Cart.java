package sikkimgenuine.com.sikkimgenuine.Model;

public class Cart {
    String hid,homestayname,homestayprice,time,date,discont,customernumber;
    public Cart()
    {

    }

    public Cart(String hid, String homestayname, String homestayprice, String time, String date, String discont, String customernumber) {
        this.hid = hid;
        this.homestayname = homestayname;
        this.homestayprice = homestayprice;
        this.time = time;
        this.date = date;
        this.discont = discont;
        this.customernumber = customernumber;
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getHomestayname() {
        return homestayname;
    }

    public void setHomestayname(String homestayname) {
        this.homestayname = homestayname;
    }

    public String getHomestayprice() {
        return homestayprice;
    }

    public void setHomestayprice(String homestayprice) {
        this.homestayprice = homestayprice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDiscont() {
        return discont;
    }

    public void setDiscont(String discont) {
        this.discont = discont;
    }

    public String getCustomernumber() {
        return customernumber;
    }

    public void setCustomernumber(String customernumber) {
        this.customernumber = customernumber;
    }
}

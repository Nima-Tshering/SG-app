package sikkimgenuine.com.sikkimgenuine.Model;

public class AdminOrders {

    private String date,time,arrivaldate,departuredate,email,name,phonenumber,state,totalamount;
    public AdminOrders()
    {

    }

    public AdminOrders(String date, String time, String arrivaldate, String departuredate, String email, String name, String phonenumber, String state, String totalamount) {
        this.date = date;
        this.time = time;
        this.arrivaldate = arrivaldate;
        this.departuredate = departuredate;
        this.email = email;
        this.name = name;
        this.phonenumber = phonenumber;
        this.state = state;
        this.totalamount = totalamount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getArrivaldate() {
        return arrivaldate;
    }

    public void setArrivaldate(String arrivaldate) {
        this.arrivaldate = arrivaldate;
    }

    public String getDeparturedate() {
        return departuredate;
    }

    public void setDeparturedate(String departuredate) {
        this.departuredate = departuredate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }
}



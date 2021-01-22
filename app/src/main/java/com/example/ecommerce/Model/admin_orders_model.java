package com.example.ecommerce.Model;

public class admin_orders_model {
    private String fname,lname, phone , address , city ,state , current_date, current_time, total_amount;

    public admin_orders_model(String name,String lname ,String phone, String address, String city, String state, String current_date, String current_time, String total_amount) {
        this.fname = name;
        this.lname = lname;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.current_date = current_date;
        this.current_time = current_time;
        this.total_amount = total_amount;
    }

    public admin_orders_model() {
    }

    public String getfname() {
        return fname;
    }

    public String getlname() {
        return lname;
    }

    public void setName(String name) {
        this.fname = name;

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }

    public String getCurrent_time() {
        return current_time;
    }

    public void setCurrent_time(String current_time) {
        this.current_time = current_time;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }
}

package com.example.ecommerce;

public class products {
    private String pname , description,price ,image,catoegory,pid,date,time;

    public products(String pname, String description, String price, String image, String catoegory, String pid, String date, String time) {
        this.pname = pname;
        this.description = description;
        this.price = price;
        this.image = image;
        this.catoegory = catoegory;
        this.pid = pid;
        this.date = date;
        this.time = time;
    }

    public products() {
    }

    public String getPname() {
        return pname;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getCatoegory() {
        return catoegory;
    }

    public String getPid() {
        return pid;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCatoegory(String catoegory) {
        this.catoegory = catoegory;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

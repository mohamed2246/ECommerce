package com.example.ecommerce.User;

public class cart_model {
    String pid;
    String pname;
    String price;
    String quantity;
    String discount;
    String image;

    public cart_model(String pid, String pname, String price, String quantity, String discount ,String image) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
        this.image = image;

    }

    public cart_model() {
    }

    public String getPid() {
        return pid;
    }

    public String getPname() {
        return pname;
    }
    public String getimage() {
        return image;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}

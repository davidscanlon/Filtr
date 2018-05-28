package com.bignerdranch.android.filtr;

public class User {

    public String userName, favDrink, favShop;

    public User () {

    }

    public User(String userName, String favDrink, String favShop) {
        this.userName = userName;
        this.favDrink = favDrink;
        this.favShop = favShop;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFavDrink() {
        return favDrink;
    }

    public void setFavDrink(String favDrink) {
        this.favDrink = favDrink;
    }

    public String getFavShop() {
        return favShop;
    }

    public void setFavShop(String favShop) {
        this.favShop = favShop;
    }
}

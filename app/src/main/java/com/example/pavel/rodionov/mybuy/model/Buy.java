package com.example.pavel.rodionov.mybuy.model;

public class Buy {
    private int id;
    private String goods = "";

    public Buy(){}

    public Buy(int id, String text) {
        this.id = id;
        this.goods = text;
    }

    public Buy(String text) {
        this.goods = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String text) {
        this.goods = text;
    }

    public String toString(){
        return (id + ". " + goods);
    }
}

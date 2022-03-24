package com.company.entity;

public class SelectedFood {
    private int quantity;
    private String food;

    public SelectedFood(int quantity, String food) {
        this.quantity = quantity;
        this.food = food;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    @Override
    public String toString() {
        return
                "*Soni* = " + quantity +"\n"+
                "*food* = " + food +"" + "\n" ;
    }
}

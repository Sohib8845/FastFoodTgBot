package com.company.entity;

public class Korzina {
    private String chatId;
    private SelectedFood selectedFood;

    public Korzina(String chatId, SelectedFood selectedFood) {
        this.chatId = chatId;
        this.selectedFood = selectedFood;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public SelectedFood getSelectedFood() {
        return selectedFood;
    }

    public void setSelectedFood(SelectedFood selectedFood) {
        this.selectedFood = selectedFood;
    }

    @Override
    public String toString() {
        return selectedFood+"" ;
    }
}

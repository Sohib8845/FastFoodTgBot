package com.company;

import com.company.controller.MenuController;
import com.company.controller.WelcomeController;
import com.company.entity.Korzina;
import com.company.entity.SelectedFood;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public interface Konteyner {
    WelcomeController welcomeController = new WelcomeController();
    MenuController menuController = new MenuController();
    HashMap<String, String> userCurrentMenu = new HashMap<>();
    HashMap<String, SelectedFood> countFood = new HashMap<>();
    List<String> deleteList = new LinkedList<>();
    List<Korzina> korzinaList=new LinkedList<>();
}

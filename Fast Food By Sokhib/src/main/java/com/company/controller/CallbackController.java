package com.company.controller;

import com.company.Konteyner;
import com.company.entity.Korzina;
import com.company.entity.SelectedFood;
import com.company.utils.InlineKeyboardUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class CallbackController implements Konteyner {

    public SendPhoto start(CallbackQuery callbackQuery) {
        String[] comandList = callbackQuery.getData().split("/");
        String comand = comandList[comandList.length - 1];
        String chatId=callbackQuery.getFrom().getId().toString();
        SelectedFood selectedFood=new SelectedFood(0,userCurrentMenu.get(chatId));
        countFood.put(chatId,selectedFood);
        return menuController.foodControle(callbackQuery, comand);
    }

    public EditMessageText editCount(CallbackQuery callbackQuery,String comand) {
        String chatId=callbackQuery.getFrom().getId().toString();
        String food=userCurrentMenu.get(chatId);
        SelectedFood selectedFood=countFood.get(chatId);
        selectedFood.setFood(food);
        int i=1;

        if(comand.equals("minus")) i=-1;


        selectedFood.setQuantity(selectedFood.getQuantity()+i);
        if(selectedFood.getQuantity()==-1)
            selectedFood.setQuantity(0);

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId);
        editMessageText.setText("Sonini tanlang");
        editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessageText.setReplyMarkup(countFoodButtons(chatId));

        return editMessageText;
    }



    public InlineKeyboardMarkup countFoodButtons(String chatId){
        SelectedFood selectedFood=countFood.get(chatId);
        List<InlineKeyboardButton> row1= InlineKeyboardUtil.row(InlineKeyboardUtil.button("","/food/count/minus",":heavy_minus_sign:"),
                InlineKeyboardUtil.button(String.valueOf(selectedFood.getQuantity()),"/food/count"),
                InlineKeyboardUtil.button("","/food/count/plus",":heavy_plus_sign:"));
        List<InlineKeyboardButton> row2=InlineKeyboardUtil.row(InlineKeyboardUtil.button("Back","/back","👈"));
        return InlineKeyboardUtil.keyboard(InlineKeyboardUtil.rowList(row1,row2));
    }

}

package com.company.controller;

import com.company.utils.KeyboardUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public class AdminPanel {
    private final String adminId="5029491737";

    public SendMessage start(){
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(adminId);
        sendMessage.setText("Welcome");
        sendMessage.setReplyMarkup(adminButtons());

        return sendMessage;
    }


    private ReplyKeyboardMarkup adminButtons(){
        KeyboardButton b1= KeyboardUtil.button("User Contacts","📱");
        KeyboardButton b2= KeyboardUtil.button("Order List","📜");
        KeyboardButton b3= KeyboardUtil.button("Excel File","📊");

        KeyboardRow row=KeyboardUtil.row(b1,b2);
        KeyboardRow row2=KeyboardUtil.row(b3);

        List<KeyboardRow> list=KeyboardUtil.rowList(row,row2);
        return KeyboardUtil.keyboard(list);
    }
}

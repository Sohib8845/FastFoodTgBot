package com.company.controller;

import com.company.entity.UserContacts;
import com.company.utils.KeyboardUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class WelcomeController {

    public SendMessage start(Message message){
        User user = message.getFrom();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(user.getId().toString());

        String responseText = "";
        responseText = "Welcome *" + user.getFirstName()+"*\n";
        sendMessage.setText(responseText);
        sendMessage.setReplyMarkup(getWelcomeButtons());
        sendMessage.setParseMode("Markdown");

     return sendMessage;
    }

    public SendMessage start2(Message message){
        User user = message.getFrom();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(user.getId().toString());

        if(message.getText()!=null && message.getText().contains("Yes"))
        {
            sendMessage.setText("Asosiy Menu");
            sendMessage.setReplyMarkup(getWelcomeButtons());
        }
        else {
            sendMessage.setText("Are you Confirm (Tasdiqlaysizmi ?)");
            sendMessage.setReplyMarkup(confirmMenu());
        }
        return sendMessage;

    }



    public ReplyKeyboardMarkup getWelcomeButtons(){
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setResizeKeyboard(true);
        keyboard.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        //first row
        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(KeyboardUtil.button("Food Menu","🍽"));

        //second row
        KeyboardRow secondRow = new KeyboardRow();
        secondRow.add(KeyboardUtil.button("Korzina","🛒"));

        KeyboardRow thirdRow = new KeyboardRow();
        thirdRow.add(KeyboardUtil.button("Baholang","✔"));
        thirdRow.add(KeyboardUtil.button("Sozlamalar","⚙"));


        keyboardRowList.add(firstRow);
        keyboardRowList.add(secondRow);
        keyboardRowList.add(thirdRow);
        keyboard.setKeyboard(keyboardRowList);

        return keyboard;
    }
    public ReplyKeyboardMarkup confirmMenu(){
        KeyboardButton button1= KeyboardUtil.button("No","🔒");

        KeyboardButton button2=KeyboardUtil.button("Yes","🔑");

        KeyboardButton button3=KeyboardUtil.button("Back",":arrow_left:");

        KeyboardRow keyboardRow1=KeyboardUtil.row(button1,button2);
        KeyboardRow keyboardRow2=KeyboardUtil.row(button3);
        List<KeyboardRow>keyboardRowList=KeyboardUtil.rowList(keyboardRow1,keyboardRow2);
        return KeyboardUtil.keyboard(keyboardRowList);
    }



    public ReplyKeyboardMarkup locationMenu(){
        KeyboardButton button1= KeyboardUtil.button("Send location", "📲");
        button1.setRequestLocation(true);
        KeyboardButton button2=KeyboardUtil.button("Send phone","📞");
        button2.setRequestContact(true);
        KeyboardRow keyboardRow1=KeyboardUtil.row(button1,button2);
        List<KeyboardRow>keyboardRowList=KeyboardUtil.rowList(keyboardRow1);

        return KeyboardUtil.keyboard(keyboardRowList);
    }
}

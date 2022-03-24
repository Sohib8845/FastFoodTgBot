package com.company.controller;

import com.company.utils.KeyboardUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public class ConfirmOrderController {

    public SendMessage confirmContact(String chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("*Send Your Contact*");
        sendMessage.setParseMode("Markdown");
        sendMessage.setReplyMarkup(locationMenu());
        return sendMessage;
    }



    public ReplyKeyboardMarkup locationMenu(){
        KeyboardButton button1= KeyboardUtil.button("Send location", "📲");
        button1.setRequestLocation(true);
        KeyboardButton button2=KeyboardUtil.button("Send phone","📞");
        button2.setRequestContact(true);
        KeyboardRow keyboardRow1=KeyboardUtil.row(button1,button2);
        List<KeyboardRow> keyboardRowList=KeyboardUtil.rowList(keyboardRow1);

        return KeyboardUtil.keyboard(keyboardRowList);
    }

}

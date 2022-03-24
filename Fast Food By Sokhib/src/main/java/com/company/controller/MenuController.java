package com.company.controller;

import com.company.Konteyner;
import com.company.utils.InlineKeyboardUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.File;
import java.util.List;

public class MenuController implements Konteyner {

    public SendMessage start(SendMessage sendMessage) {
        sendMessage.setText("Foods Menu");
        sendMessage.setReplyMarkup(getInlineMenuButtons());
        return sendMessage;
    }



    public SendPhoto foodControle(CallbackQuery callbackQuery,String food) {
        SendPhoto sendPhoto=new SendPhoto();
        String chatId=callbackQuery.getFrom().getId().toString();

        sendPhoto.setReplyMarkup(countFoodButtons());
        sendPhoto.setChatId(chatId);
        sendPhoto.setParseMode("Markdown");
        String path="";



        userCurrentMenu.put(chatId,food);

        switch (food){
            case "lavash":
                sendPhoto.setCaption("*Lavash Big 22.000 sum*");
                path="src/main/java/com/company/photos/lavashPhoto.jpg";
                break;
            case "set":
                sendPhoto.setCaption("*Set Mix 19.000 sum*");
                path="src/main/java/com/company/photos/setPhoto.jpg";
                break;
            case "burger":
                sendPhoto.setCaption("*Burger 18.000 sum*");
                path="src/main/java/com/company/photos/burgerPhoto.jpg";
                break;
            case "shaurma":
                sendPhoto.setCaption("*Shaurma 18.000 sum*");
                path="src/main/java/com/company/photos/shaurmaPhoto.jpg";
                break;
            case "donar":
                sendPhoto.setCaption("*Donar 18.000 sum*");
                path="src/main/java/com/company/photos/donarPhoto.jpg";
                break;
            case "xot-dog":
                sendPhoto.setCaption("*Xot-Dog 16.000 sum*");
                path="src/main/java/com/company/photos/xot-dogPhoto.jpg";
                break;
            case "napitok":
                sendPhoto.setCaption("*Pepsi 1,5L 14.000 sum*");
                path="src/main/java/com/company/photos/pepsiPhoto.jpg";
                break;
            case "garnir":
                sendPhoto.setCaption("*Fri  10.000 sum*");
                path="src/main/java/com/company/photos/friPhoto.jpg";
                break;
            case "desert":
                sendPhoto.setCaption("*Fri  10.000 sum*");
                path="src/main/java/com/company/photos/pirogPhoto.jpg";
                break;
        }

        InputFile inputFile = new InputFile(new File(path));
        sendPhoto.setPhoto(inputFile);
        return sendPhoto;
    }





    public InlineKeyboardMarkup getInlineMenuButtons(){
        List<InlineKeyboardButton> row1=InlineKeyboardUtil.row(InlineKeyboardUtil.button("Set","/food/set","🧆"),
                InlineKeyboardUtil.button("Lavash","/food/lavash","🌯"));
        List<InlineKeyboardButton> row2=InlineKeyboardUtil.row(InlineKeyboardUtil.button("Shaurma","/food/shaurma","🥙"),
                InlineKeyboardUtil.button("Donar","/food/donar","🍔"));
        List<InlineKeyboardButton> row3=InlineKeyboardUtil.row(InlineKeyboardUtil.button("Burger","/food/burger","🍔"),
                InlineKeyboardUtil.button("Xot-dog","/food/xot-dog","🌭"));
        List<InlineKeyboardButton> row4=InlineKeyboardUtil.row(InlineKeyboardUtil.button("Disert","/food/disert","🍩"),
                InlineKeyboardUtil.button("Napitok","/food/napitok","🍼"));
        List<InlineKeyboardButton> row5=InlineKeyboardUtil.row(InlineKeyboardUtil.button("Garnir","/food/garnir","🍶"));

        List<List<InlineKeyboardButton>> rowList=InlineKeyboardUtil.rowList(row1,row2,row3,row4,row5);

        return  InlineKeyboardUtil.keyboard(rowList);

    }



    public InlineKeyboardMarkup countFoodButtons(){
        List<InlineKeyboardButton> row2=InlineKeyboardUtil.row(InlineKeyboardUtil.button("Korzinaga","/korzina","🛒"));
        List<InlineKeyboardButton> row3=InlineKeyboardUtil.row(InlineKeyboardUtil.button("BACK","/back","👈"));

        return InlineKeyboardUtil.keyboard(InlineKeyboardUtil.rowList(row2,row3));
    }



}

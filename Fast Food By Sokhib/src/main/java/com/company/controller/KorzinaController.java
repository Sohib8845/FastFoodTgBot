package com.company.controller;

import com.company.Konteyner;
import com.company.entity.Korzina;
import com.company.entity.SelectedFood;
import com.company.utils.InlineKeyboardUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KorzinaController implements Konteyner {
    Map<String,String> getPrice;


    public KorzinaController() {
        getPrice =new HashMap<>();
        getPrice.put("lavash","22000");
        getPrice.put("set","19000");
        getPrice.put("burger","18000");
        getPrice.put("shaurma","18000");
        getPrice.put("donar","18000");
        getPrice.put("xot-dog","16000");
        getPrice.put("napitok","14000");
        getPrice.put("garnir","10000");
        getPrice.put("desert","10000");
    }

    public void add2Korzina(CallbackQuery callbackQuery){
        String chatId=callbackQuery.getFrom().getId().toString();
        Korzina korzina = new Korzina(chatId,countFood.get(chatId));
        korzinaList.add(korzina);
        System.out.println(korzinaList);
    }

    public SendMessage getMyKorzina(Message message){
        String chatId=message.getChatId().toString();
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(chatId);
        String response="";
        int price=0;
        System.out.println("KorzinaLIst:  "+korzinaList);
        for(Korzina k : korzinaList)
        {
            if(k.getChatId().equals(chatId)) {
                String p="";
                SelectedFood selectedFood=k.getSelectedFood();
                p="*Narxi*: "+ Integer.parseInt(getPrice.get(selectedFood.getFood()));
                price+=selectedFood.getQuantity()*
                        Integer.parseInt(getPrice.get(selectedFood.getFood()));
                response += k + p + "\n";
            }
        }

        sendMessage.setText("*Korzina*\n"+response+"" + "\n *Summa* : " +price+" sum");
        sendMessage.setParseMode("Markdown");


        List<InlineKeyboardButton> row1=
                InlineKeyboardUtil.row(InlineKeyboardUtil.button
                        ("Confirm","/confirm","✔"));
        List<InlineKeyboardButton> row2=
                InlineKeyboardUtil.row(InlineKeyboardUtil.button
                        ("Clear","/clearKorzina",":x:"));

        sendMessage.setReplyMarkup(InlineKeyboardUtil.keyboard(InlineKeyboardUtil.rowList(row1,row2)));

        return sendMessage;
    }










}

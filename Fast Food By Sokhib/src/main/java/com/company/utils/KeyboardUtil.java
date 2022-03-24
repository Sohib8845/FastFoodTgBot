package com.company.utils;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class KeyboardUtil {

    public static KeyboardButton button(String text){
        KeyboardButton keyboardButton=new KeyboardButton();
        keyboardButton.setText(text);
        return keyboardButton;
    }

    public static KeyboardButton button(String text,String emoji){
        String emojiText= EmojiParser.parseToUnicode(emoji+" "+text);
        KeyboardButton keyboardButton=new KeyboardButton();
        keyboardButton.setText(emojiText);
        return keyboardButton;
    }
    public static KeyboardRow row(KeyboardButton... keyboardButton){
        KeyboardRow keyboardRow=new KeyboardRow();
        keyboardRow.addAll(Arrays.asList(keyboardButton));
        return keyboardRow;

    }
    public static List<KeyboardRow> rowList(KeyboardRow... row){
        List<KeyboardRow>rowList=new LinkedList<>(Arrays.asList(row));
        return rowList;
    }
    public static ReplyKeyboardMarkup keyboard(List<KeyboardRow> keyboardRowList){
        ReplyKeyboardMarkup replyKeyboardMarkup=new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        replyKeyboardMarkup.setResizeKeyboard(true);//buttonni razmerini to'g'irlaydi
        replyKeyboardMarkup.setSelective(true);// bottinga strelka qoshadi;
        return replyKeyboardMarkup;
    }

}

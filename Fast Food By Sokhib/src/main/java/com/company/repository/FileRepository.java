package com.company.repository;

import com.company.entity.UserContacts;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class FileRepository {

    public void save(SendMessage sms) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("order.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            String s="";
            s=String.join("#",sms.getChatId(),sms.getText());
            printWriter.println(s+"@");

            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveContact(UserContacts userContacts) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("contacts.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            String s="";
            s=String.join("#",userContacts.getUserId().toString(),
                    userContacts.getContact().toString());
            printWriter.println(s+"@");

            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SendMessage getContacts() {
        FileReader fileReader = null;
        SendMessage sms = new SendMessage();
        sms.setChatId("5029491737");
        StringBuilder txt= new StringBuilder();
        try {
            fileReader = new FileReader("contacts.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            while (line != null) {
                line = bufferedReader.readLine();
                System.out.println("LINE="+line);
                txt.append(line).append("\n");
            }
            System.out.println("TEXT="+txt);
            bufferedReader.close();
            sms.setText(txt.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sms;
    }


    public SendMessage getList() {
        FileReader fileReader = null;
        SendMessage sms = new SendMessage();
        sms.setChatId("5029491737");
        StringBuilder txt= new StringBuilder();
        try {
            fileReader = new FileReader("order.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            while (line != null) {
                line = bufferedReader.readLine();
                txt.append(line).append("\n");

            }
            bufferedReader.close();
            sms.setText(txt.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sms;
    }

}

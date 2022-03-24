package com.company;

import com.company.controller.AdminPanel;
import com.company.controller.CallbackController;
import com.company.controller.ConfirmOrderController;
import com.company.controller.KorzinaController;
import com.company.entity.SelectedFood;
import com.company.entity.UserContacts;
import com.company.repository.ExcelRepository;
import com.company.repository.FileRepository;
import com.company.utils.InlineKeyboardUtil;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class MyTelegramBot extends TelegramLongPollingBot implements Konteyner {

    List<UserContacts> userContactList = new LinkedList<>();
    SendMessage message2Admin = null;
    FileRepository fileRepository=new FileRepository();
    ExcelRepository excelRepository=new ExcelRepository();
    ConfirmOrderController confirmOrderController=new ConfirmOrderController();
    KorzinaController korzinaController = new KorzinaController();
    AdminPanel adminPanel=new AdminPanel();
    String adminId="5029491737";


    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();

        if(update.hasMessage()){
            if (update.getMessage().getChatId().toString().equals(adminId)) {
                String text = update.getMessage().getText();
                if (update.getMessage().getText().equals("/start")) {
                    sendMsg(adminPanel.start());
                } else if (text.contains("User Contacts")) {
//                    sendMessage.setChatId(adminId);
                    sendMsg(fileRepository.getContacts());
                } else if (text.contains("Order List")) {
                    sendMessage.setChatId(adminId);
                    sendMessage.setText(fileRepository.getList().toString());
                    sendMsg(sendMessage);
                }
                else if (text.contains("Excel File")) {
                    sendMessage.setChatId(adminId);
                    excelRepository.write(userContactList);
//                    sendMessage.setText(excelRepository.read());
                    try {
                        File file1=new File("contacts.xlsx");
                        InputFile inputFile=new InputFile(file1);

                        SendDocument sendDocument = new SendDocument();
                        sendDocument.setDocument(inputFile);
                        sendDocument.setChatId(adminId);
                        sendMsg(sendDocument);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }else {
                Message message = update.getMessage();
                User user = message.getFrom();
                log(user.getFirstName(), user.getLastName(), user.getId(), update.getMessage().getText());

                sendMessage.setChatId(user.getId().toString());
                Contact contact = update.getMessage().getContact();
                Location location = update.getMessage().getLocation();

                if (message.getText() != null) {
                    if (message.getText().equals("/start"))
                        sendMsg(welcomeController.start(message));
                    else if (message.getText().contains("Yes")) {
                        sendMessage.setText("*"+user.getFirstName()+" buyurtmangiz qabul" +
                                " qilindi tez orada aloqaga chiqamiz!*");
                        sendMessage.setParseMode("Markdown");
                        sendMsg(sendMessage);
                        sendMsg(welcomeController.start2(message));
                        sendMsg2Admin(message2Admin, user.getId().toString());
                        System.out.println(fileRepository.getList());
                    } else if (message.getText().contains("Food Menu"))
                        sendMsg(menuController.start(sendMessage));
                    else if (message.getText().contains("Korzina")) {
//                    sendMsg(korzinaController.getMyKorzina(update.getMessage()));
                        message2Admin = korzinaController.getMyKorzina(update.getMessage());
                        sendMsg(message2Admin);
                    }
                    return;
                }

                if (contact != null || location != null) {
                    UserContacts userContacts = new UserContacts(user.getId(),
                            location, contact);
                    userContactList.add(userContacts);
                    System.out.println(userContactList);
                    sendMsg(welcomeController.start2(message));
                    fileRepository.saveContact(userContacts);
                }
            }

        }else if(update.hasCallbackQuery()){
            User user=update.getCallbackQuery().getFrom();
            log(user.getFirstName(), user.getLastName(), user.getId(), update.getCallbackQuery().getData());

            CallbackController callbackController = new CallbackController();
            CallbackQuery callbackQuery=update.getCallbackQuery();
            String chatId=update.getCallbackQuery().getFrom().getId().toString();
            String []comands=update.getCallbackQuery().getData().split("/");
            String end=comands[comands.length-1];
            if(end.equals("plus") || end.equals("minus"))
            {
                sendMsg(callbackController.editCount(callbackQuery,end));
            }
            else if(comands[1].equals("confirm")){
                sendMsg(confirmOrderController.confirmContact(chatId));
            }
            else if(comands[1].equals("korzina"))
            {
                korzinaController.add2Korzina(callbackQuery);
            }
            else if(update.getCallbackQuery().getData().endsWith("back"))
            {
                DeleteMessage deleteMessage=new DeleteMessage();
                deleteMessage.setChatId(chatId);
                deleteMessage.setMessageId(callbackQuery.getMessage().getMessageId());
                sendMsg(deleteMessage);
            }
            else if(comands[1].equals("food")){
                sendMsg(callbackController.start(callbackQuery));
                sendMsg(countFoodStart(chatId));
            }

        }
    }



    private void sendMsg2Admin(SendMessage msg,String chatId){
        String adminId="5029491737";
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(adminId);

        String userContact="";
        for(UserContacts u:userContactList)
        {
            if(u.getUserId().toString().equals(chatId))
                userContact=u.getContact().getPhoneNumber();
        }

        System.out.println("User Contact :"+userContact);
        sendMessage.setText(msg.getText()+"\n"+
                "*Client Contact*:"+userContact+"\n*Date*:"+LocalDateTime.now());
        System.out.println("Meesage to Admin : "+msg.getText());
        sendMessage.setParseMode("Markdown");
        fileRepository.save(sendMessage);
        sendMsg(sendMessage);
    }



    public InlineKeyboardMarkup countFoodButtons(String chatId){
        SelectedFood selectedFood=countFood.get(chatId);
        List<InlineKeyboardButton> row1= InlineKeyboardUtil.row(InlineKeyboardUtil.button("","/food/count/minus",":heavy_minus_sign:"),
                InlineKeyboardUtil.button(String.valueOf(selectedFood.getQuantity()),"/food/count"),
                InlineKeyboardUtil.button("","/food/count/plus",":heavy_plus_sign:"));
        return InlineKeyboardUtil.keyboard(InlineKeyboardUtil.rowList(row1));
    }


    public SendMessage countFoodStart(String chatId){
        SendMessage editMessageText = new SendMessage();
        editMessageText.setChatId(chatId);
        editMessageText.setText("Sonini tanlang");
        editMessageText.setReplyMarkup(countFoodButtons(chatId));
        return editMessageText;
    }

    public void sendMsg(SendMessage sendMessage){
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(SendDocument sendDocument){
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(SendPhoto sendPhoto){
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(EditMessageText editMessageText){
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(DeleteMessage deleteMessage){
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void log(String first_name, String last_name, Long user_id, String txt) {
        try {
            System.out.println("\n --------------------------------------------------------");
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            System.out.println(dateFormat.format(LocalDateTime.now()));
            System.out.println("Message from " + first_name + " " + last_name + ". (id = " + user_id + ") \n Text - " + txt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "@sokhibsayfullayev_bot";
    }

    @Override
    public String getBotToken() {
        return "1793253082:AAGW0U9SlDRcYLl8ce8juhgtD54frg2cvZw";
    }




}

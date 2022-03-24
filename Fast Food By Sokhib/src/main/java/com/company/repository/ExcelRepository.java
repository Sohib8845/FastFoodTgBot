package com.company.repository;



import com.company.entity.UserContacts;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.*;
import java.util.List;

public class ExcelRepository {
    public void write(List<UserContacts> userContactList) {
        try {
            Workbook workBook = new XSSFWorkbook();
            Sheet sheet = workBook.createSheet("contact");

            Cell cell = null;
            Row header = sheet.createRow(0);
            cell = header.createCell(0);
            cell.setCellValue("Id");


            cell = header.createCell(1);
            cell.setCellValue("Name");

            cell = header.createCell(2);
            cell.setCellValue("Contact");

            int rowCount = 1;

            for (UserContacts u : userContactList) {
                Row row = sheet.createRow(rowCount++);

                cell = row.createCell(0);
                cell.setCellValue(u.getUserId());

                cell = row.createCell(1);
                cell.setCellValue(u.getContact().getFirstName());

                cell = row.createCell(2);
                cell.setCellValue(u.getContact().getPhoneNumber());

            }

            File file = new File("contacts.xlsx");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            workBook.write(fileOutputStream);
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String read() {
        try {
            File file = new File("contacts.xlsx");
            FileInputStream fileInputStream = new FileInputStream(file);
            System.out.println("ssssssssss");
            Workbook workBook = new XSSFWorkbook(fileInputStream);
            StringBuilder txt= new StringBuilder();

            for (Sheet sh : workBook) {
                for (Row r : sh) {
                    for (Cell c : r) {
                    System.out.print(c);
                    txt.append(c);
                    }
                }
            }
            return txt.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

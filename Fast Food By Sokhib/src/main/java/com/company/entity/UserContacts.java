package com.company.entity;

import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Location;

public class UserContacts {
    private Long userId;
    private Contact contact;
    private Location location;


    public UserContacts() {
    }


    public UserContacts(Long userId, Location location, Contact contact) {
        this.userId = userId;
        this.location = location;
        this.contact = contact;
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User Contacts" +
                "userId=" + userId +
                ", contact=" + contact +
                ", location=" + location +
                '}';
    }
}

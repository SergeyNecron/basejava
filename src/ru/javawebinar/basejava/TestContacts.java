package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Contacts;

public class TestContacts {
    public static void main(String[] args) {

        for (Contacts type : Contacts.values()) {
            System.out.println(type.getTitle());
        }
    }

}
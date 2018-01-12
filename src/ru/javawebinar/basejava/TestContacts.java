package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.ContactType;

public class TestContacts {
    public static void main(String[] args) {

        for (ContactType type : ContactType.values()) {
            System.out.println(type.getTitle());
        }
    }

}
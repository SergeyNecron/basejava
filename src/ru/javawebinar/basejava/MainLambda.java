package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.List;

public class MainLambda {
    public static void main(String[] args) {
        List<String> list = new ArrayList();
        list.add("dsf1");
        list.add("dsf2");
        list.add("dsf3");
        list.add("");

        String name = "vasa";
        for (String text : list) {
            System.out.println(name + text);
        }

        list.forEach(text -> System.out.println(name + text));

        list.forEach(System.out::println);
        list.stream().forEach(System.out::println);
    }

}

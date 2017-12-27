package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Collections;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MainCollection {
    private static Resume[] resums = {new Resume("uuid1"), new Resume("uuid2"), new Resume("uuid3")};

    public static void main(String[] args) {
        Collection<Resume> collection = new ArrayList<>();

        //добавление всех резюме в коллекцию
        Collections.addAll(collection, resums);

        //вывод колллекции
        System.out.println(collection.toString());

        //удаление резюме с uuid1
        for (Resume r : resums) {
            if (r.getUuid().equals("uuid1"))
                collection.remove(r);
        }
        /*
        Iterator<Resume> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            System.out.println(r);
            if (Objects.equals(r.getUuid(), "uuid1")) {
                iterator.remove();
            }
        }*/

        System.out.println(collection.toString());

        Map<String, Resume> map = new HashMap<>();
        for (int i = 0; i < resums.length; i++) {
            map.put("uuid" + i, resums[i]);
        }
        // Bad!
        for (String uuid : map.keySet()) {
            System.out.println(map.get(uuid));
        }

        for (Map.Entry<String, Resume> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }

        List<Resume> resumes = Arrays.asList(resums[0], resums[1], resums[2]);
        resumes.remove(1);
        System.out.println(resumes);
    }
}

package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MainCollection {
    private static Resume[] resums = {new Resume("Вася"), new Resume("Настя"), new Resume("Сергей")};

    public static void main(String[] args) {
        Collection<Resume> collection = new ArrayList<>();

        //добавление всех резюме в коллекцию
        Collections.addAll(collection, resums);

        //вывод колллекции
        System.out.println(collection.toString());

        //удаление резюме с uuid1
        for (Resume r : resums) {
            if (r.getFullName().equals("Вася"))
                collection.remove(r);
        }

        System.out.println(collection.toString());

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < resums.length; i++) {
            map.put(resums[i].getUuid(), resums[i].getFullName());
        }
        // Bad!
        for (String uuid : map.keySet()) {
            System.out.println(map.get(uuid));
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }

        List<Resume> resumes = Arrays.asList(resums[0], resums[1], resums[2]);
//        resumes.remove(1);
        System.out.println(resumes);
    }
}

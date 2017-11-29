package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    protected String getIndex(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object index) {
        return map.containsKey(index);
    }

    @Override
    protected void save(Resume r, Object index) {
        map.put((String) index, r);
    }

    @Override
    protected void update(Resume r, Object index) {
        save(r, index); // для map облавление равнозначно сохранению
    }

    @Override
    protected void delete(Object index) {
        map.remove(index);
    }

    @Override
    protected Resume get(Object index) {
        return map.get(index);
    }

    @Override
    public void clear() {
        map.clear();size = 0;
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumes = new Resume[map.size()];
        int i = 0;
        for (Map.Entry<String, Resume> pair : map.entrySet()) {
            resumes[i]=pair.getValue();
            i++;
        }
        return resumes;
    }

    @Override
    public int size() {
        return map.size();
    }
}

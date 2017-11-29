package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> list = new ArrayList<>();

    @Override
    protected Integer getIndex(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (uuid.equals(list.get(i).getUuid())) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Object index) {
        return index != null;
    }

    @Override
    protected void save(Resume r, Object index) {
        list.add(r);
    }

    @Override
    protected void update(Resume r, Object index) {
        list.set((Integer) index, r);
    }

    @Override
    protected void delete(Object index) {
        list.remove(((Integer) index).intValue());
    }

    @Override
    protected Resume get(Object index) {
        return list.get((Integer) index);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public Resume get(String uuid) {
        return null;
    }


    @Override
    public Resume[] getAll() {
        return list.toArray(new Resume[list.size()]);
    }

    @Override
    public int size() {
        return list.size();
    }
}

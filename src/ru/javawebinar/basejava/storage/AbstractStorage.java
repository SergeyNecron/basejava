package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static ru.javawebinar.basejava.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractStorage implements Storage {

    protected abstract Object getIndex(String uuid);

    protected abstract boolean isExist(Object index);

    protected abstract void save(Resume r, Object index);

    protected abstract void update(Resume r, Object index);

    protected abstract void delete(Object index);

    protected abstract Resume get(Object index);

    protected int size = 0;

    @Override
    public void update(Resume r) {
        Object index = getIndex(r.getUuid());
        if (isExist(index)) update(r, index); // если резюме найден, то сохраняем
        else throw new NotExistStorageException(r.getUuid());
    }

    @Override
    public void save(Resume r) {
        if (size == STORAGE_LIMIT)
            throw new StorageException("Storage overflow", r.getUuid());
        else {
            Object index = getIndex(r.getUuid());
            if (!isExist(index))
                save(r, index); // если резюме не найден, то сохраняем
            else throw new ExistStorageException(r.getUuid());
            size++;
        }
    }

    @Override
    public void delete(String uuid) {
        Object index = getIndex(uuid);
        if (isExist(index))
            delete(index); // если резюме найден, то удаляем
        else throw new NotExistStorageException(uuid);
        size--;
    }

    @Override
    public Resume get(String uuid) {
        Object index = getIndex(uuid);
        if (isExist(index)) return get(index); // если резюме найден, то возвращаем
        else throw new NotExistStorageException(uuid);
    }
}

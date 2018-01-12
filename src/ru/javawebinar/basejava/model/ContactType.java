package ru.javawebinar.basejava.model;

public enum ContactType {
    PHONE("Телефон"),
    MOBILE("Мобильный"),
    HOME_PHONE("Домашний тел."),
    SKYPE("Скайп"),
    EMAIL("Почта"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STATCKOVERFLOW("Профиль Stackoverflow"),
    HOME_PAGE("Домашняя страница");
    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

package ru.job4j.ref;

public class User {
    private int id;
    private String name;

    /**
     * метод возвращает копию обьекта по имени
     * @param name
     * @return
     */
    public static User of(String name) {
        User user = new User();
        user.name = name;
        return user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

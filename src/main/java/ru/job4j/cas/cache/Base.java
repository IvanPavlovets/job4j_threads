package ru.job4j.cas.cache;

/**
 * Базовая модель данных, описывается двумя полями: id, version.
 * ID - уникальный идентификатор.
 * version - определяет достоверность версии в кеше
 * (для сверки актуальности данных в кеше) +1 после вызова update.
 * name - это поля бизнес модели.
 */
public class Base {
    private final int id;
    /**
     * поле должно увеличиваться на единицу каждый раз, когда модель изменили,
     * то есть вызвали метод update.
     */
    private int version;
    private String name;

    public Base(int id, int version) {
        this.id = id;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Base{"
                + "id=" + id
                + ", version=" + version
                + ", name='" + name + '\'' + '}';
    }
}

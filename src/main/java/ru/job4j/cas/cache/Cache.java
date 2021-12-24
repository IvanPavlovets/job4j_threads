package ru.job4j.cas.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Модель кеша с CRUD операциями.
 */
public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    /**
     * метод, внутри, выполняет сравнение и вставку атомарно.
     * @param model
     * @return boolean
     */
    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    /**
     * действие произойдет в том случае, если значение есть в memory.
     * передоваемая функция BiFunction (2 параметра передаем и 1 возвращаем):
     * 1) перед обновлением данных проверить поле version.
     * Если version у модели и в кеше одинаковы, то можно
     * обновить (версию и прочие даные).
     * Если version отличаются нужно выкинуть исключение.
     * key из memory.
     * value - model из memory.
     * @param model
     * @return boolean
     */
    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (key, value) -> {
                    if (model.getVersion() != value.getVersion()) {
                        throw new OptimisticException("Версия model, в кеше и параметре, не совподают!");
                    }
                    value.setVersion(value.getVersion() + 1);
                    value.setName(model.getName());
                    return value;
                }
        ) != null;
    }

    /**
     * метод, внутри, выполняет сравнение и вставку атомарно.
     * @param model
     * @return boolean
     */
    public boolean delete(Base model) {
        return memory.remove(model.getId(), model);
    }

    public Map<Integer, Base> getMemory() {
        return memory;
    }

    @Override
    public String toString() {
        return "Cache{"
                + "memory=" + memory + '}';
    }
}

package ru.job4j.search;

import java.util.ArrayList;
import java.util.List;

/**
 * Телефонный справочник на базе ArrayList.
 */
public class PhoneDictionary {
    private List<Person> persons = new ArrayList<>();

    public void add(Person person) {
        this.persons.add(person);
    }

    /**
     * Вернуть список всех пользователей, который содержат key в любых полях.
     *
     * @param key ключ поиска
     * @return список подошедших пользователей
     */
    public List<Person> find(String key) {
        List<Person> result = new ArrayList<>();
        for (Person person : this.persons) {
            if (this.contains(person, key)) {
                result.add(person);
            }
        }
        return result;
    }

    /**
     * Проверяяем наличие ключевого слова в полях пользователя.
     *
     * @param person пользователь
     * @param key ключевое слово
     * @return true, если ключ содержится хотя бы в одном из полей
     */
    private boolean contains(Person person, String key) {
        return person.getName().contains(key)
                || person.getSurname().contains(key)
                || person.getAddress().contains(key)
                || person.getPhone().contains(key);
    }


}

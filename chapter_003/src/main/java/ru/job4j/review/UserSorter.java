package ru.job4j.review;

import ru.job4j.sort.User;

import java.util.*;

/**
 * Provides sort values in input user list collection.
 */
public class UserSorter {
    /**
     * Transfer data from input user list collection into tree set collection.
     *
     * @param list user list
     * @return user treeset
     */
    Set<User> treeSet(List<User> list) {
        return new TreeSet<>(list);
    }

    /**
     * Input user list getting sorted by comparing username field's length.
     *
     * @param list user list
     * @return sorted user list
     */
    List<User> lengthSort(List<User> list) {
        list.sort(Comparator.comparingInt(user -> user.getName().length()));
        return list;
    }

    /**
     * Input user list getting sorted by comparing both name and age fields.
     *
     * @param list user list
     * @return sorted user list
     */
    List<User> deepSort(List<User> list) {
        list.sort(Comparator.comparing(User::getName).thenComparingInt(User::getAge));
        return list;
    }
}
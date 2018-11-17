package ru.job4j.generic;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Тестируем реализации AbstractStore.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-11-17
 */
public class AbstractStoreTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void testUserStore() {
        User first = new User("123");
        User second = new User("111");
        UserStore store = new UserStore();
        this.execute(store, first, second);
        this.addToEmptyStore(new UserStore(0), first);
    }

    @Test
    public void testRoleStore() {
        Role first = new Role("123");
        Role second = new Role("111");
        RoleStore store = new RoleStore();
        this.execute(store, first, second);
        this.addToEmptyStore(new RoleStore(0), first);

    }

    private <T extends Base> void execute(Store<T> store, T first, T second) {
        store.add(first);
        assertThat(store.delete(null), is(false));
        assertThat(store.findById(first.getId()), is(first));
        store.replace(first.getId(), second);
        assertNull(store.findById(first.getId()));
        assertThat(store.findById(second.getId()), is(second));
        store.delete(second.getId());
        assertNull(store.findById(second.getId()));
        assertThat(store.replace(first.getId(), second), is(false));
        assertThat(store.delete(first.getId()), is(false));
    }

    private <T extends Base> void addToEmptyStore(Store<T> store, T first) {
        this.thrown.expect(UnsupportedOperationException.class);
        store.add(first);
    }
}
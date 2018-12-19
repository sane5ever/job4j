package ru.job4j.storage;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Хранилище пользователей. Тестирование.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-17
 */
public class UserStorageTest {
    private final UserStorage storage = new UserStorage();
    private final UserStorage.User userA = new UserStorage.User(1, 100);
    private final UserStorage.User userB = new UserStorage.User(2, 300);

    @Before
    public void prepare() throws InterruptedException {
        Thread first = new MutatorThread(storage -> storage.add(userA));
        Thread second = new MutatorThread(storage -> storage.add(userB));
        first.start();
        second.start();
        first.join();
        second.join();
    }

    @Test
    public void whenUsersExistAndAmountCorrectThenShouldReturnTrue() {
        assertTrue(storage.transfer(1, 2, 50));
    }

    @Test
    public void whenDestUserNotExistThenShouldReturnFalse() {
        assertFalse(storage.transfer(1, 3, 50));
    }

    @Test
    public void whenSrcUserNotExistShouldReturnFalse() {
        assertFalse(storage.transfer(3, 2, 50));
    }

    @Test
    public void whenAmountIncorrectThenShouldReturnFalse() {
        assertFalse(storage.transfer(1, 2, 150));
    }

    @Test
    public void whenStorageIsFullThenAddReturnsFalse() {
        UserStorage storageSmall = new UserStorage(10);
        Stream.iterate(0, i -> i <= 10, i -> i + 1)
                .forEach(i -> storageSmall.add(new UserStorage.User(i, 0)));
        assertFalse(storageSmall.add(new UserStorage.User(11, 0)));

    }

    @Test
    public void whenAddingStartOrNotThenResultTrueOrFalse() throws InterruptedException {
        MutatorThread first = new MutatorThread(storage -> storage.add(new UserStorage.User(3, 500)));
        MutatorThread second = new MutatorThread(storage -> storage.add(new UserStorage.User(4, 1000)));
        first.start();
        first.join();
        assertTrue(first.result);
        assertFalse(second.result);
    }

    @Test
    public void whenDeleteUserTwiceThenOneReturnsTrueAndAnotherReturnsFalse() throws InterruptedException {
        MutatorThread first = new MutatorThread(storage -> storage.delete(userA));
        MutatorThread second = new MutatorThread(storage -> storage.delete(userA));
        first.start();
        second.start();
        first.join();
        second.join();
        assertTrue(first.result || second.result); // true || false or false || true == true
        assertFalse(first.result && second.result); // true && false or false && true == false
    }

    @Test
    public void whenUpdateExistedAndNotExistedUsersThenResultTrueAndFalse() throws InterruptedException {
        userA.amount = 500;
        MutatorThread first = new MutatorThread(storage -> storage.update(userA));
        MutatorThread second = new MutatorThread(storage -> storage.update(new UserStorage.User(3, 500)));
        first.start();
        second.start();
        first.join();
        second.join();
        assertTrue(first.result);
        assertFalse(second.result);
    }

    @Test
    public void whenUsersExistThenTransferCorrectWhileAmountAboveZero() throws InterruptedException {
        Function<UserStorage, Boolean> changer = storage -> storage.transfer(1, 2, 50);
        List<MutatorThread> threads = List.of(
                new MutatorThread(changer),
                new MutatorThread(changer),
                new MutatorThread(changer),
                new MutatorThread(changer)
        );
        threads.forEach(Thread::start);
        for (MutatorThread thread : threads) {
            thread.join();
        }
        assertThat(userA.amount, is(0));
        assertThat(userB.amount, is(400));
    }

    private class MutatorThread extends Thread {
        private final Function<UserStorage, Boolean> changer;
        boolean result;

        private MutatorThread(Function<UserStorage, Boolean> changer) {
            this.changer = changer;
        }

        @Override
        public void run() {
            result = changer.apply(storage);
        }
    }


}

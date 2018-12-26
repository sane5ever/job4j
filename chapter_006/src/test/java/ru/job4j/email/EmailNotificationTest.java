package ru.job4j.email;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Осуществление рассылки уведомлений на эл. почту. Тестирование.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-26
 */
public class EmailNotificationTest {
    private AtomicInteger counter = new AtomicInteger();

    EmailNotification sender = new EmailNotification() {
        @Override
        public void send(String subject, String body, String email) {
            super.send(subject, body, email);
            counter.getAndIncrement();
        }
    };

    @Test
    public void test() throws InterruptedException {
        AtomicBoolean checher = new AtomicBoolean(true);

        Runnable task = () -> {
            int index = (int) Thread.currentThread().getId() * 100;
            IntStream.range(index, index + 100).mapToObj(
                    i -> new User(
                            String.format("Mock%04d", i),
                            String.format("mock%04d@mail.com", i)
                    )
            ).forEach(user -> checher.compareAndSet(true, sender.emailTo(user)));
        };

        Thread first = new Thread(task);
        Thread second = new Thread(task);
        first.start();
        second.start();
        first.join();
        second.join();
        sender.close();
        while (!sender.isComplete()) {
            Thread.sleep(1);
        }

        assertTrue(checher.get());
        assertFalse(sender.emailTo(new User(null, null)));
        assertThat(counter.get(), is(200));


    }
}

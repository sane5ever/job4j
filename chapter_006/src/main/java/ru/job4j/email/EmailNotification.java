package ru.job4j.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Осуществление рассылки уведомлений на эл. почту.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-26
 */
public class EmailNotification {
    /**
     * Пул нитей для многопоточной отправки сообщений.
     */
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    /**
     * Осуществляет добавление в очередь на отправку уведомлений заданному пользователю.
     *
     * @param user пользователь
     * @return <tt>true</tt>, если таск успешно добавлен в очередь на отправку
     */
    public boolean emailTo(User user) {
        boolean result = false;
        if (!pool.isShutdown()) {
            String subject = String.format("Notification %s to email %s.", user.getUsername(), user.getEmail());
            String body = String.format("Add a new event to %s", user.getUsername());
            pool.submit(() -> this.send(subject, body, user.getEmail()));
            result = true;
        }
        return result;
    }

    /**
     * Завершает работу рассылки уведомлений, закрывает трэдпул.
     */
    public void close() {
        this.pool.shutdown();
    }

    /**
     * Отправляет заданную информацию на указанный имейл.
     *
     * @param subject заголовок отправляемого сообщения
     * @param body    тело отправляемого сообщения
     * @param email   электронный адрес для доставки
     */
    public void send(String subject, String body, String email) {

    }

    /**
     * Возвращает <tt>true</tt>, если все добавленные задачи выполнены.
     * Актуален только после завершения работы данного объекта методом {@link #close()}.
     *
     * @return <tt>true</tt>, если все добавленные задачи выполнены
     */
    public boolean isComplete() {
        return pool.isTerminated();
    }
}

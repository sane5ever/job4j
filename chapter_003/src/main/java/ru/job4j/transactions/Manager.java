package ru.job4j.transactions;

import java.util.*;

/**
 * Банковские переводы.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-10-19
 */
public class Manager {
    private Map<User, List<Account>> users = new HashMap<>();

    /**
     * Добавляем нового пользователя (если ещё нет).
     *
     * @param user пользователь
     */
    public void addUser(User user) {
        this.users.putIfAbsent(user, new ArrayList<>());
    }

    /**
     * Удаляем пользователя.
     *
     * @param user пользователь
     */
    public void deleteUser(User user) {
        this.users.remove(user);
    }

    /**
     * Добавляем пользователю новый счёт.
     *
     * @param passport паспорт пользователя
     * @param account  новый счёт
     */
    public void addAccountToUser(String passport, Account account) {
        User user = this.findUser(passport);
        if (user != null && this.users.get(user).indexOf(account) == -1) {
            this.users.get(user).add(account);
        }
    }

    /**
     * Удаляем у пользователя счёт.
     *
     * @param passport паспорт пользователя
     * @param account  удаляемый счёт
     */
    public void deleteAccountFromUser(String passport, Account account) {
        User user = this.findUser(passport);
        if (user != null) {
            this.users.get(user).remove(account);
        }
    }

    /**
     * Получаем список счетов для пользователя.
     *
     * @param passport паспорт пользователя
     * @return список счетов
     */
    public List<Account> getUserAccounts(String passport) {
        User user = this.findUser(passport);
        return user != null ? this.users.get(user) : Collections.emptyList();
    }

    /**
     * Осуществляем перевод со счёта на счёт.
     *
     * @param srcPassport   паспорт отправителя
     * @param srcRequisite  реквизиты счёта отправителя
     * @param destPassport  паспорт получателя
     * @param destRequisite реквизиты счёта получателя
     * @param amount        сумма перевода
     * @return true, если успешно
     */
    public boolean transferMoney(String srcPassport, String srcRequisite, String destPassport, String destRequisite, double amount) {
        Account srcAccount = this.findAccount(srcPassport, srcRequisite);
        Account destAccount =
                srcAccount != null && srcAccount.getValue() >= amount
                        ? this.findAccount(destPassport, destRequisite)
                        : null;
        return this.transferMoney(srcAccount, destAccount, amount);
    }

    /**
     * Поиск пользователя в базе по паспорту.
     *
     * @param passport паспорт
     * @return пользователь (либо null, если не найден)
     */
    private User findUser(String passport) {
        return this.users.entrySet()
                .stream()
                .filter(
                        entry -> entry
                                .getKey()
                                .getPassport()
                                .equals(passport)
                ).findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    /**
     * Поиск счёта пользователя в базе.
     *
     * @param passport  паспорт пользователя
     * @param requisite реквизиты счёта
     * @return номер счёта в списке (либо -1, если не найден)
     */
    private Account findAccount(String passport, String requisite) {
        return this.getUserAccounts(passport)
                .stream()
                .filter(
                        account -> account
                                .getRequisites()
                                .equals(requisite)
                ).findFirst()
                .orElse(null);
    }

    /**
     * Осуществляем перевод со счёта на счёт.
     *
     * @param src    счёт отправителя
     * @param dest   счёт получателя
     * @param amount сумма перевода
     * @return true, если успешно
     */
    private boolean transferMoney(Account src, Account dest, double amount) {
        boolean result = false;
        if (dest != null) {
            src.decreaseValue(amount);
            dest.increaseValue(amount);
            result = true;
        }
        return result;
    }
}

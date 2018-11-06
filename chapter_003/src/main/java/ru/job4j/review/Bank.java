package ru.job4j.review;

import ru.job4j.sort.User;

import java.util.*;

/**
 * Bank manager. Do something beautiful.
 */
public class Bank {
    /**
     * Mapping users to their accounts.
     */
    private Map<User, List<Account>> users = new HashMap<>();

    /**
     * Adding a new user.
     * If user already exists in the base, manager will refuse it.
     *
     * @param user a new user (expect it!)
     * @return {@code true} if it succeed
     */
    public boolean add(User user) {
        return this.users.putIfAbsent(Objects.requireNonNull(user), new ArrayList<>()) == null;
    }

    /**
     * Deleting user.
     *
     * @param user user's about to be deleted
     * @return {@code true} if it succeed
     */
    public boolean delete(User user) {
        return this.users.remove(user) != null;
    }

    /**
     * Adding an account to user.
     * Checks if user exists in the base and account doesn't.
     *
     * @param user    user
     * @param account new account
     * @return {@code true} if it succeed
     */
    public boolean addAccount(User user, Account account) {
        List<Account> accounts = this.getAccounts(user);
        return accounts != null
                && accounts.indexOf(account) == -1
                && accounts.add(account);
    }

    /**
     * Removing an account from the base.
     * Check if user exists in the base.
     *
     * @param user    user
     * @param account account's about to be deleted
     * @return {@code true} if it succeed
     */
    public boolean deleteAccount(User user, Account account) {
        return this.users.containsKey(user)
                && this.users.get(user).remove(account);
    }

    /**
     * Getting all accounts of this user.
     *
     * @param user user
     * @return user accounts, or {@code null} if they doesn't exist in the base
     */
    public List<Account> getAccounts(User user) {
        return this.users.get(user);
    }

    /**
     * Tries to transfer money from sender to receiver.
     * At first, checks if users exist in the base, and their accounts do too.
     *
     * @param sender      user sending money
     * @param from        account that money goes from
     * @param receiver    user receiving money
     * @param destination account where money goes to
     * @param amount      amount to send
     * @return {@code true} if succeed
     */
    public boolean transfer(User sender, Account from,
                            User receiver, Account destination, double amount) {
        return this.check(sender, from, receiver, destination)
                && from.transfer(destination, amount);
    }

    private boolean check(User sender, Account from, User receiver, Account destination) {
        return this.users.containsKey(sender)
                && this.getAccounts(sender).contains(from)
                && this.users.containsKey(receiver)
                && this.getAccounts(receiver).contains(destination);
    }

    /**
     * String representation of account.
     *
     * @return string line
     */
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder("Bank contains the following clients: ");
        this.users.forEach((user, accounts) -> buffer
                .append(System.lineSeparator())
                .append(user.getName())
                .append(", aged ")
                .append(user.getAge())
                .append(", accounts: ")
                .append(accounts)

        );
        return buffer.toString();

    }
}
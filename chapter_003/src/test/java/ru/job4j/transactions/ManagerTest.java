package ru.job4j.transactions;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

/**
 * Банковские переводы. Тесты.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-10-20
 */
public class ManagerTest {
    private final Manager manager = new Manager();

    @Before
    public void prepare() {
        User user = new User("Kevin", "14745");
        this.manager.addUser(user);
        this.manager.addAccountToUser("14745", new Account(50d, "14745_01"));
        this.manager.addAccountToUser("14745", new Account(100d, "14745_02"));
    }

    @Test
    public void whenNotAddedThenEmptyAccountList() {
        List<Account> result = this.manager.getUserAccounts("000");
        assertThat(result.size(), is(0));
    }

    @Test
    public void whenAddedThenExist() {
        List<Account> result = this.manager.getUserAccounts("14745");
        assertThat(result.size(), not(0));
    }

    @Test
    public void whenDeleteThenNotExist() {
        this.manager.deleteUser(new User("Kevin", "14745"));
        List<Account> result = this.manager.getUserAccounts("14745");
        assertThat(result, is(Collections.emptyList()));
    }

    @Test
    public void whenAddNewAccountToNonexistentUserThenUserNotExist() {
        this.manager.addAccountToUser("14746", new Account(150d, "14745_02"));
        int result = this.manager.getUserAccounts("14746").size();
        assertThat(result, is(0));
    }

    @Test
    public void whenAddExistAccountThenSameSize() {
        int expected = this.manager.getUserAccounts("14745").size();
        this.manager.addAccountToUser("14745", new Account(150d, "14745_02"));
        int result = this.manager.getUserAccounts("14745").size();
        assertThat(result, is(expected));
    }

    @Test
    public void whenDeleteExistAccountThenSizeDecreased() {
        int expected = this.manager.getUserAccounts("14745").size() - 1;
        this.manager.deleteAccountFromUser("14745", new Account(50d, "14745_01"));
        int result = this.manager.getUserAccounts("14745").size();
        assertThat(result, is(expected));
    }

    @Test
    public void whenDeleteNonexistentAccountThenSameSize() {
        int expected = this.manager.getUserAccounts("14745").size();
        this.manager.deleteAccountFromUser("14745", new Account(100d, "14745_03"));
        int result = this.manager.getUserAccounts("14745").size();
        assertThat(result, is(expected));
    }

    @Test
    public void whenDeleteAccountFromNonexistentUserThenUserStillNotExist() {
        this.manager.deleteAccountFromUser("14746", new Account(100d, "14745_03"));
        int result = this.manager.getUserAccounts("14746").size();
        assertThat(result, is(0));
    }

    @Test
    public void whenTransferBetweenExistAccountsThenSuccess() {
        boolean result = this.transfer(50d);
        assertThat(result, is(true));
    }

    @Test
    public void whenTransferBetweenExistAccountsThenSourceAccountDecrease() {
        this.transfer(50d);
        double result = this.manager.getUserAccounts("14745").iterator().next().getValue();
        assertThat(result, closeTo(0d, 0.1));
    }

    @Test
    public void whenTransferBetweenExistAccountsThenDestAccountIncrease() {
        this.transfer(50d);
        double result = this.manager.getUserAccounts("14745").get(1).getValue();
        assertThat(result, closeTo(150d, 0.1));
    }

    @Test
    public void whenTransferAmountTooBigThenNotSuccess() {
        boolean result = this.transfer(100d);
        assertThat(result, is(false));
    }

    @Test
    public void whenFailTransferThenSourceAccountStaysSame() {
        this.transfer(100d);
        double result = this.manager.getUserAccounts("14745").iterator().next().getValue();
        assertThat(result, closeTo(50d, 0.1));
    }

    @Test
    public void whenFailTransferThenDestAccountStaysSame() {
        this.transfer(100d);
        double result = this.manager.getUserAccounts("14745").get(1).getValue();
        assertThat(result, closeTo(100d, 0.1));
    }

    @Test
    public void whenPossibleTransferToAnotherUserThenSuccess() {
        boolean result = this.transferToUser(50d);
        assertThat(result, is(true));
    }

    @Test
    public void whenImpossibleTransferToAnotherUserThenFail() {
        boolean result = this.transferToUser(100d);
        assertThat(result, is(false));
    }

    @Test
    public void whenTransferToNonexistentUserThenFail() {
        boolean result = this.manager.transferMoney(
                "14745", "14745_01", "14777", "14777_01", 50d
        );
        assertThat(result, is(false));
    }

    @Test
    public void whenTransferToNonexistentAccountThenFail() {
        boolean result = this.manager.transferMoney(
                "14745", "14745_01", "14745", "14745_03", 50d
        );
        assertThat(result, is(false));
    }

    @Test
    public void whenTransferFromNoExistentUserThenFail() {
        boolean result = this.manager.transferMoney(
                "14747", "14745_01", "14745", "14745_02", 50d
        );
        assertThat(result, is(false));
    }

    private boolean transfer(double amount) {
        return this.manager.transferMoney(
                "14745", "14745_01", "14745", "14745_02", amount
        );
    }

    private boolean transferToUser(double amount) {
        this.manager.addUser(new User("John", "147456"));
        this.manager.addAccountToUser("147456", new Account(0d, "147456_01"));
        return this.manager.transferMoney(
                "14745", "14745_01", "147456", "147456_01", amount
        );
    }
}

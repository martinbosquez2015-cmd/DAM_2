package BankingTest;
import Banking.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {

    @Test
    void shouldPrintStatementAfterOneDeposit() {
        Account account = new Account();

        account.deposit(500);

        String statement = account.printStatement();

        assertEquals(
                "Date        Amount  Balance\n" +
                        "24.12.2015   +500      500",
                statement
        );
    }

    @Test
    void shouldPrintStatementAfterDepositAndWithdraw() {
        Account account = new Account();

        account.deposit(500);
        account.withdraw(100);

        String statement = account.printStatement();

        assertEquals(
                "Date        Amount  Balance\n" +
                        "23.8.2016    -100      400\n" +
                        "24.12.2015   +500      500",
                statement
        );
    }

    @Test
    void shouldUpdateBalanceAfterDeposit() {
        Account account = new Account();

        account.deposit(500);
        account.deposit(300);

        String statement = account.printStatement();

        assertEquals(
                "Date        Amount  Balance\n" +
                        "24.12.2015   +300      800\n" +
                        "24.12.2015   +500      500",
                statement
        );
    }

    @Test
    void shouldUpdateBalanceAfterWithdraw() {
        Account account = new Account();

        account.deposit(1000);
        account.withdraw(300);

        String statement = account.printStatement();

        assertEquals(
                "Date        Amount  Balance\n" +
                        "23.8.2016    -300      700\n" +
                        "24.12.2015  +1000     1000",
                statement
        );
    }

    @Test
    void shouldReturnEmptyStatementForNewAccount() {
        Account account = new Account();

        assertEquals(
                "Date        Amount  Balance",
                account.printStatement()
        );
    }
}
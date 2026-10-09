package BankingTest;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import Banking.Account;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountoTesto {

    @Test
    void ImprimirExtractoDespuesDeUnDeposito() {
        Account account = new Account();

        account.setDate(LocalDate.of(2015, 12, 24));
        account.deposit(500);

        String statement = account.printStatement();

        assertEquals(
                "Date        Amount  Balance\n" +
                        "24.12.2015   +500      500",
                statement
        );
    }

    @Test
    void ImprimirExtractoDespuesDeDepositoYRetiro() {
        Account account = new Account();

        account.setDate(LocalDate.of(2015, 12, 24));
        account.deposit(500);

        account.setDate(LocalDate.of(2016, 8, 23));
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
    void ActualizarSaldoDespuesDeDeposito() {
        Account account = new Account();

        account.setDate(LocalDate.of(2015, 12, 24));
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
    void ActualizarSaldoDespuesDeRetiro() {
        Account account = new Account();

        account.setDate(LocalDate.of(2015, 12, 24));
        account.deposit(1000);

        account.setDate(LocalDate.of(2016, 8, 23));
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
    void RetornarEncabezadoParaCuentaNueva() {
        Account account = new Account();

        assertEquals(
                "Date        Amount  Balance",
                account.printStatement()
        );
    }
}
package Banking;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Account {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("d.M.yyyy");

    private int balance = 0;

    private final List<Transaction> transactions = new ArrayList<>();

    private LocalDate currentDate;

    public Account() {
        this.currentDate = LocalDate.now();
    }

    public void deposit(int amount) {
        balance += amount;

        transactions.add(
                new Transaction(currentDate, amount, balance)
        );
    }

    public void withdraw(int amount) {
        balance -= amount;

        transactions.add(
                new Transaction(currentDate, -amount, balance)
        );
    }

    public void setDate(LocalDate date) {
        this.currentDate = date;
    }

    public String printStatement() {

        StringBuilder statement = new StringBuilder();

        statement.append("Date        Amount  Balance");

        for (int i = transactions.size() - 1; i >= 0; i--) {

            Transaction transaction = transactions.get(i);

            statement.append("\n");

            statement.append(
                    String.format(
                            "%-12s%+5d%9d",
                            transaction.date().format(DATE_FORMATTER),
                            transaction.amount(),
                            transaction.balance()
                    )
            );
        }

        return statement.toString();
    }

    private record Transaction(
            LocalDate date,
            int amount,
            int balance
    ) {
    }
}
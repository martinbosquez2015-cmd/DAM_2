package com.example.birthday;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class BirthdayService {

    private static final String SUBJECT = "¡Feliz cumpleaños!";

    private final ContactSource contactSource;
    private final MessageSender messageSender;
    private final Clock clock;

    public BirthdayService(
            ContactSource contactSource,
            MessageSender messageSender,
            Clock clock) {

        this.contactSource = Objects.requireNonNull(contactSource);
        this.messageSender = Objects.requireNonNull(messageSender);
        this.clock = Objects.requireNonNull(clock);
    }

    public void sendBirthdayGreetings() throws IOException {
        LocalDate today = LocalDate.now(clock);
        List<Contact> contacts = contactSource.load();

        for (Contact contact : contacts) {
            if (hasBirthdayOn(contact.dateOfBirth(), today)) {
                messageSender.send(createGreeting(contact));
            }
        }
    }

    private boolean hasBirthdayOn(
            LocalDate dateOfBirth, LocalDate today) {

        boolean bornOnLeapDay =
                dateOfBirth.getMonthValue() == 2
                        && dateOfBirth.getDayOfMonth() == 29;

        boolean birthdayMatches;

        if (bornOnLeapDay && !today.isLeapYear()) {
            birthdayMatches =
                    today.getMonthValue() == 2
                            && today.getDayOfMonth() == 28;
        } else {
            birthdayMatches =
                    dateOfBirth.getMonthValue() == today.getMonthValue()
                            && dateOfBirth.getDayOfMonth() == today.getDayOfMonth();
        }

        return birthdayMatches;
    }

    private BirthdayMessage createGreeting(Contact contact) {
        String body = "¡Feliz cumpleaños, querido/a "
                + contact.firstName() + "!";

        return new BirthdayMessage(
                contact.email(), SUBJECT, body);
    }
}

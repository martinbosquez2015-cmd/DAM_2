package Test;

import def.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BirthdayServiceTest {

    @Mock
    private ContactSource contactSource;

    @Mock
    private MessageSender messageSender;

    @Test
    void sendsGreetingWhenContactHasBirthday() throws IOException {
        Contact contact = contact(
                "Doe", "John", "1982-10-22", "john.doe@foobar.com");

        when(contactSource.load()).thenReturn(List.of(contact));
        BirthdayService service = serviceOn("2026-10-22");

        service.sendBirthdayGreetings();

        verify(messageSender).send(new BirthdayMessage(
                "john.doe@foobar.com",
                "¡Feliz cumpleaños!",
                "¡Feliz cumpleaños, querido/a John!"));
    }

    @Test
    void doesNotSendGreetingWhenContactHasNoBirthday()
            throws IOException {

        when(contactSource.load()).thenReturn(List.of(
                contact("Doe", "John", "1982-10-21",
                        "john.doe@foobar.com")));

        BirthdayService service = serviceOn("2026-10-22");

        service.sendBirthdayGreetings();

        verify(messageSender, never()).send(
                org.mockito.ArgumentMatchers.any());
    }

    @Test
    void sendsMessagesOnlyToContactsWhoHaveBirthday()
            throws IOException {

        Contact firstBirthday = contact(
                "Doe", "John", "1982-10-22", "john@foobar.com");

        Contact noBirthday = contact(
                "Ann", "Mary", "1975-09-11", "mary@foobar.com");

        Contact secondBirthday = contact(
                "Poe", "Sam", "1990-10-22", "sam@foobar.com");

        when(contactSource.load()).thenReturn(
                List.of(firstBirthday, noBirthday, secondBirthday));

        BirthdayService service = serviceOn("2026-10-22");

        service.sendBirthdayGreetings();

        ArgumentCaptor<BirthdayMessage> captor =
                ArgumentCaptor.forClass(BirthdayMessage.class);

        verify(messageSender, org.mockito.Mockito.times(2))
                .send(captor.capture());

        assertEquals(
                List.of("john@foobar.com", "sam@foobar.com"),
                captor.getAllValues().stream()
                        .map(BirthdayMessage::recipient)
                        .toList());
    }

    @Test
    void treatsFebruaryTwentyNinthAsBirthdayOnFebruaryTwentyEighthInNonLeapYear()
            throws IOException {

        when(contactSource.load()).thenReturn(List.of(
                contact("Leap", "Alex", "2000-02-29",
                        "alex@foobar.com")));

        BirthdayService service = serviceOn("2025-02-28");

        service.sendBirthdayGreetings();

        verify(messageSender).send(
                org.mockito.ArgumentMatchers.any(BirthdayMessage.class));
    }

    @Test
    void treatsFebruaryTwentyNinthAsBirthdayOnFebruaryTwentyNinthInLeapYear()
            throws IOException {

        when(contactSource.load()).thenReturn(List.of(
                contact("Leap", "Alex", "2000-02-29",
                        "alex@foobar.com")));

        BirthdayService service = serviceOn("2024-02-29");

        service.sendBirthdayGreetings();

        verify(messageSender).send(
                org.mockito.ArgumentMatchers.any(BirthdayMessage.class));
    }

    @Test
    void createsMessageWithCorrectSubjectRecipientAndBody()
            throws IOException {

        when(contactSource.load()).thenReturn(List.of(
                contact("Smith", "Taylor", "1999-05-14",
                        "taylor@foobar.com")));

        BirthdayService service = serviceOn("2026-05-14");

        service.sendBirthdayGreetings();

        ArgumentCaptor<BirthdayMessage> captor =
                ArgumentCaptor.forClass(BirthdayMessage.class);

        verify(messageSender).send(captor.capture());

        BirthdayMessage message = captor.getValue();

        assertEquals("taylor@foobar.com", message.recipient());
        assertEquals("¡Feliz cumpleaños!", message.subject());
        assertEquals(
                "¡Feliz cumpleaños, querido/a Taylor!",
                message.body());
    }

    private BirthdayService serviceOn(String date) {
        LocalDate localDate = LocalDate.parse(date);

        Clock fixedClock = Clock.fixed(
                localDate.atStartOfDay(ZoneOffset.UTC).toInstant(),
                ZoneOffset.UTC);

        return new BirthdayService(
                contactSource, messageSender, fixedClock);
    }

    private Contact contact(
            String lastName,
            String firstName,
            String dateOfBirth,
            String email) {

        return new Contact(
                lastName,
                firstName,
                LocalDate.parse(dateOfBirth),
                email);
    }
}

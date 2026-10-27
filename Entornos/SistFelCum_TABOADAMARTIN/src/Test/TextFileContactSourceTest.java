package Test;


import def.Contact;
import def.TextFileContactSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TextFileContactSourceTest {

    @TempDir
    Path tempDir;

    @Test
    void readsHeaderFieldsSpacesAndBlankLines() throws IOException {
        Path file = tempDir.resolve("contacts.txt");

        String contents =
                "last_name, first_name, date_of_birth, email\n"
                        + "Doe, John, 1982/10/08, john.doe@foobar.com\n"
                        + "\n"
                        + "Ann, Mary, 1975/09/11, mary.ann@foobar.com\n";

        Files.writeString(file, contents, StandardCharsets.UTF_8);

        List<Contact> contacts =
                new TextFileContactSource(file).load();

        assertEquals(2, contacts.size());
        assertEquals("Doe", contacts.get(0).lastName());
        assertEquals("John", contacts.get(0).firstName());
        assertEquals(
                LocalDate.of(1982, 10, 8),
                contacts.get(0).dateOfBirth());
        assertEquals(
                "john.doe@foobar.com",
                contacts.get(0).email());
        assertEquals("Mary", contacts.get(1).firstName());
    }

    @Test
    void readsFileWithoutHeader() throws IOException {
        Path file = tempDir.resolve("without-header.txt");

        Files.writeString(
                file,
                "Doe, John, 1982/10/08, john.doe@foobar.com\n",
                StandardCharsets.UTF_8);

        List<Contact> contacts =
                new TextFileContactSource(file).load();

        assertEquals(1, contacts.size());
        assertEquals("John", contacts.get(0).firstName());
    }

    @Test
    void reportsInvalidStrictDateAndLineNumber()
            throws IOException {

        Path file = tempDir.resolve("invalid-date.txt");

        Files.writeString(
                file,
                "last_name, first_name, date_of_birth, email\n"
                        + "Doe, John, 2023/02/29, john@foobar.com\n",
                StandardCharsets.UTF_8);

        TextFileContactSource source =
                new TextFileContactSource(file);

        IOException exception =
                assertThrows(IOException.class, source::load);

        assertTrue(exception.getMessage().contains("línea 2"));
        assertTrue(exception.getMessage().contains(
                "fecha de nacimiento inválida"));
    }

    @Test
    void reportsIncorrectNumberOfFieldsAndLineNumber()
            throws IOException {

        Path file = tempDir.resolve("invalid-fields.txt");

        Files.writeString(
                file,
                "Doe, John, 1982/10/08\n",
                StandardCharsets.UTF_8);

        TextFileContactSource source =
                new TextFileContactSource(file);

        IOException exception =
                assertThrows(IOException.class, source::load);

        assertTrue(exception.getMessage().contains("línea 1"));
        assertTrue(exception.getMessage().contains(
                "se esperaban 4 campos"));
    }

    @Test
    void reportsWhenFileDoesNotExist() {
        Path file = tempDir.resolve("missing.txt");
        TextFileContactSource source =
                new TextFileContactSource(file);

        assertThrows(IOException.class, source::load);
    }
}

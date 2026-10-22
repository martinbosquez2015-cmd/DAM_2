package def;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TextFileContactSource implements ContactSource {

    // uuuu permite validar estrictamente el año.
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("uuuu/MM/dd")
                    .withResolverStyle(ResolverStyle.STRICT);

    private final Path path;

    public TextFileContactSource(Path path) {
        this.path = Objects.requireNonNull(
                path, "La ruta del archivo es obligatoria.");
    }

    @Override
    public List<Contact> load() throws IOException {
        List<Contact> contacts = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(
                path, StandardCharsets.UTF_8)) {

            String line;
            int lineNumber = 0;
            boolean firstNonBlankLine = true;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (!line.isBlank()) {
                    if (firstNonBlankLine && isHeader(line)) {
                        firstNonBlankLine = false;
                    } else {
                        contacts.add(parseContact(line, lineNumber));
                        firstNonBlankLine = false;
                    }
                }
            }
        }

        return List.copyOf(contacts);
    }

    private boolean isHeader(String line) {
        String[] fields = line.split(",", -1);

        boolean header = fields.length == 4
                && fields[0].trim().equalsIgnoreCase("last_name")
                && fields[1].trim().equalsIgnoreCase("first_name")
                && fields[2].trim().equalsIgnoreCase("date_of_birth")
                && fields[3].trim().equalsIgnoreCase("email");

        return header;
    }

    private Contact parseContact(String line, int lineNumber)
            throws IOException {

        String[] fields = line.split(",", -1);

        if (fields.length != 4) {
            throw formatError(
                    lineNumber,
                    "se esperaban 4 campos separados por comas y se encontraron "
                            + fields.length + ".");
        }

        LocalDate dateOfBirth;

        try {
            dateOfBirth = LocalDate.parse(
                    fields[2].trim(), DATE_FORMATTER);
        } catch (DateTimeParseException exception) {
            throw formatError(
                    lineNumber,
                    "fecha de nacimiento inválida; el formato esperado es yyyy/MM/dd.");
        }

        Contact contact;

        try {
            contact = new Contact(
                    fields[0].trim(),
                    fields[1].trim(),
                    dateOfBirth,
                    fields[3].trim());
        } catch (IllegalArgumentException exception) {
            throw formatError(lineNumber, exception.getMessage());
        }

        return contact;
    }

    private IOException formatError(int lineNumber, String detail) {
        return new IOException(
                "Error de formato en la línea "
                        + lineNumber + ": " + detail);
    }
}

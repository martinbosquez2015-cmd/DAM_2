package def;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.time.Clock;

public class Main {

    public static void main(String[] args) {
        if (args.length > 1) {
            System.err.println(
                    "Uso: java -cp target/classes "
                            + "com.example.birthday.def.Main [ruta/al/archivo.txt]");
        } else {
            try {
                Path contactsPath = args.length == 1
                        ? Path.of(args[0])
                        : Path.of("contacts.txt");

                ContactSource contactSource =
                        new TextFileContactSource(contactsPath);

                MessageSender messageSender =
                        new ConsoleMessageSender();

                BirthdayService birthdayService =
                        new BirthdayService(
                                contactSource,
                                messageSender,
                                Clock.systemDefaultZone());

                birthdayService.sendBirthdayGreetings();

            } catch (IOException | InvalidPathException exception) {
                System.err.println(
                        "No se pudo procesar el archivo de contactos: "
                                + exception.getMessage());
            }
        }
    }
}
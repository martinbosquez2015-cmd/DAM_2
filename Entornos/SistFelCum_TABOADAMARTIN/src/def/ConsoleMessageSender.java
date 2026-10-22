package def;

public class ConsoleMessageSender implements MessageSender {

    @Override
    public void send(BirthdayMessage message) {
        System.out.println("Destinatario: " + message.recipient());
        System.out.println("Asunto: " + message.subject());
        System.out.println("Mensaje: " + message.body());
        System.out.println();
    }
}
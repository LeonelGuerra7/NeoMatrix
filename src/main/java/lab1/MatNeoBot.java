package lab1;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MatNeoBot extends TelegramLongPollingBot {

        @Override
        public String getBotUsername() {
            return "MatNeo_bot";
        }

        @Override
        public String getBotToken() {
            return "7059774353:AAHLF-9tE0UXBpVmjI_d0t2qM7GUty55ugQ";
        }

        @Override
        public void onUpdateReceived(Update update) {
            if (update.hasMessage() && update.getMessage().hasText()) {
                String messageText = update.getMessage().getText();
                String chatId = update.getMessage().getChatId().toString();

                if (messageText.startsWith("/cambio")) {
                    handleCambioCommand(chatId, messageText);
                } else {
                    switch (messageText) {
                        case "/start":
                            enviarMensajeDeBienvenida(chatId);
                            break;
                        case "/info":
                            enviarInfoPersonal(chatId, update);
                            break;
                        case "/progra":
                            enviarComentarioProgra(chatId);
                            break;
                        case "/hola":
                            enviarSaludo(chatId, update.getMessage().getFrom().getFirstName());
                            break;
                        default:
                            enviarMensaje(chatId, "Comando no reconocido");
                            break;
                    }
                }
            }
        }

    private void handleCambioCommand(String chatId, String messageText) {
        try {
            String[] parts = messageText.split(" ");
            double euros = Double.parseDouble(parts[1]);
            double tipoCambio = 8.5; // Supongamos que 1 Euro = 8.5 Quetzales
            double quetzales = euros * tipoCambio;
            enviarMensaje(chatId, euros + " Euros equivalen a " + quetzales + " Quetzales.");
        } catch (Exception e) {
            enviarMensaje(chatId, "Por favor, usa el comando de esta manera: /cambio [cantidad de euros]");
        }
    }

    private void enviarMensajeDeBienvenida(String chatId) {
        String mensaje = "¡Hola! Soy MatNeo_bot. Estos son los comandos que puedes usar:\n" +
                "/info - Obtener información personal.\n" +
                "/progra - Comentarios sobre la clase de programación.\n" +
                "/hola - Recibir un saludo personalizado con la fecha actual.\n" +
                "/cambio [cantidad] - Calcular el tipo de cambio de Euros a Quetzales.";
        enviarMensaje(chatId, mensaje);
    }

    private void enviarInfoPersonal(String chatId, Update update) {
        String nombre = update.getMessage().getFrom().getFirstName();
        String username = update.getMessage().getFrom().getUserName();
        Long userId = update.getMessage().getFrom().getId();

        String info = "Información del usuario:\n" +
                "Nombre: " + nombre + "\n" +
                "Username: @" + (username != null ? username : "No disponible") + "\n" +
                "ID de Usuario: " + userId + "\n" +
                "Actualmente estás en el 4to. semestre.";

        enviarMensaje(chatId, info);
    }

    private void enviarComentarioProgra(String chatId) {
        String comentario = "La clase de programación es muy interesante y me ha ayudado a desarrollar habilidades lógicas y técnicas. La Programación es el taller donde las ideas se convierten en realidades. Cada línea de código es una oportunidad para crear, innovar y transformar. Es el arte de dar forma al futuro con lógica y creatividad.";
        enviarMensaje(chatId, comentario);
    }

    private void enviarSaludo(String chatId, String nombre) {
        String fecha = new SimpleDateFormat("EEEE dd 'de' MMMM").format(new Date());
        String saludo = "Hola, " + nombre + ", hoy es " + fecha;
        enviarMensaje(chatId, saludo);
    }

    private void enviarMensaje(String chatId, String texto) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(texto);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}

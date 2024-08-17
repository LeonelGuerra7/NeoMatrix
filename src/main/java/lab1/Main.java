package lab1;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args)
    {
     try {
         TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
         MatNeoBot matNeoBot = new MatNeoBot();
         botsApi.registerBot(matNeoBot);
         System.out.println(("El bot está funcionando......"));
     }
     catch (Exception ex){
         System.out.println("Error" + ex.getMessage());
     }

    }
}
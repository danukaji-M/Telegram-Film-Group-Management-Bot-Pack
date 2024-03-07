package UploadBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class UploadThread extends Thread{
    public void run(){
        TelegramBotsApi botsApi;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TelegramUpload());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
}

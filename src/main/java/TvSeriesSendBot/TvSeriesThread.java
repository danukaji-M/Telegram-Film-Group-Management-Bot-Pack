package TvSeriesSendBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TvSeriesThread extends Thread{
    public void run (){
        TelegramBotsApi botsApi;
        try{
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TvSeries());
        }catch(TelegramApiException e){
            throw new RuntimeException(e);
        }
    }
}

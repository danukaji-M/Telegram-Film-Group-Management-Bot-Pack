package GroupManage;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class GroupManagerThread extends Thread{
    public void run (){
        TelegramBotsApi botsApi;
        try{
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new GroupManager());
        }catch(TelegramApiException e){
            throw new RuntimeException(e);
        }
    }
}

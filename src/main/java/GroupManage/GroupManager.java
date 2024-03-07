package GroupManage;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class GroupManager extends TelegramLongPollingBot{
    GroupManager(){

    }
    @Override
    public void onUpdateReceived(@NotNull Update update) {
        var msg = update.getMessage().getText();
        System.out.println( getBotUsername());
        System.out.println(msg);
    }

    @Override
    public String getBotUsername() {
        return "DSK Group Manager";
    }

    @Override
    public String getBotToken() {
        return "6667393200:AAHYn0L6AG_9CTJ3r5Q0S4JLszzcntOKReE";
    }

}

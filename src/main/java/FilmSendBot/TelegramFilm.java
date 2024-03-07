package FilmSendBot;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramFilm extends TelegramLongPollingBot{
    TelegramFilm(){

    }
    @Override
    public void onUpdateReceived(@NotNull Update update) {
        var msg = update.getMessage().getText();
        System.out.println( getBotUsername());
        System.out.println(msg );
    }
    @Override
    public String getBotUsername() {
        return "DSK Film Bot";
    }

    @Override
    public String getBotToken() {
        return "7193011749:AAFsUeVUsvHDYZ-HCavWe_r5UitQSh3bEHw";
    }


}

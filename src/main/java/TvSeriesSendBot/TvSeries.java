package TvSeriesSendBot;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TvSeries extends TelegramLongPollingBot {
    TvSeries(){

    }
    @Override
    public void onUpdateReceived(@NotNull Update update) {
        var msg = update.getMessage().getText();
        System.out.println( getBotUsername());
        System.out.println(msg);

    }

    @Override
    public String getBotUsername() {
        return "DSK Tv Series Bot";
    }

    @Override
    public String getBotToken() {
        return "6651173985:AAGfdzCBxYjQIt5XDvf3uDT777RxMPM7f2U";
    }


}

package FilmSendBot;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TelegramFilm extends TelegramLongPollingBot{
    private static final String CONFIG_FILE_PATH = "bot-config.properties";
    private static String BOT_TOKEN;
    private static String BOT_NAME;
    TelegramFilm(){
        Properties properties = new Properties();
        try{
            properties.load(new FileInputStream(CONFIG_FILE_PATH));
            BOT_NAME= properties.getProperty("film.bot.username");
            BOT_TOKEN=properties.getProperty("film.bot.token");
            System.out.println("bot name is :" +BOT_NAME);
            System.out.println("bot token is :" +BOT_TOKEN);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void onUpdateReceived(@NotNull Update update) {
        var msg = update.getMessage().getText();
        System.out.println( getBotUsername());
        System.out.println(msg );
    }
    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }


}

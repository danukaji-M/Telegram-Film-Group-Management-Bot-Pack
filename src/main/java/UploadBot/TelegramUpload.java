package UploadBot;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
public class TelegramUpload extends TelegramLongPollingBot{
    private String TestGroup = "1002144649301";
    //private File thumbPath;
    TelegramUpload(){

    }
    // මීට වැඩිය හොදයි ආච්චිට හාල් ගැරුවනම් වුට්ට 
    @Override
    public void onUpdateReceived(@NotNull Update update) {
        if (update.hasMessage() || update.getMessage().hasDocument()){
            sendText(update.getMessage().getChatId(),update.getMessage().getText());
            System.out.println("Message: " + update.getMessage().getText());
            Document document = update.getMessage().getDocument();
            String fileId = document.getFileId();
            GetFile getFileMethod = new GetFile();
            getFileMethod.setFileId(fileId);
            try{
                org.telegram.telegrambots.meta.api.objects.File file = execute(getFileMethod);
                String filePath = file.getFilePath();
                sendDocument(Long.valueOf(TestGroup), filePath);
                System.out.println("File Path: " + filePath);
            }catch (TelegramApiException e){
                throw new RuntimeException(e);
            }
        }
    }
    //upload Document
    public void sendDocument(Long chatId, String fileId){
        SendDocument sd = new SendDocument();
        sd.setChatId(chatId.toString());
        sd.setDocument(new InputFile(fileId));
        sd.setCaption("Test 1");
        try {
            execute(sd);
        }catch (TelegramApiException e){
            throw new RuntimeException(e);
        }
    }


    //menuSetting Controller
    public void sendMenu(Long who, String txt, InlineKeyboardMarkup km){
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .parseMode("HTML")
                .text(txt)
                .replyMarkup(km)
                .build();
        try{
            execute(sm);
        }catch(TelegramApiException e){
            throw new RuntimeException(e);
        }

    }

    //textMessage controller
    public void sendText(Long who, String what){
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what)
                .build();
        try{
            execute(sm);
        }catch(TelegramApiException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return "DSK Upload Bot";
    }

    @Override
    public String getBotToken() {
        return "6996156749:AAHpvtTCOx2ncPvs-1KYucI-UifwAQkjBwU";
    }

}

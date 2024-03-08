/**
 * This Java program demonstrates a Telegram bot designed to upload documents into a special group or channel.
 * The bot facilitates uploading documents with the ability to change file names and perform other operations.
 *
 * This project aims to streamline the process of sharing documents within a Telegram group or channel,
 * providing users with the convenience of uploading files directly through the bot interface.
 *
 * Prerequisites:
 * - Java Development Kit (JDK) installed
 * - Telegram API token for bot authentication
 * - Telegram Bot API library (e.g., TelegramBots Java library) added to the project dependencies
 *
 * Special instructions:
 * - Obtain a Telegram API token by creating a new bot through the BotFather bot on Telegram.
 * - Configure the bot's behavior and permissions through BotFather or directly in the code.
 * - Ensure proper authentication and permissions for the bot to access the target group or channel.
 *
 * Main features:
 * - Uploading documents to Telegram groups or channels.
 * - Ability to change file names before uploading.
 * - Support for various document formats such as PDFs, images, text files, etc.
 * - Integration with Telegram Bot API for seamless communication.
 *
 * Author: Danukajie Hansanath
 * Creation Date: 2024/march /08
 * Version: 1.0.0
 *
 * Acknowledgments:
 * - The Telegram Bot API documentation for guidance on bot development.
 * - Telegram Api
 */
package UploadBot;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

//import javax.print.attribute.standard.Media;
import java.io.File;
import java.util.ArrayList;
//import java.util.Collections;
import java.util.List;

public class TelegramUpload extends TelegramLongPollingBot {
    @SuppressWarnings("unused")
    private List<Long> imggroupIds = new ArrayList<>();
    private File thumbPath = new File("src/main/images/thumb.jpg");
    @SuppressWarnings("unused")
    private List<Long> videGroupIds = new ArrayList<>();
    private Long [] imggroupIdArray;
    private Long [] videoGroupArray;
    TelegramUpload() {
        imggroupIds.add(-1002144649301L);
        for (int i =0; i<imggroupIds.size();i++) {
            imggroupIdArray = imggroupIds.toArray(new Long[imggroupIds.size()-1]);
        }
        videGroupIds.add(-1002144649301L);
        for(int i = 0;i<videGroupIds.size();i++){
            videoGroupArray = videGroupIds.toArray(new Long[videGroupIds.size()-1]);
        }
    }


    // මීට වැඩිය හොදයි ආච්චිට හාල් ගැරුවනම් වුට්ට
    @Override
    public void onUpdateReceived(@NotNull Update update) {
        if(update.getMessage().hasText()){
            System.out.println("Sender Send A text Msg");
        } else if(update.getMessage().hasPhoto()){
            List <PhotoSize> photos = update.getMessage().getPhoto();
            PhotoSize photo = photos.get(photos.size()-1);
            System.out.println("Sender Send A Image : " +photo.getFileId());
            InputFile iFile = new InputFile(photo.getFileId());
            sendImage(imggroupIdArray,iFile,"NoCapption",null, photo.getFileId());
        }else if (update.getMessage().hasVideo()){
            Video video = update.getMessage().getVideo();
            System.out.println("Sender Send a video : "+video.getFileId());
            InputFile vFile = new InputFile(video.getFileId());
            System.out.println(thumbPath);
            sendVideo(videoGroupArray,vFile,"No Caption",thumbPath,video.getFileId());
        }else if(update.getMessage().hasDocument()){
            Document document = update.getMessage().getDocument();
            System.out.println("Has A Document : " + document.getFileId());
            InputFile dFile = new InputFile(document.getFileId());
            Float dsze = Float.valueOf(update.getMessage().getDocument().getFileSize()/(1024));
            String size = String.valueOf(dsze);
            String name = "_noName";
            String fileName = document.getFileName();
            String extension = "";
            if(fileName != null && !fileName.isEmpty()){
                int lastIndex = fileName.lastIndexOf('.');
                if(lastIndex >=0 && lastIndex<fileName.length()-1){
                    extension = fileName.substring(lastIndex+1);
                }
            }
             String rename = "["+size+"KB_]"+name+fileName;
            System.out.println(rename);
            sendDocument(videoGroupArray,dFile,"No Caption",thumbPath,document.getFileId(),rename);
        }
    }
    public void sendDocument(Long @NotNull [] groupIds, InputFile file, String caption, File thumb,String fileId,String fileName){
        for (Long groupId: groupIds){
            SendDocument sendDocument = new SendDocument();
            System.out.println(file.getMediaName());
            sendDocument.setChatId(groupId.toString());
            sendDocument.setDocument(file);
            System.out.println("Error");
            System.out.println("No Error");
            if (thumb !=null) {
                sendDocument.setThumb(new InputFile(thumb));
            }
            try{
                System.out.println("Error");
                execute(sendDocument);
                System.out.println("Success");
                getFileLink(fileId,groupId);
            }catch (TelegramApiException e){
                throw new RuntimeException(e);
            }
        }
    }
    //upload video
    public void sendVideo(Long @NotNull [] groupIds, InputFile file, String caption, File thumb,String fileId){
        for (Long groupId : groupIds){
            SendVideo sendVideo = new SendVideo();
            sendVideo.setVideo(file);
            sendVideo.setChatId(groupId.toString());
            sendVideo.setCaption(caption);
            if (thumb != null){
                sendVideo.setThumb(new InputFile(thumb));
            }
            try{
                execute(sendVideo);
                getFileLink(fileId,groupId);
            }catch(TelegramApiException e){
                throw new RuntimeException(e);
            }
        }
    }
    // upload image
    public void sendImage(Long @NotNull [] groupIds, InputFile file, String caption, File thumb,String fileId){
        for(Long groupId : groupIds){
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(groupId.toString());
            sendPhoto.setPhoto(file);
            sendPhoto.setCaption(caption);
            if(thumb != null){

                //sendPhoto.setThumb(new InputFile(thumb));
            }
            try{
                assert sendPhoto == null: "sendPhotoNull";
                execute(sendPhoto);
                //execute(sendDocument);
                getFileLink( fileId,groupId);
            }catch (TelegramApiException e){
                throw new RuntimeException(e);
            }
        }
    }
    //getFileLink
    private @NotNull String getFileLink(String fileId , Long who){
        GetFile getFileMethod = new GetFile();
        getFileMethod.setFileId(fileId);
        try {
            org.telegram.telegrambots.meta.api.objects.File file = execute(getFileMethod);
            String filePath = file.getFilePath();
            String fileUrl= "https://api.telegram.org/file/bot" + getBotToken() + "/" + filePath;
            sendText(who,fileUrl);
            return fileUrl;
        }catch(TelegramApiException e){
            throw new RuntimeException(e);
        }
    }
    // menuSetting Controller
    public void sendMenu(@NotNull Long who, String txt, InlineKeyboardMarkup km) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .parseMode("HTML")
                .text(txt)
                .replyMarkup(km)
                .build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    // textMessage controller
    public void sendText(Long who, String what) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what)
                .build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
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

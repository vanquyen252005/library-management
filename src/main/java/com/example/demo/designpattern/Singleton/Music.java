package com.example.demo.designpattern.Singleton;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class Music {
    private MediaPlayer mediaPlayer;
    private static Music instance;

    private Music() {
    }
    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
        String musicFilePath = "src/main/resources/audio/" + nameSong; // Thay bằng đường dẫn tới file của bạn
        Media media = new Media(new File(musicFilePath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO); // Đưa bài nhạc về đầu
            mediaPlayer.play();              // Phát lại
            System.out.println("Lặp lại bài nhạc!");
        });
    }

    public String getNameSong() {
        return nameSong;
    }

    private String nameSong;
    public static Music getInstance() {
       if (instance == null) {
           return instance = new Music();
       } else {
           return instance;
       }
    }
public void playSong() {
        mediaPlayer.play();
}
public void pauseSong() {
        mediaPlayer.pause();
}
public MediaPlayer.Status getStatus() {
        return mediaPlayer.getStatus();
    }


}

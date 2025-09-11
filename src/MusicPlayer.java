import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.IntStream;
import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.sound.sampled.*;

// music player: enten
public  class MusicPlayer {

    static Path currentFilePath = Paths.get("").toAbsolutePath();
    static Path genresPath = currentFilePath.resolve("music");

    static Scanner scanner = new Scanner(System.in);
    static String[] genres = {
            "Classical",
            "Electronic",
            "Jazz",
            "Pop",
            "R&B",
            "Soundtrack"};


    static void start(){
        System.out.println("Genres:");

        IntStream.range(0, genres.length)
                .forEach(i -> System.out.println("[" + (i + 1) + "] " + genres[i]));

        int promptGenre;
        promptGenre = scanner.nextInt();

        switch (promptGenre){
            case 1 -> play(genres[promptGenre]);
            case 2 -> play(genres[promptGenre]);
            case 3 -> play(genres[promptGenre]);
            case 4 -> play(genres[promptGenre]);
        }
    };

    static void favorites(){};
    static void history(){};

    static String getFileExtension(String fileName){
        int pointer = fileName.lastIndexOf('.');
        return fileName.substring(pointer + 1);
    }

    static String convertFile(String file){
        Converter converter = new Converter();
        String wavFile = file.replaceAll("\\.mp3$", ".wav");
        try {
            converter.convert(file, wavFile);
            return wavFile;
        } catch (JavaLayerException e) {
            throw new RuntimeException(e);
        }
    }
    static void play(String genre){
        Path curGenrePath = currentFilePath.resolve("src").resolve("music").resolve(genre.toLowerCase());
        System.out.println(curGenrePath);

        try{
            Files.list(curGenrePath).forEach(file -> {
                String curFileString = file.toString();
                if(!getFileExtension(file.toString()).equals("wav")){
                    curFileString = convertFile(file.toString());
                }

                try {
                    File curFile = new File(curFileString);
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(curFile);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();


                    System.out.println("NO ptoblemd");

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }






            });
        } catch (IOException er){
            return;
        }
        String curGenrePathS = genresPath.resolve(genre).toString();
        System.out.println(curGenrePathS);
    };



    public static void main(String[] args) {
        // here we go again


        MusicPlayer Enten = new MusicPlayer();

        System.out.println("----------Enten-----------");
        System.out.println("Your Personal Music Player");
        System.out.println("What would you like to listen today?");
        System.out.println("---------------------------");

        int choice = -1;

        while(choice != 0){
            System.out.println("Menu");
            System.out.println("[1] Start");
            System.out.println("[2] Favorites");
            System.out.println("[3] History");
            System.out.println("[0] Exit");

            choice = scanner.nextInt();

            switch (choice){
                case 1 -> Enten.start();
                case 2 -> Enten.favorites();
                case 3 -> Enten.history();
            }
        }
        // menu..

        scanner.close();



    }
}
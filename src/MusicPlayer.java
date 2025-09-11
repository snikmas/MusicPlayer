import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;
import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import jdk.jshell.execution.Util;


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




    static void play(String genre){
        Path curGenrePath = currentFilePath.resolve("src").resolve("music").resolve(genre.toLowerCase());
        System.out.println(curGenrePath);

        try {
            // 1. create a list of songs in the folder
            List<Path> files = Files.list(curGenrePath).toList();
            int index = 0;
            Clip clip = null;

            char userRes = 'p';
            while (userRes != '0') {
                //2. play the song
                if (clip == null || !clip.isOpen()) {
                    clip = Utilities.openClip(files.get(index));
                    System.out.println("Playing " + files.get(index) + ". . .");
                }

                //3. menu
                Utilities.playerMenu();
                userRes = scanner.next().toUpperCase().charAt(0);

                switch (userRes) {
                    case '>' -> { // next
                        clip.close();
                        index = (index + 1) % files.size();
                        clip = Utilities.openClip(files.get(index));
                        clip.start();
                    }
                    case '<' -> {
                        clip.close();
                        index = (index - 1 + files.size()) % files.size();
                        clip = Utilities.openClip(files.get(index));
                        clip.start();
                    }
                    case 'P' -> clip.start();
                    case 'S' -> clip.stop();
                    case 'R' -> clip.setMicrosecondPosition(0);
                    case '0' -> {
                        clip.close();
                        System.out.println("Exiting...");
                    }
                    default -> System.out.println("Invalid input");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
            };
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
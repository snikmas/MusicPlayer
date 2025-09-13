import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;


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

        if(promptGenre >= 0 && promptGenre < genres.length){
            play(genres[promptGenre - 1]);
        } else {
            System.out.println("Invalid Genre!");
        }
    };

    static void favorites(){};
    static void history(){};




    static void play(String genre){
        Path curGenrePath = currentFilePath.resolve("src").resolve("music").resolve(genre.toLowerCase());

        try {
            // 1. create a list of songs in the folder
            List<Path> files = Files.list(curGenrePath)
                    .filter(p -> p.toString().endsWith(".mp3") || p.toString()
                    .endsWith(".wav"))
                    .toList();
            int index = 0;
            Clip clip = null;


            char userRes = 'p';
            while (userRes != '0') {
                //2. play the song
                if (clip == null || !clip.isOpen()) {
                    clip = Utilities.openClip(files.get(index));
                    clip.start();
                    System.out.println("Playing " + files.get(index).getFileName() + ". . .");
                    History.historyAdd(files.get(index).getFileName().toString());
                }

                //3. menu
                Utilities.playerMenu();
                userRes = scanner.next().toUpperCase().charAt(0);

                switch (userRes) {
                    case '>' -> { // next
                        clip.close();
                        Utilities.deleteTrash(files.get(index));
                        index = (index + 1) % files.size();
                        clip = Utilities.openClip(files.get(index));
                        clip.start();
                        System.out.println("Playing " + files.get(index).getFileName() + ". . .");
                        History.historyAdd(files.get(index).getFileName().toString());
                    }
                    case '<' -> {
                        clip.close();
                        Utilities.deleteTrash(files.get(index));
                        index = (index - 1 + files.size()) % files.size();
                        clip = Utilities.openClip(files.get(index));
                        clip.start();
                        History.historyAdd(files.get(index).getFileName().toString());
                        System.out.println("Playing " + files.get(index).getFileName() + ". . .");
                    }
                    case 'P' -> clip.start();
                    case 'S' -> clip.stop();
                    case 'R' -> clip.setMicrosecondPosition(0);
                    case 'A' -> Saved.addSave(files.get(index).getFileName().toString());
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
            System.out.println("[2] Saved");
            System.out.println("[3] History");
            System.out.println("[0] Exit");

            choice = scanner.nextInt();

            switch (choice){
                case 1 -> Enten.start();
                case 2 -> Saved.viewSave();
                case 3 -> History.viewHistory();
            }
        }
        // menu..

        scanner.close();



    }
}
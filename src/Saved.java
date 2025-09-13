import java.util.ArrayList;
import java.util.stream.IntStream;

public class Saved {
    static ArrayList<String> saved = new ArrayList<>();

    static void addSave(String file){
        int dotIndex = file.lastIndexOf('.');
        file = file.substring(0, dotIndex);

        if(saved.contains(file)){
            System.out.println("Track " + file + " already has been saved!");
        }

        saved.add(file);
        System.out.println("Track " + file + " has been added!");
    }

    static void viewSave(){

        if(saved.isEmpty()){
            System.out.println("No saved!");
        } else {
        System.out.println("Saved tracks:");
            if(!saved.isEmpty()){
                IntStream.rangeClosed(1, saved.size()).forEach(i -> {
                    System.out.println("[" + i + "] - " + saved.get(i - 1));
                });
        }
        }

        Character userInput = '1';


        while(userInput != '0'){
            System.out.println("[1] Remove A Track");
            System.out.println("[2] Remove All Tracks");
            System.out.println("\n[0] Back");

            userInput = MusicPlayer.scanner.next().charAt(0);
            switch(userInput){
                case '1' -> {
                    System.out.println("Input the number of the track to remove:");
                    int removeNum = MusicPlayer.scanner.nextInt();
                    if(removeNum < 0 || removeNum > saved.size()){
                        System.out.println("Invalid input!");
                    } else {
                      saved.remove(removeNum);
                        System.out.println("Track " + removeNum + " has been removed!");
                    };
                }
                case '2' -> {
                    saved.clear();
                    System.out.println("Tracks have been removed!");
                }
            }

        }

    }

}

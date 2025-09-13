import java.util.ArrayList;

public class History {
    static ArrayList<String> history = new ArrayList<>();

    static void historyAdd(String file){
        int dotIndex = file.lastIndexOf('.');
        history.add(file.substring(0, dotIndex)); // don't include extension .wav
    }

    static void viewHistory(){

        if(history.isEmpty()){
            System.out.println("No history!");
        } else {
            System.out.println("History: ");
            history.forEach(System.out::println);
        }

        Character userInput = "1".charAt(0);

        System.out.println("[0] Back");
        while(userInput != '0'){
            System.out.println("[1] Remove A Track From the History");
            System.out.println("[2] Clear The History");
            System.out.println("\n[0] Back");

            userInput = MusicPlayer.scanner.next().charAt(0);
            switch(userInput){
                case '1' -> {
                    System.out.println("Input the number of the track to remove:");
                    int removeNum = MusicPlayer.scanner.nextInt();
                    if(removeNum < 0 || removeNum > history.size()){
                        System.out.println("Invalid input!");
                    } else {
                        history.remove(removeNum);
                        System.out.println("Track " + removeNum + " has been removed!");
                    };
                }
                case '2' -> {
                    history.clear();
                    System.out.println("History has been cleared!");
                }
            }


        }
    }
}

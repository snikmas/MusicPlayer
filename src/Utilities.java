import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.nio.file.Path;

public class Utilities {

    static String getFileExtension(String fileName){
        int pointer = fileName.lastIndexOf('.');
        return fileName.substring(pointer + 1);
    };

    static String convertFile(String file){
        Converter converter = new Converter();
        String wavFile = file.replaceAll("\\.mp3$", ".wav");
        try {
            converter.convert(file, wavFile);
            return wavFile;
        } catch (JavaLayerException e) {
            throw new RuntimeException(e);
        }
    };

    static void playerMenu(){
        System.out.println("[<] Prev");
        System.out.println("[P] Play");
        System.out.println("[S] Stop");
        System.out.println("[R] Restart");
        System.out.println("[>] Next");
        System.out.println("[0] Exit");
    }

    static Clip openClip(Path filePath) {
        try {
            String curFileString = filePath.toString();
            if (!Utilities.getFileExtension(curFileString).equals("wav")) {
                curFileString = Utilities.convertFile(curFileString);
            }
            File curFile = new File(curFileString);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(curFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

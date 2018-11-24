package Driver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;

public class MainTest {

    public static void main (String [] args) throws Exception {
        String fileName = "/Users/minhduong/Desktop/My Projects/Java Project/SE 4348 OS/DiskAllocation/src/main/java/a.txt";

        File file = new File(fileName);
        InputStream is = new FileInputStream(file);

        byte[] byteArray = Files.readAllBytes(new File(fileName).toPath());

        System.out.println(byteArray.length);

        System.out.println();
    }

}

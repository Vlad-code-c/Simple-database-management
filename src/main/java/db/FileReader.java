package db;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {

    /**
     * Citeste scriptul, adresa caruia este salvata in variabila fileUrl, si va returna codul in format text
     * @return script from file
     * */
    public static String getSqlScript(String fileUrl) {
        String code = "";

        try {
            File file = new File(fileUrl);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                code += line + "\n";
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return code;
    }

}

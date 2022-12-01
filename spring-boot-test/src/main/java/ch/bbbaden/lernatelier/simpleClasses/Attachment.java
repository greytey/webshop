package ch.bbbaden.lernatelier.simpleClasses;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Attachment {
    private String fileNameWithType;;
    private String fileContent;
    private File file;

    public Attachment(String fileName, String fileContent, String fileType) {
        this.fileContent = fileContent;
        fileNameWithType = fileName + "." + fileType;
        file = new File(fileNameWithType);
        createFile();
        writeToFile();
    }

    private void createFile() {
        try {
            if (file.createNewFile()) {
                System.out.println("File created");
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeToFile() {
        try {
            FileWriter fileWriter = new FileWriter(fileNameWithType);
            fileWriter.write(fileContent);
            fileWriter.close();
            System.out.println("File write successful");
        } catch (IOException e) {
            System.out.println("File write failed");
            throw new RuntimeException(e);
        }
    }

    public void deleteFile() {
        if (file.delete()) {
            System.out.println("File deleted");
        } else {
            System.out.println("File delete failed");
        }
    }

    public File getFile() {
        return file;
    }
}

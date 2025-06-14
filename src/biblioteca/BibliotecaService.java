package biblioteca;


import utils.Biblioteca;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class BibliotecaService implements Biblioteca {

    private String currentPath;
    private String libName;

    public void createDiretory(String baseName) {
        String binPath = System.getProperty("user.dir") + File.separator + "bin";
        int suffix = 0;
        Path mainDir;
        String folderName;

        while (true) {
            folderName = baseName + (suffix == 0 ? "" : suffix);
            mainDir = Paths.get(binPath, folderName);
            if (!Files.exists(mainDir)) {
                break;
            }
            suffix++;
        }

        Path pathSlide = mainDir.resolve("Slide");
        Path pathBook = mainDir.resolve("Book");
        Path pathNotes = mainDir.resolve("Notes");

        try {
            Files.createDirectories(mainDir);
            Files.createDirectories(pathSlide);
            Files.createDirectories(pathBook);
            Files.createDirectories(pathNotes);
            Files.createFile(Paths.get(binPath).resolve(folderName+".ser"));
            System.out.println("Biblioteca criado: " + baseName);
            setCurrentPath(mainDir.toString());
            setLibName(baseName);
        } catch (IOException e) {
            System.err.println("Erro ao criar diretórios: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public String getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(String currentPath) {
        this.currentPath = currentPath;
    }

    public String getLibName() {
        return libName;
    }

    public void setLibName(String libName) {
        this.libName = libName;
    }


}

package models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public abstract class ArquivoPDF implements Serializable {
    private String title;
    private String author;
    private String type;
    private String path;
    private String finalPath;


    public ArquivoPDF(String title, String author, String type, String path) {
        this.author = author;
        this.title = title;
        this.type = type;
        this.path = path;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return  type +
                " ( title = "  + title  +
                ", author = " + author +
                ", type = " + type +
                ", path = " + finalPath  +
                " )";
    }

    public void setFinalPath(String finalPath) {
        this.finalPath = finalPath;
    }

    public String getFinalPath() {
        return finalPath;
    }

    public void copyToDirectory(String title, String type, String originString, String endString){
        Path origin = Paths.get(originString);
        Path end = Paths.get(endString + "\\"+ type +"\\" + title + ".pdf" );

        try {
            Files.copy(origin, end, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Arquivo copiado com sucesso para " + end);
            setFinalPath(end.toString());


        } catch (NoSuchFileException e){} catch (Exception e) {
            System.err.println("Erro ao copiar o arquivo: " + e.getMessage());
            System.out.println(originString + "\n" + endString + "\n" + type);
            e.printStackTrace();
            // erro nas barras e nos espaços file.NoSuchFileException
        }

    }

    public void deleteToDirectory(String title, String type, String libName){
        Path pathToDelete = Paths.get(libName + "\\" + type + "\\" + title + ".pdf");
        try {
            System.out.println("Arquivo deletado");
            Files.deleteIfExists(pathToDelete);
            setFinalPath(null);
        } catch (Exception e) {
            System.err.println("Erro ao deletar arquivo");
        }

    }

}

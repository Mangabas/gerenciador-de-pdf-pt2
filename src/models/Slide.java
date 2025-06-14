package models;

import java.nio.file.Path;

public class Slide extends ArquivoPDF{

    private String clasroomName;

    public Slide(String title, String clasroomName, String author, String path) {
        super(title, author, "Slide", path);
        this.clasroomName = clasroomName;
    }

    public String getClasroomName() {
        return clasroomName;
    }

    public void setClasroomName(String clasroomName) {
        this.clasroomName = clasroomName;
    }
}

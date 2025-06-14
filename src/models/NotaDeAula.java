package models;

import java.nio.file.Path;

public class NotaDeAula extends ArquivoPDF{
    private String subjectName;
    private String subtitle;

    public NotaDeAula(String title, String subtitle, String subjectName, String author, String path) {
        super(title, author, "Notes", path);
        this.subtitle = subtitle;
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}

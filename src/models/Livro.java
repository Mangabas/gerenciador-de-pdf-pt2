package models;

import java.nio.file.Path;
import java.time.LocalDate;

public class Livro extends ArquivoPDF{
    private String subtitle;
    private int date;
    private String knowledge;

    public Livro(String author, String title,String subtitle, String knowledge,int date, String path) {
        super(title, author, "Book", path);
        this.subtitle = subtitle;
        this.knowledge = knowledge;
        this.date = date;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }

    public String toStringBibTeX() {
        String key = getAuthor().split(" ")[getAuthor().split(" ").length - 1].toLowerCase() + date;

        return "@book{" + key + ",\n" +
                "  author    = {" + getAuthor() + "},\n" +
                "  title     = {" + getTitle() + (subtitle != null && !subtitle.isEmpty() ? ": " + subtitle : "") + "},\n" +
                "  year      = {" + date + "},\n" +
                "  keywords  = {" + knowledge + "},\n" +
                "  note      = {Disponível em: " + getPath() + "}\n" +
                "}";
    }

    @Override
    public String toString() {
        return getType() +
                " ( title = "  + getTitle()  +
                ", author = " + getAuthor() +
                ", path = " + getFinalPath()  +
                " )";
    }
}

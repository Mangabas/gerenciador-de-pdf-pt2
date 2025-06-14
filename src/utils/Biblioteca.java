package utils;

public interface Biblioteca {
    void createDiretory(String nome);
    String getLibName();
    void setLibName(String nome);
    String getCurrentPath();
    void setCurrentPath(String path);
}

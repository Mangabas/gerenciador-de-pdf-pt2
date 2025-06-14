package exception;

public class ColecaoVaziaException extends RuntimeException {
    public ColecaoVaziaException() {
        super("A coleção está vazia e não pode ser lida.");
    }
}
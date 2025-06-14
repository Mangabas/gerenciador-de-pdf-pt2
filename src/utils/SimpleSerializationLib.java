package utils;

import java.io.*;

public class SimpleSerializationLib {

    /**
     * Salva um objeto serializável em um arquivo.
     * @param obj Objeto que implementa Serializable
     * @param filePath Caminho do arquivo onde será salvo
     * @throws IOException Caso ocorra erro de escrita
     */
    public static void writeObjectToFile(Object obj, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(obj);
        }
    }

    /**
     * Lê um objeto serializado de um arquivo.
     * @param filePath Caminho do arquivo para leitura
     * @return Objeto desserializado
     * @throws IOException Caso ocorra erro de leitura
     * @throws ClassNotFoundException Caso a classe do objeto não seja encontrada
     */
    public static Object readObjectFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return ois.readObject();
        }
    }
}

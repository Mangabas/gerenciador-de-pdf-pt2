package utils;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LibMethods implements Serializable {

    public static List<String> checkLib() throws IOException, ClassNotFoundException {
        List<String> library = new ArrayList<>();
        try{
            library = (List<String>) SimpleSerializationLib.readObjectFromFile(System.getProperty("user.dir") + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator") + "lib.ser");
        }catch (FileNotFoundException fileNotFoundException){
            Files.createFile(Paths.get(System.getProperty("user.dir") + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator") + "lib.ser"));

        }
        return library;

    }

    public static void checkBin() throws IOException {
        if (!Files.exists(Paths.get(System.getProperty("user.dir") + System.getProperty("file.separator") + "bin"))) {
            Files.createDirectories(Paths.get(System.getProperty("user.dir") + System.getProperty("file.separator") + "bin"));
        }
    }

    public static Optional<String> changeLib(List<String> libs){
        Scanner scanner = new Scanner(System.in);
        while (true){
            CollectionMethods.distinctList(libs).forEach(System.out::println);
            System.out.println("Digite a biblioteca que deseja selecionar");
            String input = scanner.nextLine();
            String finalInput = input;
            try {
                Optional<String> libSearch = libs.stream()
                        .filter(lib -> lib.equalsIgnoreCase(finalInput))
                        .findFirst();

                return libSearch;

            }catch (Exception e){
                System.err.println("Digite um nome correto");
            }

        }

    }
}

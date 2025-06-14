package utils;

import models.ArquivoPDF;
import models.FilterDePDF;
import models.Livro;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CollectionMethods implements Serializable {

    public static <T> List<T> distinctList(List<T> input) {
        List<T> lista = input.stream().distinct().collect(Collectors.toList());
        return lista;
    }


    public static List<String> checkCollection() throws IOException, ClassNotFoundException {
        List<String> collection = new ArrayList<>();
        collection = (List<String>) SimpleSerializationLib.readObjectFromFile(System.getProperty("user.dir") + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator")+ "Collections" + System.getProperty("file.separator") +"collections.ser");

        return collection;
    }

    public static String collection(List<ArquivoPDF> list, String type, int limit, String name){
        String typeR;
        switch (type.toLowerCase()) {
            case "book", "livro" -> typeR = "Book";
            case "slide" -> typeR = "Slide";
            case "notas", "notes" -> typeR = "Notes";
            default -> {
                return "Erro ao inicializar tipo";
            }
        }
        String title = "";
        List<String> titles = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        for(ArquivoPDF pdf : (list)){
            if (pdf.getType().equals(typeR)){
                System.out.println(pdf.getTitle() + " " + pdf.getAuthor());
            }
        }

        while (!(title.equals("sair"))){
            System.out.println("digite o titulo das entradas ou 'sair' para finalizar ");
            title = scanner.nextLine();
            titles.add(title);
        }
        distinctList(titles).forEach(System.out::println);

        List<ArquivoPDF> typeList = list.stream()
                .limit(limit)
                .filter(x -> x.getType().equals(typeR))
                .limit(limit)
                .collect(Collectors.toList());

        List<ArquivoPDF> filterList = new ArrayList<>();
        for (ArquivoPDF arquivo:typeList){
            if(titles.contains(arquivo.getTitle().toLowerCase())){
                filterList.add(arquivo);
            }
        }

        try {
            SimpleSerializationLib.writeObjectToFile(filterList, System.getProperty("user.dir") + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator")+ "Collections" + System.getProperty("file.separator") + name +".ser");
            System.out.println("Coleção criada com sucesso\n");
            filterList.forEach(System.out::println);
        } catch (Exception e){
            System.err.println("Erro ao serializar coleção");
        }

        return name;
    }

    public static void checkCollectionFile() throws IOException {
        String pathDirectory = System.getProperty("user.dir") + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator") + "Collections";
        String pathFile = System.getProperty("user.dir") + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator") + "Collections" + System.getProperty("file.separator") + "collections.ser";
        if (!Files.exists(Paths.get(pathDirectory))){
            Files.createDirectories(Paths.get(pathDirectory));
        }
        if (!Files.exists(Paths.get(pathFile))){
            Files.createFile(Paths.get(pathFile));
        }
    }

    public static List<ArquivoPDF> addTitle (String title, String collection, List<ArquivoPDF> list, String type) throws IOException, ClassNotFoundException {
        String typeR = "";
        if (type.toLowerCase().equals("book") || type.toLowerCase().equals("livro")){
            typeR = "Book";
        } else if (type.toLowerCase().equals("slide")) {
            typeR = "Slide";
        } else if (type.toLowerCase().equals("notas") || type.toLowerCase().equals("notes")) {
            typeR = "Notes";
        }else{
            System.out.println("Erro ao inicializar tipo");
        }

        List<ArquivoPDF> tempAdditions = new ArrayList<>();

        List<ArquivoPDF> currentCollection = (List<ArquivoPDF>) SimpleSerializationLib.readObjectFromFile(System.getProperty("user.dir") + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator")+ "Collections" + System.getProperty("file.separator") + collection +".ser");
        for (ArquivoPDF arquivoPDF : currentCollection) {
                 for (ArquivoPDF arquivoPDF1: list){
                     if(arquivoPDF1.getTitle().toLowerCase().equals(title) && arquivoPDF1.getType().equals(typeR)){
                         tempAdditions.add(arquivoPDF1);
                 }

                 }
             }
        currentCollection.addAll(tempAdditions);

        List<ArquivoPDF> filterList = currentCollection.stream()
                                                        .distinct()
                                                        .collect(Collectors.toList());
        SimpleSerializationLib.writeObjectToFile(filterList,System.getProperty("user.dir") + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator")+ "Collections" + System.getProperty("file.separator") + collection +".ser");

        return filterList;

    }

    public static List<ArquivoPDF> removeByTitle(String title, String collection)
            throws IOException, ClassNotFoundException {

        String filePath = System.getProperty("user.dir") +
                System.getProperty("file.separator") + "bin" +
                System.getProperty("file.separator") + "Collections" +
                System.getProperty("file.separator") + collection + ".ser";

        List<ArquivoPDF> currentCollection = (List<ArquivoPDF>) SimpleSerializationLib.readObjectFromFile(filePath);

        List<ArquivoPDF> filteredCollection = currentCollection.stream()
                .filter(arquivoPDF -> !arquivoPDF.getTitle().toLowerCase().equals(title))
                .distinct()
                .collect(Collectors.toList());

        SimpleSerializationLib.writeObjectToFile(filteredCollection, filePath);

        return filteredCollection;
    }

    public static void CollectionToBibTex(List<ArquivoPDF> list, String path) throws IOException {
        FilterDePDF<Livro> filterDePDF = new FilterDePDF<>(Livro.class);
        List<Livro> books = filterDePDF.filter(list);
        List<String> booksFiltered = new ArrayList<>();
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        String bib;

        for (Livro book : books){
        booksFiltered.add(book.toStringBibTeX());
        }
        List <String > distinctBooks = booksFiltered.stream().distinct().collect(Collectors.toList());
        for (String b: distinctBooks){
            bib = b.replace("\\", "/");
            writer.write(bib);
            writer.newLine();
        }
        writer.close();
        System.out.println("BibTex criado com sucesso");
    }

    public static void zipCollection(String zipDestino, String name) throws IOException, ClassNotFoundException {
        List<ArquivoPDF> colecao = new ArrayList<>();
        // Lê a lista de ArquivoPDF do arquivo serializado
        try {
            colecao = (List<ArquivoPDF>) SimpleSerializationLib.readObjectFromFile(System.getProperty("user.dir") + File.separator + "bin" + File.separator + "Collections" + File.separator + name + ".ser");
        }catch (FileNotFoundException e){
            System.err.println("Digite o nome da coleção corretamente");
        }
        // Cria pasta temporária onde os PDFs serão copiados
        Path pastaTemporaria = Files.createTempDirectory("pdfs_temp");

        // Copia cada arquivo PDF para a pasta temporária
        for (ArquivoPDF pdf : colecao) {
            Path origem = Paths.get(pdf.getFinalPath());
            if (Files.exists(origem)) {
                Path destino = pastaTemporaria.resolve(origem.getFileName());
                Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
            } else {
                System.err.println("Arquivo não encontrado: " + origem);
            }
        }

        // Cria o arquivo ZIP
        Path zipPath = Paths.get(zipDestino);
        Files.createDirectories(zipPath.getParent()); // Garante que a pasta de destino existe

        try (ZipOutputStream zipOut = new ZipOutputStream(Files.newOutputStream(zipPath))) {
            Files.walk(pastaTemporaria)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        try {
                            Path caminhoRelativo = pastaTemporaria.relativize(path);
                            ZipEntry zipEntry = new ZipEntry(caminhoRelativo.toString());
                            zipOut.putNextEntry(zipEntry);
                            Files.copy(path, zipOut);
                            zipOut.closeEntry();
                        } catch (IOException e) {
                            System.err.println("Erro ao adicionar arquivo ao zip: " + path + " -> " + e.getMessage());
                        }
                    });
        }

        // Opcional: deletar pasta temporária após uso
        deleteRecursively(pastaTemporaria);
        System.out.println("ZIP criado com sucesso: " + zipPath.toString());
    }

    // Função auxiliar para deletar a pasta temporária
    private static void deleteRecursively(Path path) throws IOException {
        if (Files.exists(path)) {
            Files.walk(path)
                    .sorted((a, b) -> b.compareTo(a)) // deleta arquivos antes de pastas
                    .forEach(p -> {
                        try {
                            Files.delete(p);
                        } catch (IOException e) {
                            System.err.println("Não foi possível deletar: " + p);
                        }
                    });
        }
    }

    public static Optional<String> changeCollection(List<String> allCollections){
        Scanner scanner = new Scanner(System.in);
        while (true){
            allCollections.forEach(System.out::println);
            System.out.println("Digite a coleção que deseja selecionar");
            String input = scanner.nextLine();
            String finalInput = input;
            try {
                Optional<String> colSearch = allCollections.stream()
                        .filter(lib -> lib.equalsIgnoreCase(finalInput))
                        .findFirst();

                return colSearch;

            }catch (Exception e){
                System.err.println("Digite um nome correto");
            }

        }

    }

    public static void listByAuthor(List<String> allCollections, String author) throws IOException, ClassNotFoundException {
        List <ArquivoPDF> authors;

        for (String string : allCollections){
            authors = (List<ArquivoPDF>) SimpleSerializationLib.readObjectFromFile(System.getProperty("user.dir") + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator")+ "Collections" + System.getProperty("file.separator") + string +".ser");
            for (ArquivoPDF arquivoPDF: authors){
                if(arquivoPDF.getAuthor().toLowerCase().equals(author)){
                    System.out.println(arquivoPDF);;
                }
            }
        }

    }

    public static void listBytype(List<String> allCollections, String type) throws IOException, ClassNotFoundException {
        String typeR = "";
        switch (type.toLowerCase()) {
            case "book", "livro" -> typeR = "Book";
            case "slide" -> typeR = "Slide";
            case "notas", "notes" -> typeR = "Notes";
            default -> System.out.println("Erro ao inicializar tipo");
        }

        List <ArquivoPDF> pdfs;
        List <ArquivoPDF> typeFilter = new ArrayList<>();

        for (String string : allCollections){
            pdfs = (List<ArquivoPDF>) SimpleSerializationLib.readObjectFromFile(System.getProperty("user.dir") + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator")+ "Collections" + System.getProperty("file.separator") + string +".ser");
            for (ArquivoPDF arquivoPDF: pdfs){
                if(arquivoPDF.getType().toLowerCase().equals(typeR)){
                    System.out.println(arquivoPDF);
                }
            }
        }
    }
}


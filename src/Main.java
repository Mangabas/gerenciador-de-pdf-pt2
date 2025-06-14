import biblioteca.BibliotecaService;
import exception.BibliotecaNaoEncontradaException;
import exception.ColecaoVaziaException;
import models.*;
import utils.CollectionMethods;
import utils.LibMethods;
import utils.SimpleSerializationLib;
import java.io.EOFException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;


public class Main {

    private static Path pathBin = Paths.get(System.getProperty("user.dir") + System.getProperty("file.separator") + "bin");
    private static BibliotecaService bibliotecaService = new BibliotecaService();
    private static List<ArquivoPDF> list = new ArrayList<>();
    private static List<String> libs = new ArrayList<>();
    private static List<String> allCollections = new ArrayList<>();
    private static List<ArquivoPDF> currentCollection;


    public static void main(String[] args) throws IOException, ClassNotFoundException {

        if (args.length == 0) {
            functions();
            return;
        }


        String command = args[0].toLowerCase();

        // Verifica a se existe os arquivos de persistência e lê eles
        try {
            LibMethods.checkBin();
            libs = LibMethods.checkLib();
            bibliotecaService.setLibName(libs.get(0));
            bibliotecaService.setCurrentPath(pathBin + System.getProperty("file.separator") + bibliotecaService.getLibName());
            list = (List<ArquivoPDF>) SimpleSerializationLib.readObjectFromFile(pathBin + System.getProperty("file.separator") + bibliotecaService.getLibName() + ".ser");
            CollectionMethods.checkCollectionFile();
            allCollections = CollectionMethods.checkCollection();

            if (libs.isEmpty()) {
                throw new BibliotecaNaoEncontradaException("Nenhuma biblioteca encontrada no sistema.");
            }

        }catch (BibliotecaNaoEncontradaException e) {
            System.err.println(e.getMessage());
        }
        catch (EOFException exception){}
        catch (IndexOutOfBoundsException exception){}

        Main.help();


            if (bibliotecaService.getLibName() == null){
                System.out.println("Não existe nenhuma bilbioteca.\n");
                command = "create-lib";
            }

            switch (command){

                case "create-lib":
                    if ( args.length < 2) {
                        System.out.println("Erro: Nome da biblioteca é obrigatório");
                        System.out.println("Uso: java Main create-lib <nome>");
                        return;
                    }
                    bibliotecaService.createDiretory(args[1]);
                    list.clear();
                    libs.add(bibliotecaService.getLibName());
                    SimpleSerializationLib.writeObjectToFile( libs,System.getProperty("user.dir") + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator") + "lib.ser");
                    break;

                case "add-book":
                    if (args.length < 7) {
                        System.out.println("Erro: Parâmetros insuficientes para criar livro");
                        System.out.println("Uso: java Main add-book <autor> <titulo> <subtitulo> <area> <ano> <caminho>");
                        return;
                    }
                    Livro livro = new Livro(args[1], args[2], args[3], args[4], Integer.parseInt(args[5]), args[6]);
                    livro.copyToDirectory(livro.getTitle(), livro.getType(), livro.getPath(), bibliotecaService.getCurrentPath());
                    list.add(livro);
                    SimpleSerializationLib.writeObjectToFile(list, pathBin + System.getProperty("file.separator") + bibliotecaService.getLibName() + ".ser");
                    break;
                case "add-slide":
                    if (args.length < 5) {
                        System.out.println("Erro: Parâmetros insuficientes para criar slide");
                        System.out.println("Uso: java Main add-slide <titulo> <disciplina> <autor> <caminho>");
                        return;
                    }
                    Slide slide = new Slide(args[1], args[2], args[3], args[4]);
                    slide.copyToDirectory(slide.getTitle(), slide.getType(), slide.getPath(), bibliotecaService.getCurrentPath());
                    list.add(slide);
                    SimpleSerializationLib.writeObjectToFile(list, pathBin + System.getProperty("file.separator") + bibliotecaService.getLibName() + ".ser");
                    break;
                case "add-notes":
                    if (args.length < 6) {
                        System.out.println("Erro: Parâmetros insuficientes para criar notas");
                        System.out.println("Uso: java Main add-notes <titulo> <subtitulo> <disciplina> <autor> <caminho>");
                        return;
                    }
                    NotaDeAula notaDeAula = new NotaDeAula(args[1], args[2], args[3], args[4], args[5]);
                    notaDeAula.copyToDirectory(notaDeAula.getTitle(), notaDeAula.getType(), notaDeAula.getPath(), bibliotecaService.getCurrentPath());
                    list.add(notaDeAula);
                    SimpleSerializationLib.writeObjectToFile(list, pathBin + System.getProperty("file.separator") + bibliotecaService.getLibName() + ".ser");
                    break;
                case "list-pdf":
                    try {
                        list = (List<ArquivoPDF>) SimpleSerializationLib.readObjectFromFile(pathBin + System.getProperty("file.separator") + bibliotecaService.getLibName() + ".ser");
                        list.forEach(System.out::println);
                        break;
                    } catch (EOFException e){
                        System.err.println("A biblioteca nao possui nenhum pdf adicionado adicionado");
                        break;
                    }

                case "change-lib":
                    libs = LibMethods.checkLib();
                    Optional<String> libSearch = LibMethods.changeLib(libs);
                    libs.add(0,libSearch.get());
                    SimpleSerializationLib.writeObjectToFile(libs, pathBin + System.getProperty("file.separator") + "lib.ser");
                    bibliotecaService.setCurrentPath(pathBin + System.getProperty("file.separator") + libSearch.get());
                    list.clear();
                    try{
                        list = (List<ArquivoPDF>) SimpleSerializationLib.readObjectFromFile(pathBin + System.getProperty("file.separator") + bibliotecaService.getLibName() + ".ser");
                    }
                    catch (EOFException e){
                        System.out.println("Esta biblioteca nao possui pdfs");
                    }

                    System.out.println("Biblioteca " + libSearch.get() + " selecionada");

                    break;

                case "change-collection":
                    allCollections = CollectionMethods.checkCollection();
                    Optional<String> collectionSearch = CollectionMethods.changeCollection(allCollections);
                    allCollections.add(0,collectionSearch.get());
                    SimpleSerializationLib.writeObjectToFile(allCollections, pathBin + System.getProperty("file.separator")+ "Collections" + System.getProperty("file.separator") + "collections.ser");
                    try{
                        currentCollection = (List<ArquivoPDF>) SimpleSerializationLib.readObjectFromFile(pathBin + System.getProperty("file.separator") + allCollections.get(0) + ".ser");
                    }
                    catch (EOFException e){
                        e.getMessage();
                    }

                    System.out.println("Coleção " + collectionSearch.get() + " selecionada");
                    break;

                case "create-collection":
                    if (args.length < 4) {
                        System.out.println("Erro: Parâmetros insuficientes");
                        System.out.println("Uso: java Main create-collection <tipo> <numero> <nome>");
                        return;
                    }
                    CollectionMethods.checkCollectionFile();
                    allCollections.add(0, CollectionMethods.collection(list,args[1],Integer.parseInt(args[2]),args[3]));
                    SimpleSerializationLib.writeObjectToFile(allCollections,System.getProperty("user.dir") + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator")+ "Collections" + System.getProperty("file.separator") +"collections.ser");
                    break;


                case "read-collection":
                    try {
                        currentCollection = (List<ArquivoPDF>) SimpleSerializationLib.readObjectFromFile(pathBin + System.getProperty("file.separator") + "Collections" + System.getProperty("file.separator") + allCollections.get(0) + ".ser");
                        if (currentCollection.isEmpty()) {
                            throw new ColecaoVaziaException();
                        }
                        CollectionMethods.distinctList(currentCollection).forEach(System.out::println);
                    } catch (ColecaoVaziaException e){
                        System.err.println(e.getMessage());
                    }
                    break;
                case "add-to-collection":
                    if (args.length < 4) {
                        System.out.println("Erro: Parâmetros insuficientes");
                        System.out.println("Uso: java Main add-to-collection <titulo> <nome da coleção> <tipo>");
                        return;
                    }
                    currentCollection = CollectionMethods.addTitle(args[1],args[2],list, args[3]);
                    CollectionMethods.distinctList(currentCollection).forEach(System.out::println);
                    break;

                case "list-libs":
                    List <String> allLibs = (List<String>) SimpleSerializationLib.readObjectFromFile(pathBin + System.getProperty("file.separator") + "lib.ser");
                    CollectionMethods.distinctList(allLibs).forEach(System.out::println);
                    break;

                case "delete-to-collection":
                    if (args.length < 3) {
                        System.out.println("Erro: Parâmetros insuficientes");
                        System.out.println("Uso: java Main delete-to-collection <titulo> <nome da coleção>");
                        return;
                    }
                    currentCollection = CollectionMethods.removeByTitle(args[1],args[2]);
                    CollectionMethods.distinctList(currentCollection).forEach(System.out::println);
                    break;

                case "to-bibtex":
                    if (args.length < 3) {
                        System.out.println("Erro: Parâmetros insuficientes");
                        System.out.println("Uso: java Main to-bibtex <caminho> <nome do arquivo>");
                        return;
                    }
                    CollectionMethods.CollectionToBibTex(list, args[1] + args[2] + ".bib");
                    break;

                case "to-zip":
                    if (args.length < 3) {
                        System.out.println("Erro: Parâmetros insuficientes");
                        System.out.println("Uso: java Main to-zip <caminho> <nome da colecao>");
                        return;
                    }
                    CollectionMethods.zipCollection(args[1] + ".zip",args[2]);
                    break;
                case "collection-by-type":
                    if (args.length < 2) {
                        System.out.println("Erro: Parâmetros insuficientes");
                        System.out.println("Uso: java Main collection-by-type <tipo>");
                        return;
                    }
                    CollectionMethods.listBytype(allCollections, args[1]);
                    break;
                case "collection-by-author":
                    if (args.length < 2) {
                        System.out.println("Erro: Parâmetros insuficientes");
                        System.out.println("Uso: java Main collection-by-author <author>");
                        return;
                    }
                    CollectionMethods.listByAuthor(allCollections,args[1]);
                    break;

                default:
                    System.out.println("Digite uma opção válida\n");
                    functions();
            }



}

public static void functions(){
    System.out.println(
            "Escolha uma opção\n" +
            "create-lib\n" +
            "add-book\n" +
            "add-slide\n" +
            "add-notes\n" +
            "list-pdf\n" +
            "change-lib\n" +
            "create-collection\n" +
            "read-collection\n" +
            "add-to-collection\n" +
            "delete-to-collection\n" +
            "to-bibtex\n" +
            "to-zip\n" +
            "exit\n");
}
public static void help(){
    System.out.println("""
            """);
}

}

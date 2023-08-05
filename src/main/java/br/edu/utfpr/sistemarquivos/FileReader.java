package br.edu.utfpr.sistemarquivos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReader {
    private static String VALID_EXTENSION = ".txt";
    public void read(Path path) {
        // TODO implementar a leitura dos arquivos do PATH aqui
        try {
            if(!path.endsWith(VALID_EXTENSION)){
                throw new UnsupportedOperationException("Extension not supported.");
            }
            Files.readAllLines(path).forEach(System.out::println);
        } catch (IOException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }
}

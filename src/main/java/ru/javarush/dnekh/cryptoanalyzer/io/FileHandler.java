package ru.javarush.dnekh.cryptoanalyzer.io;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * This class provides methods for reading text from a file and writing text to a file using Java NIO.
 */
public class FileHandler {

    private final Charset charset = StandardCharsets.UTF_8;

    /**
     * Reads the entire text content from the specified file using Java NIO.
     *
     * @param filePath the path to the file
     * @return the text content of the file
     * @throws IOException if an I/O error occurs reading from the file
     */
    public String readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path, charset);
        return String.join(System.lineSeparator(), lines);
    }

    /**
     * Writes the specified text to the specified file using Java NIO.
     * If the file already exists, it will be overwritten.
     *
     * @param filePath the path to the file
     * @param content  the text content to write to the file
     * @throws IOException if an I/O error occurs writing to the file
     */
    public void writeFile(String filePath, String content) throws IOException {
        Path path = Paths.get(filePath);
        Files.writeString(path, content, charset, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}

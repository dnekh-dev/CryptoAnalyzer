package ru.javarush.dnekh.cryptoanalyzer.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * This class provides methods for reading text from a file and writing text to a file using Java NIO.
 */
public class FileHandler {

    private final Charset charset = StandardCharsets.UTF_8;

    /**
     * Reads the entire text content from the specified file using Java NIO with buffered reader.
     *
     * @param filePath the path to the file
     * @return the text content of the file
     * @throws IOException if an I/O error occurs reading from the file
     */
    public String readFile(String filePath) throws IOException, InvalidPathException {
        Path path = Paths.get(filePath);
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }

        return content.toString();
    }

    /**
     * Writes the specified text to the specified file using Java NIO with buffered writer.
     * If the file already exists, it will be overwritten.
     *
     * @param filePath the path to the file
     * @param content  the text content to write to the file
     * @throws IOException if an I/O error occurs writing to the file
     */
    public void writeFile(String filePath, String content) throws IOException {
        Path path = Paths.get(filePath);

        try (BufferedWriter writer = Files.newBufferedWriter(path, charset, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(content);
        }
    }
}

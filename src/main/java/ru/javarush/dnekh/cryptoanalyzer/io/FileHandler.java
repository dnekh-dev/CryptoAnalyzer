package ru.javarush.dnekh.cryptoanalyzer.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class provides methods for reading text from a file and writing text to a file.
 */
public class FileHandler {

    /**
     * Reads the entire text content from the specified file using a buffered reader.
     *
     * @param filePath the path to the file
     * @return the text content of the file
     * @throws IOException if an I/O error occurs reading from the file
     */
    public String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }

        return content.toString();
    }

    /**
     * Writes the specified text to the specified file using a buffered writer.
     * If the file already exists, it will be overwritten.
     *
     * @param filePath the path to the file
     * @param content  the text content to write to the file
     * @throws IOException if an I/O error occurs writing to the file
     */
    public void writeFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }
}

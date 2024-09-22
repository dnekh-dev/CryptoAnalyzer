package ru.javarush.dnekh.cryptoanalyzer.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
    private static final String DEFAULT_OUTPUT_FILENAME = "result_output.txt";

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
     * If the file path is not specified, it writes to a default file in the same directory
     * as the source file with the name "result_output.txt".
     *
     * @param sourceFilePath the path to the source file
     * @param outputFilePath the path to the file
     * @param content  the text content to write to the file
     * @throws IOException if an I/O error occurs writing to the file
     */
    public void writeFile(String sourceFilePath, String outputFilePath, String content) throws IOException {
        Path outputPath = getOutputFilePath(sourceFilePath, outputFilePath);

        try (BufferedWriter writer = Files.newBufferedWriter(outputPath, charset, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(content);
        }
    }

    /**
     * Generates the output file path by appending a suffix to the original file name.
     *
     * @param originalFilePath the path of the original file
     * @param operation        the operation being performed ("encrypt", "decrypt", "bruteforce_decrypt")
     * @return the generated output file path
     */
    public String generateOutputFilePath(String originalFilePath, String operation) {
        File originalFile = new File(originalFilePath);
        String parentDirectory = originalFile.getParent();
        String originalFileName = originalFile.getName();

        String suffix = switch (operation) {
            case "encrypt" -> "_encrypted";
            case "decrypt" -> "_decrypted";
            case "bruteforce_decrypt" -> "_bruteforce_decrypted";
            default -> "_result";
        };

        int extensionIndex = originalFileName.lastIndexOf(".");
        String newFileName;
        if (extensionIndex > 0) {
            newFileName = originalFileName.substring(0, extensionIndex) + suffix + originalFileName.substring(extensionIndex);
        } else {
            newFileName = originalFileName + suffix;
        }

        return parentDirectory + File.separator + newFileName;
    }

    /**
     * Determines the output file path based on the source file path or a provided output file path.
     *
     * @param sourceFilePath the path to the source file
     * @param outputFilePath the path to the output file or null/empty if not specified
     * @return the resolved output file path
     */
    private Path getOutputFilePath(String sourceFilePath, String outputFilePath) {
        if (outputFilePath == null || outputFilePath.isEmpty()) {
            Path sourcePath = Paths.get(sourceFilePath);
            Path sourceDirectory = sourcePath.getParent();

            if (sourceDirectory == null) {
                return Paths.get(DEFAULT_OUTPUT_FILENAME);
            } else {
                return sourceDirectory.resolve(DEFAULT_OUTPUT_FILENAME);
            }
        } else {
            return Paths.get(outputFilePath);
        }
    }
}
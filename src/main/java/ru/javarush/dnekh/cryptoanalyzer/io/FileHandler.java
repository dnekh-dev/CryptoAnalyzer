package ru.javarush.dnekh.cryptoanalyzer.io;

import ru.javarush.dnekh.cryptoanalyzer.service.OperationSuffix;
import ru.javarush.dnekh.cryptoanalyzer.utils.UserInputUtils;

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
     * The output file path is generated based on the source file path and the operation suffix.
     *
     * @param sourceFilePath the path to the source file
     * @param operationSuffix the operation suffix enum
     * @param content  the text content to write to the file
     * @throws IOException if an I/O error occurs writing to the file
     */
    public void writeFile(String sourceFilePath, OperationSuffix operationSuffix, String content) throws IOException {
        Path outputPath = UserInputUtils.getValidatedOutputFilePath(sourceFilePath, operationSuffix.getSuffix());

        try (BufferedWriter writer = Files.newBufferedWriter(outputPath, charset, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(content);
        }
    }

    /**
     * Generates the output file path by appending a suffix from OperationSuffix enum to the original file name.
     *
     * @param originalFilePath the path of the original file
     * @param operationSuffix  the operation suffix enum
     * @return the generated output file path
     */
    public String generateOutputFilePath(String originalFilePath, OperationSuffix operationSuffix) {
        File originalFile = new File(originalFilePath);
        String parentDirectory = originalFile.getParent();
        String originalFileName = originalFile.getName();

        String suffix = operationSuffix.getSuffix();

        int extensionIndex = originalFileName.lastIndexOf(".");
        String newFileName;
        if (extensionIndex > 0) {
            newFileName = originalFileName.substring(0, extensionIndex) + suffix + originalFileName.substring(extensionIndex);
        } else {
            newFileName = originalFileName + suffix;
        }

        return parentDirectory + File.separator + newFileName;
    }
}
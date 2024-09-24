package ru.javarush.dnekh.cryptoanalyzer.utils;

import ru.javarush.dnekh.cryptoanalyzer.exception.ErrorHandler;
import ru.javarush.dnekh.cryptoanalyzer.validation.InputValidator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Utility class for handling user input operations such as file path and key input.
 */
public class UserInputUtils {

    private static final Scanner scanner = new Scanner(System.in);

    private static final String ENTER_FILE_PATH = "Enter the file path to %s: ";
    private static final String ENTER_KEY_OPERATION = "Enter the key for %s: ";

    /**
     * Prompts the user for a valid file path and validates it.
     *
     * @param operation the operation being performed (e.g., "encrypt", "decrypt")
     * @return the validated file path or null if invalid
     */
    public static String getInputFilePath(String operation) {
        String filePath;
        while (true) {
            System.out.printf(ENTER_FILE_PATH, operation);
            filePath = scanner.nextLine().trim();
            if (InputValidator.isValidFilePath(filePath)) {
                return filePath;
            }
            ErrorHandler.showError(ErrorHandler.INVALID_FILE_PATH);
            System.out.println();
        }
    }

    /**
     * Prompts the user to input a key for encryption or decryption.
     *
     * @param operation the operation being performed (e.g., "encrypt", "decrypt")
     * @return the valid integer key entered by the user
     */
    public static int getKeyFromUser(String operation) {
        int key;
        while (true) {
            System.out.printf(ENTER_KEY_OPERATION, operation);
            try {
                key = Integer.parseInt(scanner.nextLine().trim());
                return key;
            } catch (NumberFormatException e) {
                ErrorHandler.showError(ErrorHandler.INVALID_KEY_ENTERED);
                System.out.println();
            }
        }
    }


    public static Path getValidatedOutputFilePath(String sourceFilePath, String operationSuffix) {
        Path sourcePath = Paths.get(sourceFilePath);
        Path sourceDirectory = sourcePath.getParent();

        String newFileName = buildResultFileName(operationSuffix, sourcePath);
        Path outputPath = (sourceDirectory == null) ? Paths.get(newFileName) : sourceDirectory.resolve(newFileName);

        Path outputDirectory = outputPath.getParent();
        if (outputDirectory == null || InputValidator.isValidDirectory(outputDirectory.toString())) {
            return outputPath;
        }

        ErrorHandler.showError(ErrorHandler.INVALID_OUTPUT_DIRECTORY);
        return getPathFromUser();
    }

    /**
     * Prompts the user to input a valid output file path until a valid path is provided.
     *
     * @return the validated output path entered by the user
     */
    private static Path getPathFromUser() {
        while (true) {
            System.out.print(ErrorHandler.ENTER_VALID_PATH);
            String userInputPath = scanner.nextLine().trim();

            if (!userInputPath.isEmpty()) {
                Path outputPath = Paths.get(userInputPath);
                Path outputDirectory = outputPath.getParent();

                if (outputDirectory != null && InputValidator.isValidDirectory(outputDirectory.toString())) {
                    return outputPath;
                } else {
                    ErrorHandler.showError(ErrorHandler.INVALID_OUTPUT_DIRECTORY);
                }
            } else {
                System.out.println(ErrorHandler.PATH_CANNOT_BE_EMPTY);
            }
        }
    }

    private static String buildResultFileName(String operationSuffix, Path sourcePath) {
        String originalFileName = sourcePath.getFileName().toString();
        int extensionIndex = originalFileName.lastIndexOf(".");
        String newFileName;

        if (extensionIndex > 0) {
            newFileName = originalFileName.substring(0, extensionIndex) + operationSuffix + originalFileName.substring(extensionIndex);
        } else {
            newFileName = originalFileName + operationSuffix;
        }
        return newFileName;
    }
}
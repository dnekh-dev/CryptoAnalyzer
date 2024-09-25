package ru.javarush.dnekh.cryptoanalyzer.utils;

import ru.javarush.dnekh.cryptoanalyzer.exception.ErrorHandler;
import ru.javarush.dnekh.cryptoanalyzer.validation.InputValidator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Utility class for handling user input operations such as file path and key input.
 * This class provides static methods to interact with the user and validate their input.
 */
public class UserInputUtils {

    private static final Scanner scanner = new Scanner(System.in);

    private static final String ENTER_FILE_PATH = "Enter the file path to %s: ";
    private static final String ENTER_KEY_OPERATION = "Enter the key for %s: ";

    /**
     * Prompts the user for a valid file path and validates it.
     * Repeatedly asks the user for a valid file path until one is provided.
     *
     * @param operation the operation being performed (e.g., "encrypt", "decrypt")
     * @return the validated file path as a string
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
     * Ensures that the user provides a valid integer as the key.
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

    /**
     * Generates and validates the output file path where the result will be saved.
     * If the default output path is invalid, prompts the user to provide a valid path.
     *
     * @param sourceFilePath  the path to the source file
     * @param operationSuffix the suffix to be added to the result file name based on the operation
     * @return the validated output file path as a Path object
     */
    public static Path getValidatedOutputFilePath(String sourceFilePath, String operationSuffix) {
        Path sourcePath = Paths.get(sourceFilePath);
        Path sourceDirectory = sourcePath.getParent();

        // Construct the result file name with the operation suffix
        String newFileName = buildResultFileName(operationSuffix, sourcePath);
        Path outputPath = (sourceDirectory == null) ? Paths.get(newFileName) : sourceDirectory.resolve(newFileName);

        // Check if the output directory is valid
        Path outputDirectory = outputPath.getParent();
        if (outputDirectory == null || InputValidator.isValidDirectory(outputDirectory.toString())) {
            return outputPath;
        }

        // Display error and prompt the user to enter a valid path
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

                // Validate the output directory path
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

    /**
     * Constructs the result file name by appending the operation suffix to the original file name.
     *
     * @param operationSuffix the suffix to be appended based on the operation (e.g., "_encrypted", "_decrypted")
     * @param sourcePath      the path to the source file
     * @return the constructed result file name as a string
     */
    private static String buildResultFileName(String operationSuffix, Path sourcePath) {
        String originalFileName = sourcePath.getFileName().toString();
        int extensionIndex = originalFileName.lastIndexOf(".");
        String newFileName;

        // Insert the suffix before the file extension if it exists
        if (extensionIndex > 0) {
            newFileName = originalFileName.substring(0, extensionIndex) + operationSuffix + originalFileName.substring(extensionIndex);
        } else {
            // If no extension, simply append the suffix to the file name
            newFileName = originalFileName + operationSuffix;
        }
        return newFileName;
    }
}
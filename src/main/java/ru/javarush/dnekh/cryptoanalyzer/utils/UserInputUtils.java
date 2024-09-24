package ru.javarush.dnekh.cryptoanalyzer.utils;

import ru.javarush.dnekh.cryptoanalyzer.exception.ErrorHandler;
import ru.javarush.dnekh.cryptoanalyzer.validation.InputValidator;

import java.util.Scanner;

/**
 * Utility class for handling user input operations such as file path and key input.
 */
public class UserInputUtils {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Prompts the user for a valid file path and validates it.
     *
     * @param operation the operation being performed (e.g., "encrypt", "decrypt")
     * @return the validated file path or null if invalid
     */
    public static String getInputFilePath(String operation) {
        System.out.printf("Enter the file path to %s: ", operation);
        String filePath = scanner.nextLine().trim();
        if (!InputValidator.isValidFilePath(filePath)) {
            ErrorHandler.showError("INVALID FILE PATH. PLEASE TRY AGAIN!\n");
            return null;
        }
        return filePath;
    }

    /**
     * Prompts the user to input a key for encryption or decryption.
     *
     * @param operation the operation being performed (e.g., "encrypt", "decrypt")
     * @return the valid integer key entered by the user
     */
    public static int getKeyFromUser(String operation) {
        System.out.printf("Enter the key for %s: ", operation);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            ErrorHandler.showError("Invalid key entered. Please enter a valid integer.");
            return getKeyFromUser(operation); // Recursively ask for the key until valid input is provided
        }
    }
}
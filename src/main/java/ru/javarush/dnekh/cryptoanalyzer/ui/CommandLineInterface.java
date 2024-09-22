package ru.javarush.dnekh.cryptoanalyzer.ui;

import ru.javarush.dnekh.cryptoanalyzer.exception.ErrorHandler;
import ru.javarush.dnekh.cryptoanalyzer.io.FileHandler;
import ru.javarush.dnekh.cryptoanalyzer.model.CaesarCipher;
import ru.javarush.dnekh.cryptoanalyzer.validation.InputValidator;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class provides a command line interface for the Caesar cipher application.
 */
public class CommandLineInterface {
    private final Scanner scanner;
    private final FileHandler fileHandler;
    private final CaesarCipher caesarCipher;

    // Constants for messages
    private static final String MENU_HEADER = "MENU OPTIONS:";
    private static final String PROMPT_OPTION = "Choose an option: ";
    private static final String SUCCESS_FILE_OPERATION = "File %s successfully.\n";
    private static final String EXIT_MESSAGE = "Exiting...";

    public CommandLineInterface() {
        this.scanner = new Scanner(System.in);
        this.fileHandler = new FileHandler();
        this.caesarCipher = new CaesarCipher();
    }

    public void start() {
        while (true) {
            System.out.println(MENU_HEADER);
            MenuOption.printMenu();
            System.out.print(PROMPT_OPTION);
            String option = scanner.nextLine().trim();
            System.out.println();

            try {
                MenuOption menuOption = MenuOption.fromString(option);
                switch (menuOption) {
                    case ENCRYPT:
                        processFileOperation("encrypt");
                        break;
                    case DECRYPT:
                        processFileOperation("decrypt");
                        break;
                    case BRUTE_FORCE:
                        handleBruteForceFileOperation();
                        break;
                    case HELP:
                        printHelp();
                        break;
                    case EXIT:
                        exitApplication();
                        break;
                    default:
                        ErrorHandler.showError("Invalid option. Please try again.");
                }
            } catch (IllegalArgumentException e) {
                ErrorHandler.showError(e.getMessage());
            }
        }
    }

    private void processFileOperation(String operation) {
        String filePath = getInputFilePath(operation);
        if (filePath == null) return;

        String outputFilePath = fileHandler.generateOutputFilePath(filePath, operation);

        try {
            String content = fileHandler.readFile(filePath);
            int key = getKeyFromUser(operation);

            String resultContent = operation.equals("encrypt")
                    ? caesarCipher.encrypt(content, key)
                    : caesarCipher.decrypt(content, key);

            fileHandler.writeFile(filePath, outputFilePath, resultContent);
            System.out.printf(SUCCESS_FILE_OPERATION, operation + "ed");
            System.out.println("Result saved to: " + outputFilePath);
        } catch (IOException e) {
            ErrorHandler.showError("File error: " + e.getMessage());
        }
    }

    private void handleBruteForceFileOperation() {
        String filePath = getInputFilePath("decrypt using Brute Force");
        if (filePath == null) return;

        try {
            String content = fileHandler.readFile(filePath);
            System.out.println("Attempting Brute Force decryption...");

            int confirmedShift = caesarCipher.bruteForceDecryptAndGetShift(content);

            if (confirmedShift != -1) {
                String decryptedContent = caesarCipher.decrypt(content, confirmedShift);
                String outputFilePath = fileHandler.generateOutputFilePath(filePath, "bruteforce_decrypt");
                fileHandler.writeFile(filePath, outputFilePath, decryptedContent);
                System.out.printf(SUCCESS_FILE_OPERATION, "decrypted using Brute Force");
                System.out.println("Result saved to: " + outputFilePath);
            } else {
                ErrorHandler.showError("Decryption was not confirmed. No file was saved.");
            }
        } catch (IOException e) {
            ErrorHandler.showError("File error: " + e.getMessage());
        }
    }

    private String getInputFilePath(String operation) {
        System.out.printf("Enter the file path to %s: ", operation);
        String filePath = scanner.nextLine().trim();
        if (!InputValidator.isValidFilePath(filePath)) {
            ErrorHandler.showError("INVALID FILE PATH. PLEASE TRY AGAIN!\n");
            return null;
        }
        return filePath;
    }

    private int getKeyFromUser(String operation) {
        System.out.printf("Enter the key for %s: ", operation);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            ErrorHandler.showError("Invalid key entered. Please enter a valid integer.");
            return getKeyFromUser(operation); // Recursively ask for the key until valid input is provided
        }
    }

    private void printHelp() {
        System.out.println("""
                HELP:
                1. Encrypt text from file - Encrypt a text file with a Caesar cipher.
                2. Decrypt text from file with key - Decrypt a text file with a Caesar cipher using a key.
                3. Brute Force Decrypt - Decrypt text using the Brute Force method.
                4. Help - Show this help message.
                5. Exit - Exit the application.
                """);
    }

    private void exitApplication() {
        System.out.println(EXIT_MESSAGE);
        scanner.close();
        System.exit(0);
    }
}
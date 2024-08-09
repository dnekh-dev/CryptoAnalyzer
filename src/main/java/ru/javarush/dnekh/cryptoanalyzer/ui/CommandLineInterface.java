package ru.javarush.dnekh.cryptoanalyzer.ui;

import ru.javarush.dnekh.cryptoanalyzer.actions.Action;
import ru.javarush.dnekh.cryptoanalyzer.actions.DecryptAction;
import ru.javarush.dnekh.cryptoanalyzer.actions.EncryptAction;
import ru.javarush.dnekh.cryptoanalyzer.exception.InvalidCharacterException;
import ru.javarush.dnekh.cryptoanalyzer.io.FileHandler;
import ru.javarush.dnekh.cryptoanalyzer.validation.InputValidator;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class provides a command line interface for the Caesar cipher application.
 */
public class CommandLineInterface {
    private final Scanner scanner;
    private final FileHandler fileHandler;

    // Constants for messages
    private static final String MENU_HEADER = "MENU OPTIONS:";
    private static final String PROMPT_OPTION = "Choose an option: ";
    private static final String PROMPT_FILE_PATH = "Enter the file path to %s: ";
    private static final String PROMPT_KEY = "Enter the key for %s: ";
    private static final String PROMPT_OUTPUT_PATH = "Enter the output file path (or press Enter to use the default path): ";
    private static final String ERROR_INVALID_PATH = "INVALID FILE PATH. PLEASE TRY AGAIN!\n";
    private static final String ERROR_INVALID_DIRECTORY = "Invalid output directory. Please try again.";
    private static final String SUCCESS_FILE_OPERATION = "File %s successfully.\n";
    private static final String EXIT_MESSAGE = "Exiting...";
    private static final String ERROR_MESSAGE = "Error: ";

    public CommandLineInterface() {
        this.scanner = new Scanner(System.in);
        this.fileHandler = new FileHandler();
    }

    public void start() {
        while (true) {
            System.out.println(MENU_HEADER);
            MenuOption.printMenu();
            System.out.print(PROMPT_OPTION);
            String option = scanner.nextLine();
            System.out.println();

            try {
                MenuOption menuOption = MenuOption.fromString(option);
                switch (menuOption) {
                    case ENCRYPT:
                        handleFileOperation(new EncryptAction(), "encrypt");
                        break;
                    case DECRYPT:
                        handleFileOperation(new DecryptAction(), "decrypt");
                        break;
                    case ENCRYPT_CONSOLE:
                        handleConsoleOperation(new EncryptAction(), "encrypt");
                        break;
                    case DECRYPT_CONSOLE:
                        handleConsoleOperation(new DecryptAction(), "decrypt");
                        break;
                    case HELP:
                        printHelp();
                        break;
                    case EXIT:
                        System.out.println(EXIT_MESSAGE);
                        return;
                    default:
                        System.out.println(ERROR_INVALID_PATH);
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void handleFileOperation(Action action, String operation) {
        System.out.printf(PROMPT_FILE_PATH, operation);
        String filePath = scanner.nextLine();
        if (!InputValidator.isValidFilePath(filePath)) {
            System.out.println(ERROR_INVALID_PATH);
            return;
        }

        System.out.printf(PROMPT_KEY, operation);
        int key = Integer.parseInt(scanner.nextLine());

        System.out.print(PROMPT_OUTPUT_PATH);
        String outputFilePath = scanner.nextLine();

        if (!outputFilePath.isEmpty()) {
            String outputDirectoryPath = new File(outputFilePath).getParent();
            if (outputDirectoryPath != null && !InputValidator.isValidDirectory(outputDirectoryPath)) {
                System.out.println(ERROR_INVALID_DIRECTORY);
                return;
            }
        } else {
            outputFilePath = null; // Use the default file path logic in FileHandler
        }

        try {
            String content = fileHandler.readFile(filePath);
            String resultContent = action.execute(content, key);
            fileHandler.writeFile(filePath, outputFilePath, resultContent);
            System.out.printf(SUCCESS_FILE_OPERATION, operation + "ed");
        } catch (IOException | InvalidCharacterException e) {
            System.out.println(ERROR_MESSAGE + e.getMessage());
        }
    }

    private void handleConsoleOperation(Action action, String operation) {
        System.out.printf("Enter the text to %s: ", operation);
        String text = scanner.nextLine();
        System.out.printf(PROMPT_KEY, operation);
        int key = Integer.parseInt(scanner.nextLine());

        try {
            String resultText = action.execute(text, key);
            System.out.printf("Resulting %s text: %s%n%n", operation, resultText);
        } catch (InvalidCharacterException e) {
            System.out.println(ERROR_MESSAGE + e.getMessage());
        }
    }

    private void printHelp() {
        String helpMessage = String.format(
                "HELP:%n" +
                        "1. Encrypt text from file - Encrypt a text file with a Caesar cipher.%n" +
                        "2. Decrypt text from file with key - Decrypt a text file with a Caesar cipher using a key.%n" +
                        "3. Encrypt text from console - Encrypt text entered through the console with a Caesar cipher.%n" +
                        "4. Decrypt text from console with key - Decrypt text entered through the console with a Caesar cipher using a key.%n" +
                        "5. Help - Show this help message.%n" +
                        "6. Exit - Exit the application.%n"
        );
        System.out.println(helpMessage);
    }
}

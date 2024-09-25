package ru.javarush.dnekh.cryptoanalyzer.ui;

import ru.javarush.dnekh.cryptoanalyzer.exception.ErrorHandler;
import ru.javarush.dnekh.cryptoanalyzer.service.FileProcessor;
import ru.javarush.dnekh.cryptoanalyzer.utils.UserInputUtils;
import ru.javarush.dnekh.cryptoanalyzer.service.OperationSuffix;

import java.util.Scanner;

/**
 * This class provides a command line interface for the Caesar cipher application.
 * It allows users to interact with the application by selecting menu options
 * for encrypting, decrypting, and brute-forcing text files.
 */
public class CommandLineInterface {

    private final Scanner scanner;
    private final FileProcessor fileProcessor;

    private static final String MENU_HEADER = "MENU OPTIONS:";
    private static final String PROMPT_OPTION = "Choose an option: ";
    private static final String EXIT_MESSAGE = "Exiting...";
    private static final String BRUTE_FORCE_CHOSEN_OPERATION_MESSAGE = "decrypt using Brute Force";

    /**
     * Message shown when the user requests help information.
     */
    private static final String HELP_MESSAGE = """
                HELP:
                1. Encrypt text from file - Encrypt a text file with a Caesar cipher.
                2. Decrypt text from file with key - Decrypt a text file with a Caesar cipher using a key.
                3. Brute Force Decrypt - Decrypt text using the Brute Force method.
                4. Help - Show this help message.
                5. Exit - Exit the application.
                """;

    /**
     * Constructor initializes the CommandLineInterface with necessary components.
     */
    public CommandLineInterface() {
        this.scanner = new Scanner(System.in);
        this.fileProcessor = new FileProcessor();
    }

    /**
     * Starts the command line interface, displaying the menu options and handling user input.
     * This method will run in a loop until the user chooses to exit the application.
     */
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
                        handleFileOperation(OperationSuffix.ENCRYPT);
                        break;
                    case DECRYPT:
                        handleFileOperation(OperationSuffix.DECRYPT);
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
                        ErrorHandler.showError(ErrorHandler.INVALID_CHOSEN_OPTION_MESSAGE);
                }
            } catch (IllegalArgumentException e) {
                ErrorHandler.showError(ErrorHandler.SOME_ERROR + e.getMessage());
            }
        }
    }

    /**
     * Handles file operations for encryption and decryption based on the user's choice.
     * It retrieves the file path and key from the user and processes the operation.
     *
     * @param operationSuffix the suffix indicating the operation to perform (encrypt/decrypt)
     */
    private void handleFileOperation(OperationSuffix operationSuffix) {
        String filePath = UserInputUtils.getInputFilePath(operationSuffix.name().toLowerCase());
        int key = UserInputUtils.getKeyFromUser(operationSuffix.name().toLowerCase());

        fileProcessor.processFileOperation(filePath, operationSuffix, key);
    }

    /**
     * Handles the Brute Force decryption operation by retrieving the file path
     * from the user and passing it to the FileProcessor for processing.
     */
    private void handleBruteForceFileOperation() {
        String filePath = UserInputUtils.getInputFilePath(BRUTE_FORCE_CHOSEN_OPERATION_MESSAGE);
        fileProcessor.handleBruteForceFileOperation(filePath);
    }

    /**
     * Displays the help message to the user, providing information about the available menu options.
     */
    private void printHelp() {
        System.out.println(HELP_MESSAGE);
    }

    /**
     * Exits the application by closing the scanner and terminating the program.
     */
    private void exitApplication() {
        System.out.println(EXIT_MESSAGE);
        scanner.close();
        System.exit(0);
    }
}
package ru.javarush.dnekh.cryptoanalyzer.ui;

import ru.javarush.dnekh.cryptoanalyzer.exception.ErrorHandler;
import ru.javarush.dnekh.cryptoanalyzer.service.FileProcessor;
import ru.javarush.dnekh.cryptoanalyzer.utils.UserInputUtils;
import ru.javarush.dnekh.cryptoanalyzer.service.OperationSuffix;

import java.util.Scanner;

/**
 * This class provides a command line interface for the Caesar cipher application.
 */
public class CommandLineInterface {

    private final Scanner scanner;
    private final FileProcessor fileProcessor;

    private static final String MENU_HEADER = "MENU OPTIONS:";
    private static final String PROMPT_OPTION = "Choose an option: ";
    private static final String EXIT_MESSAGE = "Exiting...";
    
    private static final String BRUTE_FORCE_CHOSEN_OPERATION_MESSAGE = "decrypt using Brute Force";

    private static final String HELP_MESSAGE = """
                HELP:
                1. Encrypt text from file - Encrypt a text file with a Caesar cipher.
                2. Decrypt text from file with key - Decrypt a text file with a Caesar cipher using a key.
                3. Brute Force Decrypt - Decrypt text using the Brute Force method.
                4. Help - Show this help message.
                5. Exit - Exit the application.
                """;

    public CommandLineInterface() {
        this.scanner = new Scanner(System.in);
        this.fileProcessor = new FileProcessor();
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

    private void handleFileOperation(OperationSuffix operationSuffix) {
        String filePath = UserInputUtils.getInputFilePath(operationSuffix.name().toLowerCase());
        int key = UserInputUtils.getKeyFromUser(operationSuffix.name().toLowerCase());
        fileProcessor.processFileOperation(filePath, operationSuffix, key);
    }

    private void handleBruteForceFileOperation() {
        String filePath = UserInputUtils.getInputFilePath(BRUTE_FORCE_CHOSEN_OPERATION_MESSAGE);
        fileProcessor.handleBruteForceFileOperation(filePath);
    }

    private void printHelp() {
        System.out.println(HELP_MESSAGE);
    }

    private void exitApplication() {
        System.out.println(EXIT_MESSAGE);
        scanner.close();
        System.exit(0);
    }
}
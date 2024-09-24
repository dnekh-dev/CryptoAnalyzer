package ru.javarush.dnekh.cryptoanalyzer.ui;

import ru.javarush.dnekh.cryptoanalyzer.exception.ErrorHandler;
import ru.javarush.dnekh.cryptoanalyzer.service.FileProcessor;
import ru.javarush.dnekh.cryptoanalyzer.utils.UserInputUtils;

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
                        handleFileOperation("encrypt");
                        break;
                    case DECRYPT:
                        handleFileOperation("decrypt");
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

    private void handleFileOperation(String operation) {
        String filePath = UserInputUtils.getInputFilePath(operation);
        if (filePath == null) return;
        int key = UserInputUtils.getKeyFromUser(operation);
        fileProcessor.processFileOperation(filePath, operation, key);
    }

    private void handleBruteForceFileOperation() {
        String filePath = UserInputUtils.getInputFilePath("decrypt using Brute Force");
        if (filePath != null) {
            fileProcessor.handleBruteForceFileOperation(filePath);
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
package ru.javarush.dnekh.cryptoanalyzer.ui;

import ru.javarush.dnekh.cryptoanalyzer.exception.InvalidCharacterException;
import ru.javarush.dnekh.cryptoanalyzer.io.FileHandler;
import ru.javarush.dnekh.cryptoanalyzer.model.CaesarCipher;
import ru.javarush.dnekh.cryptoanalyzer.validation.InputValidator;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class provides a command line interface for the Caesar cipher application.
 */
public class CommandLineInterface {
    private final Scanner scanner;
    private final CaesarCipher caesarCipher;
    private final FileHandler fileHandler;

    public CommandLineInterface() {
        this.scanner = new Scanner(System.in);
        this.caesarCipher = new CaesarCipher();
        this.fileHandler = new FileHandler();
    }

    public void start() {
        while (true) {
            System.out.println("MENU OPTIONS:");
            MenuOption.printMenu();
            System.out.print("Choose an option: ");
            String option = scanner.nextLine();
            System.out.println();

            try {
                MenuOption menuOption = MenuOption.fromString(option);
                switch (menuOption) {
                    case ENCRYPT:
                        handleEncryption();
                        break;
                    case DECRYPT:
                        handleDecryption();
                        break;
                    case ENCRYPT_CONSOLE:
                        handleConsoleEncryption();
                        break;
                    case DECRYPT_CONSOLE:
                        handleConsoleDecryption();
                        break;
                    case HELP:
                        printHelp();
                        break;
                    case EXIT:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void handleEncryption() {
        System.out.print("Enter the file path to encrypt: ");
        String filePath = scanner.nextLine();
        if (!InputValidator.isValidFilePath(filePath)) {
            System.out.println("INVALID FILE PATH. PLEASE TRY AGAIN!\n");
            return;
        }

        System.out.print("Enter the key for encryption: ");
        int key = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter the output file path (or press Enter to use the default path): ");
        String outputFilePath = scanner.nextLine();

        if (outputFilePath.isEmpty()) {
            outputFilePath = null; // Use the default file path logic in FileHandler
        } else {
            String outputDirectoryPath = new File(outputFilePath).getParent();
            if (outputDirectoryPath != null && !InputValidator.isValidDirectory(outputDirectoryPath)) {
                System.out.println("Invalid output directory. Please try again.");
                return;
            }
        }

        try {
            String content = fileHandler.readFile(filePath);
            String encryptedContent = caesarCipher.encrypt(content, key);
            fileHandler.writeFile(filePath, outputFilePath, encryptedContent);
            System.out.println("File encrypted successfully.\n");
        } catch (IOException | InvalidCharacterException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleDecryption() {
        System.out.print("Enter the file path to decrypt: ");
        String filePath = scanner.nextLine();
        if (!InputValidator.isValidFilePath(filePath)) {
            System.out.println("INVALID FILE PATH. PLEASE TRY AGAIN!\n");
            return;
        }

        System.out.print("Enter the key for decryption: ");
        int key = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter the output file path (or press Enter to use the default path): ");
        String outputFilePath = scanner.nextLine();

        if (outputFilePath.isEmpty()) {
            outputFilePath = null; // Use the default file path logic in FileHandler
        } else {
            String outputDirectoryPath = new File(outputFilePath).getParent();
            if (outputDirectoryPath != null && !InputValidator.isValidDirectory(outputDirectoryPath)) {
                System.out.println("Invalid output directory. Please try again.");
                return;
            }
        }

        try {
            String content = fileHandler.readFile(filePath);
            String decryptedContent = caesarCipher.decrypt(content, key);
            fileHandler.writeFile(filePath, outputFilePath, decryptedContent);
            System.out.println("File decrypted successfully.\n");
        } catch (IOException | InvalidCharacterException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleConsoleEncryption() {
        System.out.print("Enter the text to encrypt: ");
        String text = scanner.nextLine();
        System.out.print("Enter the key for encryption: ");
        int key = Integer.parseInt(scanner.nextLine());

        try {
            String encryptedText = caesarCipher.encrypt(text, key);
            System.out.println("Encrypted text: " + encryptedText + "\n");
        } catch (InvalidCharacterException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleConsoleDecryption() {
        System.out.print("Enter the text to decrypt: ");
        String text = scanner.nextLine();
        System.out.print("Enter the key for decryption: ");
        int key = Integer.parseInt(scanner.nextLine());

        try {
            String decryptedText = caesarCipher.decrypt(text, key);
            System.out.println("Decrypted text: " + decryptedText + "\n");
        } catch (InvalidCharacterException e) {
            System.out.println("Error: " + e.getMessage());
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

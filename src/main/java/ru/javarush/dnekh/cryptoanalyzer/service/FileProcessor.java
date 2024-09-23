package ru.javarush.dnekh.cryptoanalyzer.service;

import ru.javarush.dnekh.cryptoanalyzer.io.FileHandler;
import ru.javarush.dnekh.cryptoanalyzer.model.CaesarCipher;
import ru.javarush.dnekh.cryptoanalyzer.exception.ErrorHandler;

import java.io.IOException;

/**
 * This class processes file operations like encryption, decryption, and brute force.
 */
public class FileProcessor {

    private final FileHandler fileHandler;
    private final CaesarCipher caesarCipher;

    public FileProcessor() {
        this.fileHandler = new FileHandler();
        this.caesarCipher = new CaesarCipher();
    }

    public void processFileOperation(String filePath, String operation, int key) {
        String outputFilePath = fileHandler.generateOutputFilePath(filePath, operation);
        try {
            String content = fileHandler.readFile(filePath);
            String resultContent = operation.equals("encrypt")
                    ? caesarCipher.encrypt(content, key)
                    : caesarCipher.decrypt(content, key);
            fileHandler.writeFile(filePath, outputFilePath, resultContent);
            System.out.printf("File %s successfully.\n", operation + "ed");
            System.out.println("Result saved to: " + outputFilePath);
        } catch (IOException e) {
            ErrorHandler.showError("File error: " + e.getMessage());
        }
    }

    public void handleBruteForceFileOperation(String filePath) {
        try {
            String content = fileHandler.readFile(filePath);
            int confirmedShift = caesarCipher.bruteForceDecryptAndGetShift(content);
            if (confirmedShift != -1) {
                String decryptedContent = caesarCipher.decrypt(content, confirmedShift);
                String outputFilePath = fileHandler.generateOutputFilePath(filePath, "bruteforce_decrypt");
                fileHandler.writeFile(filePath, outputFilePath, decryptedContent);
                System.out.println("File decrypted using Brute Force successfully.");
                System.out.println("Result saved to: " + outputFilePath + "\n");
            } else {
                ErrorHandler.showError("Decryption was not confirmed. No file was saved.");
            }
        } catch (IOException e) {
            ErrorHandler.showError("File error: " + e.getMessage());
        }
    }
}
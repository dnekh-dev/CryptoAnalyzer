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

    private static final String ENCRYPT_DECRYPT_RESULT_MESSAGE = "File %s successfully.";
    private static final String BRUTE_FORCE_RESULT_MESSAGE = "File decrypted using Brute Force successfully.";
    private static final String SAVED_TO_MESSAGE = "Result saved to: ";

    public FileProcessor() {
        this.fileHandler = new FileHandler();
        this.caesarCipher = new CaesarCipher();
    }

    public void processFileOperation(String filePath, OperationSuffix operationSuffix, int key) {
        try {
            String content = fileHandler.readFile(filePath);

            String resultContent = (operationSuffix == OperationSuffix.ENCRYPT)
                    ? caesarCipher.encrypt(content, key)
                    : caesarCipher.decrypt(content, key);

            fileHandler.writeFile(filePath, operationSuffix, resultContent);
            System.out.printf(ENCRYPT_DECRYPT_RESULT_MESSAGE, operationSuffix.name().toLowerCase() + "ed");
            System.out.println(SAVED_TO_MESSAGE + fileHandler.generateOutputFilePath(filePath, operationSuffix));
            System.out.println();
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

                fileHandler.writeFile(filePath, OperationSuffix.BRUTE_FORCE_DECRYPT, decryptedContent);
                System.out.println(BRUTE_FORCE_RESULT_MESSAGE);
                System.out.println(SAVED_TO_MESSAGE + fileHandler.generateOutputFilePath(filePath, OperationSuffix.BRUTE_FORCE_DECRYPT));
                System.out.println();
            } else {
                ErrorHandler.showError("Decryption was not confirmed. No file was saved.");
            }
        } catch (IOException e) {
            ErrorHandler.showError("File error: " + e.getMessage());
        }
    }
}
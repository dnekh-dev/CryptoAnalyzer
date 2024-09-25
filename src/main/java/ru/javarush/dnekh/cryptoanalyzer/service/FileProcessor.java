package ru.javarush.dnekh.cryptoanalyzer.service;

import ru.javarush.dnekh.cryptoanalyzer.io.FileHandler;
import ru.javarush.dnekh.cryptoanalyzer.model.CaesarCipher;
import ru.javarush.dnekh.cryptoanalyzer.exception.ErrorHandler;

import java.io.IOException;

/**
 * This class handles file processing operations such as encryption, decryption, and brute force decryption.
 * It interacts with the `FileHandler` and `CaesarCipher` classes to read, process, and write text files.
 */
public class FileProcessor {

    private final FileHandler fileHandler;
    private final CaesarCipher caesarCipher;

    private static final String ENCRYPT_DECRYPT_RESULT_MESSAGE = "File %s successfully.";
    private static final String BRUTE_FORCE_RESULT_MESSAGE = "File decrypted using Brute Force successfully.";
    private static final String SAVED_TO_MESSAGE = "Result saved to: ";

    /**
     * Constructor initializes the `FileProcessor` with instances of `FileHandler` and `CaesarCipher`.
     */
    public FileProcessor() {
        this.fileHandler = new FileHandler();
        this.caesarCipher = new CaesarCipher();
    }

    /**
     * Processes the file operation for encryption or decryption based on the provided key and operation type.
     * Reads the content of the file, performs the Caesar cipher operation, and writes the result to a new file.
     *
     * @param filePath       the path to the source file
     * @param operationSuffix the enum indicating the operation type (ENCRYPT or DECRYPT)
     * @param key            the key to be used for the encryption or decryption process
     */
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
            ErrorHandler.showError(ErrorHandler.FILE_ERROR_MESSAGE + e.getMessage());
        }
    }

    /**
     * Handles the brute force decryption operation by attempting all possible shifts until the correct one is confirmed by the user.
     * Reads the content of the file, tries all shift values, and writes the result to a new file if confirmed.
     *
     * @param filePath the path to the source file to be decrypted using brute force
     */
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
                ErrorHandler.showError(ErrorHandler.DECRYPTION_NOT_CONFIRMED_MESSAGE);
            }
        } catch (IOException e) {
            ErrorHandler.showError(ErrorHandler.FILE_ERROR_MESSAGE + e.getMessage());
        }
    }
}
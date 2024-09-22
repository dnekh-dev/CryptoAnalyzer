package ru.javarush.dnekh.cryptoanalyzer.model;

import ru.javarush.dnekh.cryptoanalyzer.exception.InvalidCharacterException;

import java.util.Scanner;

/**
 * This class provides methods for encrypting and decrypting text using the Caesar cipher.
 */
public class CaesarCipher {

    private final Alphabet alphabet;

    /**
     * Constructor initializes the CaesarCipher with a given Alphabet.
     */
    public CaesarCipher() {
        this.alphabet = new Alphabet();
    }

    /**
     * Encrypts a given text using the Caesar cipher with the specified key.
     *
     * @param text the text to encrypt
     * @param key  the encryption key (shift value)
     * @return the encrypted text
     * @throws InvalidCharacterException if the text contains a character not in the alphabet
     */
    public String encrypt(String text, int key) throws InvalidCharacterException {
        StringBuilder encryptedText = new StringBuilder();
        char[] alphabetArray = alphabet.getAlphabet();
        int alphabetSize = alphabetArray.length;

        for (char c : text.toLowerCase().toCharArray()) {
            if (c == '\n') {
                continue;
            }
            if (alphabet.isCharInAlphabet(c)) {
                int originalIndex = findCharIndex(c, alphabetArray);
                int newIndex = (originalIndex + key) % alphabetSize;
                if (newIndex < 0) {
                    newIndex += alphabetSize;
                }
                encryptedText.append(alphabetArray[newIndex]);
            } else {
                throw new InvalidCharacterException("Character '" + c + "' is not in the alphabet.");
            }
        }

        return encryptedText.toString();
    }

    /**
     * Decrypts a given text using the Caesar cipher with the specified key.
     *
     * @param text the text to decrypt
     * @param key  the decryption key (shift value)
     * @return the decrypted text
     * @throws InvalidCharacterException if the text contains a character not in the alphabet
     */
    public String decrypt(String text, int key) throws InvalidCharacterException {
        return encrypt(text, -key);
    }

    /**
     * Performs brute force decryption by trying all possible shifts and returns the confirmed shift value.
     *
     * @param text the text to decrypt
     * @return the confirmed shift value if the user confirms the decryption, or -1 if no confirmation
     */
    public int bruteForceDecryptAndGetShift(String text) {
        Scanner scanner = new Scanner(System.in);
        char[] alphabetArray = alphabet.getAlphabet();
        int alphabetSize = alphabetArray.length;

        System.out.println("\nStarting Brute Force Decryption...");

        for (int shift = 1; shift < alphabetSize; shift++) {
            String decryptedText = decrypt(text, shift);

            String firstSentence = decryptedText.split("[.!?]", 2)[0];
            if (firstSentence.length() > 100) {
                firstSentence = firstSentence.substring(0, 100);
            }

            System.out.println("Shift " + shift + ": " + firstSentence);
            System.out.print("Is this correct? (yes/no): ");
            String userResponse = scanner.nextLine().trim().toLowerCase();

            if (userResponse.equals("yes")) {
                return shift;
            }
        }

        System.out.println("Brute Force Decryption Completed. No correct decryption found.");
        return -1;
    }

    /**
     * Finds the index of a character in the alphabet array.
     *
     * @param c             the character to find
     * @param alphabetArray the alphabet array
     * @return the index of the character, or -1 if not found
     */
    private int findCharIndex(char c, char[] alphabetArray) {
        for (int i = 0; i < alphabetArray.length; i++) {
            if (alphabetArray[i] == c) {
                return i;
            }
        }
        return -1;
    }
}
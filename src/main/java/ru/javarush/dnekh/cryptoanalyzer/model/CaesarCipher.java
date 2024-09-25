package ru.javarush.dnekh.cryptoanalyzer.model;

import ru.javarush.dnekh.cryptoanalyzer.exception.InvalidCharacterException;

import java.util.Scanner;

/**
 * This class provides methods for encrypting, decrypting, and brute force decryption of text using the Caesar cipher.
 * It works with both the given shift key for encryption/decryption and the brute force method without a key.
 */
public class CaesarCipher {

    private final Alphabet alphabet;

    private static final String ERR_MESSAGE_CHARACTER_NOT_SUPPORTED = " IS NOT SUPPORTED";
    private static final String UNSUCCESSFUL_BRUTE_FORCE_RESULT_MESSAGE = "BRUTE FORCE DECRYPTION COMPLETED. NO CORRECT DECRYPTION FOUND.";
    private static final String BRUTE_FORCE_STARTING_MESSAGE = "Starting Brute Force Decryption...";
    private static final String IS_CORRECT_QUESTION_MESSAGE = "Is this correct? (yes/no): ";

    /**
     * Constructor initializes the CaesarCipher with a given Alphabet instance.
     */
    public CaesarCipher() {
        this.alphabet = new Alphabet();
    }

    /**
     * Encrypts a given text using the Caesar cipher with the specified key.
     * Converts the text to lowercase and shifts each character according to the provided key.
     *
     * @param text the text to encrypt
     * @param key  the encryption key (shift value)
     * @return the encrypted text
     * @throws InvalidCharacterException if the text contains a character not present in the alphabet
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
                throw new InvalidCharacterException(c + ERR_MESSAGE_CHARACTER_NOT_SUPPORTED);
            }
        }

        return encryptedText.toString();
    }

    /**
     * Decrypts a given text using the Caesar cipher with the specified key.
     * This method simply calls the encrypt method with the negative of the given key.
     *
     * @param text the text to decrypt
     * @param key  the decryption key (shift value)
     * @return the decrypted text
     * @throws InvalidCharacterException if the text contains a character not present in the alphabet
     */
    public String decrypt(String text, int key) throws InvalidCharacterException {
        return encrypt(text, -key);
    }

    /**
     * Performs brute force decryption by trying all possible shifts and returns the confirmed shift value.
     * The user is prompted to confirm if the decryption is correct after each shift attempt.
     *
     * @param text the text to decrypt using brute force
     * @return the confirmed shift value if the user confirms the decryption, or -1 if no confirmation is given
     */
    public int bruteForceDecryptAndGetShift(String text) {
        Scanner scanner = new Scanner(System.in);
        char[] alphabetArray = alphabet.getAlphabet();
        int alphabetSize = alphabetArray.length;

        System.out.println();
        System.out.println(BRUTE_FORCE_STARTING_MESSAGE);

        for (int shift = 1; shift < alphabetSize; shift++) {
            String decryptedText = decrypt(text, shift);

            // Extract the first sentence up to 100 characters or until a punctuation mark
            String firstSentence = decryptedText.split("[.!?]", 2)[0];
            if (firstSentence.length() > 100) {
                firstSentence = firstSentence.substring(0, 100);
            }

            System.out.println("Shift " + shift + ": " + firstSentence);
            System.out.print(IS_CORRECT_QUESTION_MESSAGE);
            String userResponse = scanner.nextLine().trim().toLowerCase();

            if (userResponse.equals("yes")) {
                System.out.println();
                return shift;
            }
        }

        System.out.println(UNSUCCESSFUL_BRUTE_FORCE_RESULT_MESSAGE);
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
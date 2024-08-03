package ru.javarush.dnekh.cryptoanalyzer.model;

import ru.javarush.dnekh.cryptoanalyzer.exception.InvalidCharacterException;

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

        for (char c : text.toCharArray()) {
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
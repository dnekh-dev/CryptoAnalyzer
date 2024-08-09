package ru.javarush.dnekh.cryptoanalyzer.model;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents an alphabet used for Caesar cipher encryption and decryption.
 * It provides methods to get the alphabet and to check if a character is part of the alphabet.
 */
public class Alphabet {

    // Strings representing the Cyrillic alphabet, English alphabet, and additional symbols
    private static final String RUS_ALPHABET =
            "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";

    private static final String ENG_ALPHABET =
            "abcdefghijklmnopqrstuvwxyz";

    private static final String SYMBOLS =
            ".,«»\"':;!? -";

    // Combined alphabet string
    private static final String ALPHABET = RUS_ALPHABET + ENG_ALPHABET + SYMBOLS;

    // Set for fast character lookup
    private final Set<Character> alphabetSet;

    /**
     * Constructor initializes the set of alphabet characters.
     * The alphabet includes Cyrillic characters, English characters, and specific punctuation marks.
     */
    public Alphabet() {
        this.alphabetSet = new HashSet<>();
        for (char ch : ALPHABET.toCharArray()) {
            alphabetSet.add(ch);
        }
    }

    /**
     * Returns a copy of the alphabet as a character array.
     * This prevents modification of the original alphabet string.
     *
     * @return a copy of the alphabet as a character array
     */
    public char[] getAlphabet() {
        return ALPHABET.toCharArray();
    }

    /**
     * Checks if a character is part of the alphabet.
     *
     * @param c the character to check
     * @return true if the character is in the alphabet, false otherwise
     */
    public boolean isCharInAlphabet(char c) {
        return alphabetSet.contains(c);
    }
}

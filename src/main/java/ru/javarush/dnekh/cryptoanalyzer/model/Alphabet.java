package ru.javarush.dnekh.cryptoanalyzer.model;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents an alphabet used for Caesar cipher encryption and decryption.
 * It provides methods to get the alphabet and to check if a character is part of the alphabet.
 */
public class Alphabet {

    // Array of characters representing the Cyrillic alphabet and additional symbols
    private static final char[] ALPHABET = {
            'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю', 'я', '.', ',', '«', '»', '"', '\'', ':', ';', '!', '?', ' '
    };

    // Set for fast character lookup
    private final Set<Character> alphabetSet;

    /**
     * Constructor initializes the set of alphabet characters.
     * The alphabet includes Cyrillic characters and specific punctuation marks.
     */
    public Alphabet() {
        this.alphabetSet = new HashSet<>();
        for (char ch : ALPHABET) {
            alphabetSet.add(ch);
        }
    }

    /**
     * Returns a copy of the alphabet as a character array.
     * This prevents modification of the original alphabet array.
     *
     * @return a copy of the alphabet array
     */
    public char[] getAlphabet() {
        return ALPHABET.clone();
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

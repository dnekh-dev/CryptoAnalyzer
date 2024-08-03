package ru.javarush.dnekh.cryptoanalyzer.exception;

/**
 * This exception is thrown when a character is not part of the defined alphabet.
 */
public class InvalidCharacterException extends RuntimeException {
    /**
     * Constructs a new InvalidCharacterException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidCharacterException(String message) {
        super(message);
    }
}

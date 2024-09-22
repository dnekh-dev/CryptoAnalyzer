package ru.javarush.dnekh.cryptoanalyzer.exception;

/**
 * Utility class for handling error messages.
 */
public final class ErrorHandler {

    // Private constructor to prevent instantiation
    private ErrorHandler() {
        // This constructor is intentionally left empty
    }

    /**
     * Displays an error message to the console.
     *
     * @param message the error message to display
     */
    public static void showError(String message) {
        System.out.println("Error: " + message);
    }
}

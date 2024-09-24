package ru.javarush.dnekh.cryptoanalyzer.exception;

/**
 * Utility class for handling error messages.
 */
public final class ErrorHandler {

    private ErrorHandler() {
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

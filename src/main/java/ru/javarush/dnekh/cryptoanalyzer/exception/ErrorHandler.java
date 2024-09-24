package ru.javarush.dnekh.cryptoanalyzer.exception;

/**
 * Utility class for handling error messages.
 */
public final class ErrorHandler {

    public static final String SOME_ERROR = "SOME ERROR OCCURRED: ";
    public static final String FILE_ERROR_MESSAGE = "FILE ERROR: ";
    public static final String DECRYPTION_NOT_CONFIRMED_MESSAGE = "DECRYPTION WAS NOT CONFIRMED. NO FILE WAS SAVED OR UPDATED.";
    public static final String INVALID_CHOSEN_OPTION_MESSAGE = "INVALID OPTION. PLEASE TRY AGAIN.";
    public static final String INVALID_OUTPUT_DIRECTORY = "THE OUTPUT DIRECTORY IS INVALID OR NOT WRITABLE. PLEASE ENTER A VALID PATH: ";
    public static final String INVALID_FILE_PATH = "INVALID FILE PATH. PLEASE TRY AGAIN!";
    public static final String INVALID_KEY_ENTERED = "INVALID KEY ENTERED. PLEASE ENTER A VALID INTEGER!";

    public static final String ENTER_VALID_PATH = "ENTER A VALID OUTPUT FILE PATH: ";
    public static final String PATH_CANNOT_BE_EMPTY = "PATH CANNOT BE EMPTY. PLEASE ENTER A VALID PATH: ";

    private ErrorHandler() {
    }

    /**
     * Displays an error message to the console.
     *
     * @param message the error message to display
     */
    public static void showError(String message) {
        System.out.println(message);
    }
}
